package com.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String mobileNumber;
    private String address;
    private String password;
    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private List<BookingDetails> bookings;

}
