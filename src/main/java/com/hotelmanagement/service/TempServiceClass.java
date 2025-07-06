package com.hotelmanagement.service;

import com.hotelmanagement.dao.BookingRepo;
import com.hotelmanagement.model.BookingDetails;
import com.hotelmanagement.model.BookingStatus;
import com.hotelmanagement.model.ConfirmedStatus;
import com.hotelmanagement.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TempServiceClass {

    @Autowired
    BookingRepo bookingRepo;
    @Autowired
    RoomService roomService;
    @Autowired
    MailSenderService mailSenderService;

    public String bookNow(BookingDetails details) {

        Room roomBooking = roomService.findById(details.getRoomId().getRoomNumber());

        if(roomBooking.getStatus() == BookingStatus.AVAILABLE) {

            if(details.getCheckInDate().isBefore(LocalDateTime.now())) {
                throw new DateTimeException("Please try for future date/time");
            }



            roomBooking.setStatus(BookingStatus.BOOKED);
            details.setRoomId(roomBooking);
            roomService.updateRoom(roomBooking);

            details.setConfirmedStatus(ConfirmedStatus.BOOKING_CONFIRMED);
            details.setBookingDate(LocalDateTime.now());
            details.setCheckOutDate(details.getCheckInDate().plusDays(details.getStayingDays()));

            bookingRepo.save(details);

            mailSenderService.sendMail(details);

            return "Successfully booked the room in Mana Hotel. Have a happy stay :)";
        }

        return "Room status is " + roomService.findById(details.getRoomId().getRoomNumber()).getStatus()
                + ", Please check for other rooms!!!";
    }
}
