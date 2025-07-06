package com.hotelmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HotelManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelManagementApplication.class, args);
	}

}
