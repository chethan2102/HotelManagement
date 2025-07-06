package com.hotelmanagement;

import com.hotelmanagement.dao.UserRepo;
import com.hotelmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.hotelmanagement.model.UserPrincipal;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByName(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid User!!!");
        }

        return new UserPrincipal(user);
    }
}
