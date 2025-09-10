package com.innowise.orders.analysis.service;

import com.innowise.orders.analysis.model.Customer;
import com.innowise.orders.analysis.model.Order;
import com.innowise.orders.analysis.model.enums.OrderStatus;
import com.innowise.orders.analysis.model.OrderItem;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for sales and customer analysis.
 * Provides methods to calculate various business metrics from order data.
 */
public class OrderMetricsService {
    /**
     * Retrieves a list of unique cities where orders came from.
     *
     * @param orders the list of orders to analyze
     * @return the list of unique cities
     */
    public List<String> getUniqueOrderCities(List<Order> orders) {
        return orders.stream()
                .map(Order::getCustomer)
                .map(Customer::getCity)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Calculates total income for all completed orders.
     *
     * @param orders the list of orders to analyze
     * @return total income for completed orders
     */
    public double countTotalCompletedIncome(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .mapToDouble(order -> calculateTotalAmount(order.getItems()))
                .sum();
    }

    /**
     * Identifies the most popular product by sales.
     *
     * @param orders the list of orders to analyze
     * @return the most popular product
     */
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

    /**
     * Calculates the average check for successfully delivered orders.
     *
     * @param orders the list of orders to analyze
     * @return the average check for delivered orders
     */
    public double calculateDeliveredOrdersAverageCheck(List<Order> orders) {
        DoubleSummaryStatistics stats = orders.stream()
                .filter(order -> order.getStatus().equals(OrderStatus.DELIVERED))
                .mapToDouble(order -> calculateTotalAmount(order.getItems()))
                .summaryStatistics();

        if (stats.getCount() == 0) {
            return 0.00;
        }

        return stats.getAverage();
    }

    /**
     * Finds customers who have more than 5 orders.
     *
     * @param orders the list of orders to analyze
     * @return the list of customers with more than 5 orders
     */
    public List<Customer> findCustomersWithMoreThanFiveOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()))
                .entrySet().stream()
                .filter(es -> es.getValue() > 5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private double calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}