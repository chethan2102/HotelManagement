package com.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingNumber;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User userId;

    private int stayingDays;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime checkInDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime checkOutDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime BookingDate;

    @Enumerated(EnumType.STRING)
    private ConfirmedStatus confirmedStatus;

    @JoinColumn(name = "room_id")
    @ManyToOne
    private Room roomId;



    public BookingDetails(User userId, int stayingDays, LocalDateTime checkInDate, Room roomId) {
        this.userId = userId;
        this.stayingDays = stayingDays;
        this.checkInDate = checkInDate;
        this.roomId = roomId;
    }

}
