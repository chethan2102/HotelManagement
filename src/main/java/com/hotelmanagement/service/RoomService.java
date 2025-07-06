package com.hotelmanagement.service;

import com.hotelmanagement.dao.RoomRepo;
import com.hotelmanagement.model.BookingStatus;
import com.hotelmanagement.model.Room;
import com.hotelmanagement.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepo roomRepo;

    public List<Room> getRooms() {
        return roomRepo.findAll();
    }

    public String addRoom(Room room) {
        Optional<Room> r = roomRepo.findById(room.getRoomNumber());

        if(r.isEmpty()) {
            roomRepo.save(room);
            return "Successfully added the room : " + room.getRoomNumber();
        }
        else return "Failed to add the room. " + room.getRoomNumber() + " is already present.";
    }

    public String updateRoom(Room room) {
        Optional<Room> r = roomRepo.findById(room.getRoomNumber());

        if(r.isPresent()) {
            roomRepo.save(room);
            return "Successfully updated the room : " + room.getRoomNumber();
        }
        else return "Failed to update the room. " + room.getRoomNumber() + " is not available to update.";
    }

    public String delete(int id) {
        roomRepo.deleteById(id);
        return "Deleted the room successfully";
    }

    public List<Room> searchBy(String keyword) {
        List<Room> rooms = new ArrayList<>();
        try{
            rooms.addAll(roomRepo.findByType(Type.valueOf(keyword.toUpperCase())));
        }catch(Exception ignored) {}
        try{
            rooms.addAll(roomRepo.findByStatus(BookingStatus.valueOf(keyword.toUpperCase())));
        }catch(Exception ignored) {}

        return rooms;
    }

    public Room findById(int id) {

        return roomRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Invalid Room number. Pleas try with a valid number"));

    }
}
