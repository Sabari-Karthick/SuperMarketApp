package com.vanenburg.sm.dto.order;

import java.time.LocalDate;

import com.vanenburg.sm.enums.Status;

import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private Double totalPrice;
    private LocalDate date;
    private String firstName;
    private String lastName;
    private String city;
    private String email;
    private String phoneNumber;
    private Status status;
}
