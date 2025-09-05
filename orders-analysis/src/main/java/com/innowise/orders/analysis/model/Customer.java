package com.innowise.orders.analysis.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private LocalDateTime registeredAt;
    private int age;
    private String city;

    public String getCity() {
        return city;
    }
}