package com.hotelmanagement.controller;

import com.hotelmanagement.model.BookingDetails;
import com.hotelmanagement.model.User;
import com.hotelmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/viewAll")
    public List<User> viewAllGuests(){
        return service.viewAll();
    }

    @GetMapping("/view/{id}")
    public User viewGuestsById(@PathVariable int id){
        return service.viewUserById(id);
    }

    @PostMapping("/addUser")
    public String registerUser(@RequestBody User user){
        return service.addUser(user);
    }

    @PostMapping("/addStaff")
    public String registerStaff(@RequestBody User user){
        return service.addStaff(user);
    }

    @PostMapping("/addAdmin")
    public String registerAdmin(@RequestBody User user){
        return service.addAdmin(user);
    }

    @PutMapping("/updateUser/{id}")
    public String UpdateGuest(@RequestBody User user, @PathVariable int id){
        return service.UpdateUser(user, id);
    }

    @DeleteMapping("deleteUser/{id}")
    public String deleteGuest(@PathVariable int id){
        return service.deleteUser(id);
    }

    @DeleteMapping("deleteStaff/{id}")
    public String deleteStaff(@PathVariable int id){
        return service.deleteStaff(id);
    }

    @DeleteMapping("deleteAdmin/{id}")
    public String deleteAdmin(@PathVariable int id){
        return service.deleteAdmin(id);
    }

    @GetMapping("/myBookings/{userId}")
    public List<BookingDetails> guestBooking(@PathVariable int userId){
        return service.findBookingsByUserId(userId);
    }

}
