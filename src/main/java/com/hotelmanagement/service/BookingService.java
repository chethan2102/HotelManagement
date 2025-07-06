package com.hotelmanagement.service;

import com.hotelmanagement.dao.BookingRepo;
import com.hotelmanagement.dao.RoomRepo;
import com.hotelmanagement.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MailSenderService mailSenderService;

    public String bookNow(BookingDetails details) {

        Room roomBooking = roomService.findById(details.getRoomId().getRoomNumber());

        List<BookingDetails> bookedDetails = bookingRepo.findByConfirmedStatusAndRoomId_RoomNumber(ConfirmedStatus.BOOKING_CONFIRMED, details.getRoomId().getRoomNumber());
        System.out.println("In temp service class : " + bookedDetails);
        if(bookedDetails != null) {
            for(BookingDetails b : bookedDetails) {
                if(details.getCheckInDate().isAfter(b.getCheckInDate())
                        && details.getCheckInDate().isBefore(b.getCheckOutDate().plusHours(1L))) {
                    throw new RuntimeException("The selected room was already booked for that particular date. Please try for other room/date.");
                }
            }
            return bookingConfirmed(details);
        }
        return bookingConfirmed(details);
    }


    private String bookingConfirmed(BookingDetails details) {

        Room roomBooking = roomService.findById(details.getRoomId().getRoomNumber());

        if(details.getCheckInDate().isAfter(LocalDateTime.now())) {
            details.setConfirmedStatus(ConfirmedStatus.BOOKING_CONFIRMED);
            details.setBookingDate(LocalDateTime.now());
            details.setCheckOutDate(details.getCheckInDate().plusDays(details.getStayingDays()));

            roomBooking.setStatus(BookingStatus.BOOKED);
            details.setRoomId(roomBooking);
            roomService.updateRoom(roomBooking);
            bookingRepo.save(details);

            mailSenderService.sendMail(details);


            return "Successfully booked the room in Hotel Grand. Have a happy stay :)";
        }
        throw new DateTimeException("Please try for future date/time");
    }

    @Transactional
    public String cancelBooking(int bookingId) {
        BookingDetails details = bookingRepo.findById(bookingId)
                                        .orElseThrow(() -> new RuntimeException("Invalid booking id!!!"));

        if(details != null && details.getConfirmedStatus() == ConfirmedStatus.BOOKING_CONFIRMED) {
            details.setConfirmedStatus(ConfirmedStatus.BOOKING_CANCELLED);
            details.setStayingDays(0);
            details.setCheckInDate(null);
            details.setCheckOutDate(LocalDateTime.now());

            Room detailsRoom = details.getRoomId();
            detailsRoom.setStatus(BookingStatus.CANCELLED);
            roomService.updateRoom(detailsRoom);

            details.setRoomId(detailsRoom);
            bookingRepo.save(details);

            return "Sorry to hear you are cancelling...\nSuccessfully cancelled the room booking";
        }
        return "Invalid booking Id";
    }

    public List<BookingDetails> getAllBookings() {
        return bookingRepo.findAll();
    }

    public List<BookingDetails> searchByRoomId(int roomId) {
        return bookingRepo.findByRoomId_RoomNumber(roomId);
    }

    public List<BookingDetails> searchByUserId(int guestId) {
        return bookingRepo.findByUserId_Id(guestId);
    }
}
