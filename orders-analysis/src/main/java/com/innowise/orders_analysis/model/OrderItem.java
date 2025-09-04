package com.innowise.orders_analysis.model;

import com.innowise.orders_analysis.model.enums.Category;
import lombok.Builder;

@Builder
public class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}