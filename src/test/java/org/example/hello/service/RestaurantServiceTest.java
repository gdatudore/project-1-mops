package org.example.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import org.example.hello.model.Restaurant;
import org.example.hello.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestaurantServiceTest {

    @Mock
    RestaurantRepository restaurantRepository;

    @InjectMocks
    RestaurantService restaurantService;

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
    void getAllRestaurantsReturnsEmptyList() {

        // Act
        var result = restaurantService.getAllRestaurants();

        assertEquals(result.size(), 0);
    }

    @Test
    void getRestaurantById() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.ofNullable(restaurant));

        Restaurant result = restaurantService.getRestaurantById(restaurant.getId());

        assertEquals(result, restaurant);
    }

    @Test
    void getRestaurantByIdThrowsOnEmpty() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> restaurantService.getRestaurantById(restaurant.getId()),
                "Expected getRestaurantById() to throw a ResponseStatusException, but it didn't."
        );
    }

    @Test
    void createRestaurant() {
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantService.createRestaurant(restaurant);

        assertEquals(result.getName(), restaurant.getName());
        assertEquals(result.getMenu(), restaurant.getMenu());
    }

    @Test
    void updateRestaurant() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.ofNullable(restaurant));
        Restaurant other = new Restaurant();
        other.setName("Mega Giga Ultra Restaurant");
        List<String> menu = List.of("242323afds2", "afdas2244423", "fasdfas223421a", "fdsj234ja9djk");
        other.setMenu(menu);

        // Act
        Restaurant result = restaurantService.updateRestaurant(restaurant.getId(), other);

        // Assert
        verify(restaurantRepository, times(1)).save(any());
        assertEquals(restaurant.getName(), other.getName());
        assertEquals(restaurant.getMenu(), other.getMenu());
    }

    @Test
    void deleteRestaurantById() {
        when(restaurantRepository.findById(any())).thenReturn(Optional.ofNullable(restaurant));

        Restaurant result = restaurantService.deleteRestaurantById(restaurant.getId());

        verify(restaurantRepository, times(1)).deleteById(eq(restaurant.getId()));
        assertEquals(result, restaurant);
    }
}
