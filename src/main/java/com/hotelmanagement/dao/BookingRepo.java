package com.hotelmanagement.dao;

import com.hotelmanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<BookingDetails, Integer> {

    List<BookingDetails> findByRoomId_Status(BookingStatus bookingStatus);

    List<BookingDetails> findByRoomId_RoomNumber(int roomId);

    List<BookingDetails> findByUserId_Id(int guestId);


    @Query("SELECT new com.hotelmanagement.model.BookingConfirmedDetailsDTO" +
            "(b.bookingNumber, u.name, r.roomNumber, b.stayingDays, b.checkInDate, b.checkOutDate, b.confirmedStatus) " +
            "from BookingDetails b join b.roomId r join b.userId u WHERE b.bookingNumber = :bookingNum")
    BookingConfirmedDetailsDTO findBookedDetailsForMailing(@Param("bookingNum") int bookingNumber);

    List<BookingDetails> findByConfirmedStatusAndRoomId_RoomNumber(ConfirmedStatus confirmedStatus, int roomNumber);

}
