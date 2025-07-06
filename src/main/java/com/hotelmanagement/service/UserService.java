package com.hotelmanagement.service;

import com.hotelmanagement.dao.UserRepo;
import com.hotelmanagement.model.BookingDetails;
import com.hotelmanagement.model.Role;
import com.hotelmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    PasswordEncoder encoder;

    public List<User> viewAll() {
        return repo.findAll();
    }

    public String addUser(User user) {
        if((repo.findUserByName(user.getName())) == null){
            user.setRole(Role.USER);
            user.setPassword(encoder.encode(user.getPassword()));
            repo.save(user);
            return "added the user successfully.";
        }
        return "Existing username!!!. Please try with new name :)";
    }

    public String addStaff(User user) {
        if((repo.findUserByName(user.getName())) == null){
            user.setRole(Role.STAFF);
            user.setPassword(encoder.encode(user.getPassword()));
            repo.save(user);
            return "added a new staff successfully.";
        }
        return "Existing username!!!. Please try with new name :)";
    }

    public String addAdmin(User user) {
        if((repo.findUserByName(user.getName())) == null){
            user.setRole(Role.ADMIN);
            user.setPassword(encoder.encode(user.getPassword()));
            repo.save(user);
            return "added the user successfully.";
        }
        return "Existing username!!!. Please try with new name :)";
    }

    public String UpdateUser(User user, int id) {
        if((repo.findById(id)).isPresent()){
            user.setId(id);
            repo.save(user);
            return "Updated the user successfully, User id : " + id;
        }
        return "Invalid user!!!";
    }

    public String deleteUser(int id) {
        User user = repo.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if(user != null){
            if(user.getRole() == Role.USER) {
                repo.deleteById(id);
                return "Deleted the guest : " + id;
            }
            else {
                return "Unauthorized link to delete " + user.getRole();
            }
        }
        return "Invalid guest id!!!";
    }

    public String deleteStaff(int id) {
        User user = repo.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if(user != null){
            if(user.getRole() == Role.STAFF) {
                repo.deleteById(id);
                return "Deleted the guest : " + id;
            }
            else {
                return "Unauthorized link to delete " + user.getRole();
            }
        }
        return "Invalid guest id!!!";
    }

    public String deleteAdmin(int id) {
        User user = repo.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        if(user != null){
            if(user.getRole() == Role.ADMIN) {
                repo.deleteById(id);
                return "Deleted the guest : " + id;
            }
            else {
                return "Unauthorized link to delete " + user.getRole();
            }
        }
        return "Invalid guest id!!!";
    }

    public User viewUserById(int id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Invalid guest Id"));
    }

    public List<BookingDetails> findBookingsByUserId(int userId) {
        return repo.findBookingsByUserId(userId);
    }
}
