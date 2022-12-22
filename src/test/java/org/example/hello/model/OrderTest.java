package org.example.hello.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId("4fdsajkl423fjdsak23");
        order.setClientId("jh4k32l1h43123h21");
        Map<String, Integer> hm = new HashMap<String, Integer>();
        hm.put("Spaghetti Carbonara", 2);
        hm.put("Bread", 3);
        order.setMeals(hm);
        order.setTotalPrice(40f);
    }

    @Test
    void updateOrder() {
        // Arrange
        Order other = new Order();
        other.setClientId("jhk4432l1h43123h21");
        Map<String, Integer> hm = new HashMap<String, Integer>();
        hm.put("Spaghetti Bolognese", 1);
        hm.put("Bread", 2);
        other.setMeals(hm);
        other.setTotalPrice(20f);

        // Act
        order.updateOrder(other);

        // Assert
        assertEquals(order.getClientId(), other.clientId);
        assertEquals(order.getMeals(), other.meals);
        assertEquals(order.getTotalPrice(), other.totalPrice);
    }

}
