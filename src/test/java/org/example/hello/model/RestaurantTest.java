package org.example.hello.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId("fdsafds7fasdfsa8fas");
        restaurant.setName("Mega Giga Restaurant");
        List<String> menu = List.of("242323afdas2", "afdas223423", "fasdfasf23421a", "fddasj234ja9djk");
        restaurant.setMenu(menu);
    }

    @Test
    void updateRestaurant() {
        // Arrange
        Restaurant other = new Restaurant();
        other.setName("Mega Giga Ultra Restaurant");
        List<String> menu = List.of("242323afds2", "afdas2244423", "fasdfas223421a", "fdsj234ja9djk");
        other.setMenu(menu);

        // Act
        restaurant.updateRestaurant(other);

        // Assert
        assertEquals(restaurant.getName(), other.getName());
        assertEquals(restaurant.getMenu(), other.getMenu());
    }
}
