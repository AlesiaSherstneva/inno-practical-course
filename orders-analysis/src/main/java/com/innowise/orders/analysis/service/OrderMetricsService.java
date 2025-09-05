package com.innowise.orders.analysis.service;

import com.innowise.orders.analysis.model.Customer;
import com.innowise.orders.analysis.model.Order;
import com.innowise.orders.analysis.model.enums.OrderStatus;
import com.innowise.orders.analysis.model.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMetricsService {
    public List<String> getUniqueOrderCities(List<Order> orders) {
        return orders.stream()
                .map(Order::getCustomer)
                .map(Customer::getCity)
                .distinct()
                .collect(Collectors.toList());
    }

    public double countTotalCompletedIncome(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .mapToDouble(order -> calculateTotalAmount(order.getItems()))
                .sum();
    }

    public String findBestSellingProduct(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity))
                )
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("No products found");
    }

    public double calculateDeliveredOrdersAverageCheck(List<Order> orders) {
        List<Order> deliveredOrders = orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .toList();

        if (deliveredOrders.isEmpty()) {
            return 0.00;
        }

        double totalAmount = deliveredOrders.stream()
                .mapToDouble(order -> calculateTotalAmount(order.getItems()))
                .reduce(0, Double::sum);

        return totalAmount / deliveredOrders.size();
    }

    public List<Customer> findCustomersWithFiveOrMoreOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()))
                .entrySet().stream()
                .filter(es -> es.getValue() >= 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private double calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}