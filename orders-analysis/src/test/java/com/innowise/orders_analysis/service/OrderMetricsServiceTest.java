package com.innowise.orders_analysis.service;

import com.innowise.orders_analysis.model.Customer;
import com.innowise.orders_analysis.model.Order;
import com.innowise.orders_analysis.model.OrderItem;
import com.innowise.orders_analysis.model.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderMetricsServiceTest {
    private final OrderMetricsService orderMetricsService = new OrderMetricsService();
    private List<Order> testOrders;

    @BeforeEach
    void setUp() {
        testOrders = new ArrayList<>();
    }

    @Test
    void getUniqueOrderCitiesSuccessfulTest() {
        String city1 = "Test city 1", city2 = "Test city 2";
        for (int i = 0; i < 3; i++) {
            testOrders.add(ModelsBuilder.getOrderByCity(city1));
        }
        testOrders.add(ModelsBuilder.getOrderByCity(city2));

        List<String> uniqueOrderCities = orderMetricsService.getUniqueOrderCities(testOrders);

        assertEquals(2, uniqueOrderCities.size());
        assertTrue(uniqueOrderCities.contains(city1));
        assertTrue(uniqueOrderCities.contains(city2));
    }

    @Test
    void getUniqueOrderCitiesFromEmptyListTest() {
        List<String> uniqueOrderCities = orderMetricsService.getUniqueOrderCities(testOrders);

        assertTrue(uniqueOrderCities.isEmpty());
    }

    @Test
    void countTotalCompletedIncomeSuccessfulTest() {
        double itemsPrice = 10.32;
        int itemsQuantity = 3;

        Order deliveredOrder1 = ModelsBuilder.getOrderByStatus(OrderStatus.DELIVERED);
        deliveredOrder1.setItems(List.of(
                ModelsBuilder.getOrderItemByPriceAndQuantity(itemsPrice, itemsQuantity),
                ModelsBuilder.getOrderItemByPriceAndQuantity(itemsPrice, itemsQuantity)
        ));
        Order deliveredOrder2 = ModelsBuilder.getOrderByStatus(OrderStatus.DELIVERED);
        deliveredOrder2.setItems(List.of(
                ModelsBuilder.getOrderItemByPriceAndQuantity(itemsPrice, itemsQuantity)
        ));
        Order cancelledOrder = ModelsBuilder.getOrderByStatus(OrderStatus.CANCELLED);
        cancelledOrder.setItems(List.of(
                ModelsBuilder.getOrderItemByPriceAndQuantity(itemsPrice, itemsQuantity)
        ));

        testOrders.addAll(List.of(deliveredOrder1, deliveredOrder2, cancelledOrder));

        double totalCompletedIncome = orderMetricsService.countTotalCompletedIncome(testOrders);

        assertEquals(itemsPrice * itemsQuantity * 3, totalCompletedIncome);
    }

    @Test
    void countTotalCompletedIncomeFromEmptyListTest() {
        assertEquals(0.00, orderMetricsService.countTotalCompletedIncome(testOrders));
    }

    @Test
    void findBestSellingProductSuccessfulTest() {
        String name1 = "Test product 1", name2 = "Test product 2";

        Order deliveredOrder = ModelsBuilder.getOrderByStatus(OrderStatus.DELIVERED);
        deliveredOrder.setItems(List.of(
                ModelsBuilder.getOrderItemByProductNameAndQuantity(name1, 2),
                ModelsBuilder.getOrderItemByProductNameAndQuantity(name2, 2)
        ));
        Order shippedOrder = ModelsBuilder.getOrderByStatus(OrderStatus.DELIVERED);
        shippedOrder.setItems(List.of(
                ModelsBuilder.getOrderItemByProductNameAndQuantity(name1, 1)
        ));
        Order cancelledOrder = ModelsBuilder.getOrderByStatus(OrderStatus.CANCELLED);
        cancelledOrder.setItems(List.of(
                ModelsBuilder.getOrderItemByProductNameAndQuantity(name2, 1)
        ));

        testOrders.addAll(List.of(deliveredOrder, shippedOrder, cancelledOrder));

        String bestSellingProduct = orderMetricsService.findBestSellingProduct(testOrders);

        assertEquals(name1, bestSellingProduct);
    }

    @Test
    void findBestSellingProductFromEmptyListTest() {
        assertEquals("No products found", orderMetricsService.findBestSellingProduct(testOrders));
    }

    @Test
    void calculateDeliveredOrdersAverageCheckSuccessfulTest() {
        double itemsPrice = 100.00;
        int itemsQuantity = 5;

        Order deliveredOrder1 = ModelsBuilder.getOrderByStatus(OrderStatus.DELIVERED);
        deliveredOrder1.setItems(List.of(
                ModelsBuilder.getOrderItemByPriceAndQuantity(itemsPrice, itemsQuantity),
                ModelsBuilder.getOrderItemByPriceAndQuantity(itemsPrice, itemsQuantity)
        ));
        Order deliveredOrder2 = ModelsBuilder.getOrderByStatus(OrderStatus.DELIVERED);
        deliveredOrder2.setItems(List.of(
                ModelsBuilder.getOrderItemByPriceAndQuantity(itemsPrice, itemsQuantity)
        ));
        Order cancelledOrder = ModelsBuilder.getOrderByStatus(OrderStatus.CANCELLED);
        cancelledOrder.setItems(List.of(
                ModelsBuilder.getOrderItemByPriceAndQuantity(itemsPrice, itemsQuantity)
        ));

        testOrders.addAll(List.of(deliveredOrder1, deliveredOrder2, cancelledOrder));

        double ordersAverageCheck = orderMetricsService.calculateDeliveredOrdersAverageCheck(testOrders);

        double expectedAverageValue = itemsPrice * itemsQuantity * 3 / (testOrders.size() - 1);
        assertEquals(expectedAverageValue, ordersAverageCheck);
    }

    @Test
    void calculateDeliveredOrdersAverageCheckFromEmptyListTest() {
        assertEquals(0.00, orderMetricsService.calculateDeliveredOrdersAverageCheck(testOrders));
    }

    @Test
    void findCustomersWithFiveOrMoreOrdersSuccessfulTest() {
        Customer customer1 = ModelsBuilder.getCustomerById("Customer with 4 orders");
        for (int i = 0; i < 4; i++) {
            testOrders.add(ModelsBuilder.getOrderByCustomer(customer1));
        }
        Customer customer2 = ModelsBuilder.getCustomerById("Customer with 5 orders");
        for (int i = 0; i < 5; i++) {
            testOrders.add(ModelsBuilder.getOrderByCustomer(customer2));
        }
        Customer customer3 = ModelsBuilder.getCustomerById("Customer with 6 orders");
        for (int i = 0; i < 6; i++) {
            testOrders.add(ModelsBuilder.getOrderByCustomer(customer3));
        }

        List<Customer> customersWithMoreThanFiveOrders = orderMetricsService.findCustomersWithFiveOrMoreOrders(testOrders);

        assertEquals(2, customersWithMoreThanFiveOrders.size());
        assertFalse(customersWithMoreThanFiveOrders.contains(customer1));
        assertTrue(customersWithMoreThanFiveOrders.contains(customer2));
        assertTrue(customersWithMoreThanFiveOrders.contains(customer3));
    }

    @Test
    void findCustomersWithFiveOrMoreOrdersFromEmptyListTest() {
        List<Customer> customersWithMoreThanFiveOrders = orderMetricsService.findCustomersWithFiveOrMoreOrders(testOrders);

        assertTrue(customersWithMoreThanFiveOrders.isEmpty());
    }

    private static class ModelsBuilder {
        private static Order getOrderByCustomer(Customer customer) {
            return Order.builder()
                    .customer(customer)
                    .build();
        }

        private static Order getOrderByCity(String city) {
            return Order.builder()
                    .customer(Customer.builder()
                            .city(city)
                            .build())
                    .build();
        }

        private static Order getOrderByStatus(OrderStatus status) {
            return Order.builder()
                    .status(status)
                    .build();
        }

        private static OrderItem getOrderItemByPriceAndQuantity(double price, int quantity) {
            return OrderItem.builder()
                    .price(price)
                    .quantity(quantity)
                    .build();
        }

        private static OrderItem getOrderItemByProductNameAndQuantity(String productName, int quantity) {
            return OrderItem.builder()
                    .productName(productName)
                    .quantity(quantity)
                    .build();
        }

        private static Customer getCustomerById(String customerId) {
            return Customer.builder()
                    .customerId(customerId)
                    .build();
        }
    }
}