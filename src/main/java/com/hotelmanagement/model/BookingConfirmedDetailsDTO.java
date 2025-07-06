package com.hotelmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingConfirmedDetailsDTO {

    private int bookingNumber;
    private String username;
    private int roomNumber;
    private int stayingDays;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private ConfirmedStatus confirmedStatus;

}
