package com.innowise.orders.analysis.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Customer other)) {
            return false;
        }

        return this.customerId.equals(other.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}