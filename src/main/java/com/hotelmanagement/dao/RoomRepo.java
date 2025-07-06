package com.hotelmanagement.dao;

import com.hotelmanagement.model.BookingStatus;
import com.hotelmanagement.model.Room;
import com.hotelmanagement.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Integer> {

//    List<Room> findByTypeContainingOrStatusContaining(Type type, BookingStatus status);

    Collection<? extends Room> findByType(Type type);

    List<Room> findByStatus(BookingStatus status);
}
