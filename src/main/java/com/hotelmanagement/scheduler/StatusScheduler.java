package com.hotelmanagement.scheduler;

import com.hotelmanagement.dao.BookingRepo;
import com.hotelmanagement.dao.RoomRepo;
import com.hotelmanagement.model.BookingDetails;
import com.hotelmanagement.model.BookingStatus;
import com.hotelmanagement.model.ConfirmedStatus;
import com.hotelmanagement.model.Room;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatusScheduler {

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    RoomRepo roomRepo;

    @Scheduled(fixedRate = 1000*60)
    public void updatedBookedRoomDetails() {
        System.out.println("LocalDateTime : " + LocalDateTime.now());
//        System.out.println("\nUpdating the booked rooms...........................\n");
        List<BookingDetails> bookedRooms = bookingRepo.findByRoomId_Status(BookingStatus.BOOKED);

        if(bookedRooms.isEmpty()) return;
        System.out.println("\nIn updatedBookedRoomDetails : " + bookedRooms);
        for(BookingDetails b : bookedRooms){
            if (b.getCheckInDate().isEqual(LocalDateTime.now()) ||
                    (LocalDateTime.now().isAfter(b.getCheckInDate())
                            && LocalDateTime.now().isBefore(b.getCheckOutDate()))) {
                b.getRoomId().setStatus(BookingStatus.CHECKED_IN);

                roomRepo.save(b.getRoomId());
            }
            else if(b.getCheckOutDate().isBefore(LocalDateTime.now())) {
                b.getRoomId().setStatus(BookingStatus.CHECKED_OUT_UNDER_MAINTENANCE);
                roomRepo.save(b.getRoomId());
                b.setConfirmedStatus(ConfirmedStatus.VACATED_HOTEL);
            }
        }
        bookingRepo.saveAll(bookedRooms);
    }

    @Scheduled(fixedRate = 1000*60)
    @Transactional
    public void updateCheckedInRoomStatus() {
//        System.out.println("\nUpdating the checked-in rooms...........................\n");
        List<BookingDetails> checkedInRooms = bookingRepo.findByRoomId_Status(BookingStatus.CHECKED_IN);

        if(checkedInRooms.isEmpty()) {
            return;
        }

        for(BookingDetails b : checkedInRooms){
            if (b.getCheckOutDate().isBefore(LocalDateTime.now()) || b.getCheckOutDate().isEqual(LocalDateTime.now())) {
                b.setConfirmedStatus(ConfirmedStatus.VACATED_HOTEL);
                b.getRoomId().setStatus(BookingStatus.CHECKED_OUT_UNDER_MAINTENANCE);

                roomRepo.save(b.getRoomId());
            }
        }
        bookingRepo.saveAll(checkedInRooms);
    }

    @Scheduled(fixedRate = 1000*60)
    @Transactional
    public void updateMaintenanceRoomStatus() {
//        System.out.println("\nUpdating the checked-out/maintenance rooms...........................\n");
        List<BookingDetails> maintenanceRooms = bookingRepo.findByRoomId_Status(BookingStatus.CHECKED_OUT_UNDER_MAINTENANCE);

        if(maintenanceRooms.isEmpty()) return;

        for(BookingDetails b : maintenanceRooms){
            if (b.getCheckOutDate().isBefore(LocalDateTime.now().minusMinutes(1L))) {
                b.getRoomId().setStatus(BookingStatus.AVAILABLE);

                roomRepo.save(b.getRoomId());
            }
        }

    }

    @Scheduled(fixedRate = 1000*60)
    @Transactional
    public void updateCancelledRoomStatus() {
//        System.out.println("\nUpdating the cancelled rooms...........................\n");
        List<BookingDetails> cancelledBookings = bookingRepo.findByRoomId_Status(BookingStatus.CANCELLED);

        if(!cancelledBookings.isEmpty()) {
            for(BookingDetails b : cancelledBookings) {
                if(b.getStayingDays() == 0) {
                    Room room = b.getRoomId();
                    if(b.getCheckOutDate().isBefore(LocalDateTime.now())) {
                        room.setStatus(BookingStatus.AVAILABLE);

                        roomRepo.save(room);
                    }
                }
            }
        }

    }

}
