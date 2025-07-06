package com.hotelmanagement.dao;

import com.hotelmanagement.model.BookingDetails;
import com.hotelmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query("SELECT g.bookings from User g WHERE g.id = :id")
    List<BookingDetails> findBookingsByUserId(@Param("id") int id);

    User findUserByName(String name);
}
