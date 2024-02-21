package com.vanenburg.sm.domain;

import java.time.LocalDate;
import java.util.List;

import com.vanenburg.sm.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "city")
    private String city;



    @Column(name = "email")
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;



    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;
    
    @Enumerated(EnumType.STRING)
    private Status status;
}
