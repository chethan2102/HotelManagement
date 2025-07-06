package com.hotelmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    private int roomNumber;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int price;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @OneToMany(mappedBy = "roomId", fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private List<BookingDetails> bookingDetails;
}

