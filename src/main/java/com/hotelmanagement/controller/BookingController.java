package com.hotelmanagement.controller;

import com.hotelmanagement.model.BookingDetails;
import com.hotelmanagement.service.BookingService;
import com.hotelmanagement.service.TempServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService service;

    @Autowired
    TempServiceClass tempServiceClass;

    @PostMapping("/roomBooking")
    public String bookNow(@RequestBody BookingDetails details){
        return service.bookNow(details);
    }

    @PutMapping("/cancelBooking/{bookingId}")
    public String cancelBooking(@PathVariable int bookingId) {
        return service.cancelBooking(bookingId);
    }

    @GetMapping("/viewAll")
    public List<BookingDetails> getAllBookings() {
        return service.getAllBookings();
    }

    @GetMapping("/roomId/{roomId}")
    public List<BookingDetails> searchByRoomId(@PathVariable int roomId){
        return service.searchByRoomId(roomId);
    }

    @GetMapping("/userId/{userId}")
    public List<BookingDetails> searchByUserId(@PathVariable int userId){
        return service.searchByUserId(userId);
    }

}
