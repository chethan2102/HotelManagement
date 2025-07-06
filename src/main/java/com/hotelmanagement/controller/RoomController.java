package com.hotelmanagement.controller;

import com.hotelmanagement.model.Room;
import com.hotelmanagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public String home(){
        return "Welcome to SVSC hotel Management";
    }

    @GetMapping("/viewAll")
    public List<Room> getRooms(){
        return roomService.getRooms();
    }

    @PostMapping("/add")
    public String addRoom(@RequestBody Room room){
        return roomService.addRoom(room);
    }

    @PutMapping("/update")
    public String updateRoom(@RequestBody Room room){
        return roomService.updateRoom(room);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable int id){
        return roomService.delete(id);
    }

    @GetMapping("/search/{keyword}")
    public List<Room> searchByKeyword(@PathVariable String keyword){
        return roomService.searchBy(keyword);
    }

}
