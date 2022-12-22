package org.example.hello.service;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import org.example.hello.model.Client;
import org.example.hello.model.Meal;
import org.example.hello.model.Order;
import org.example.hello.repository.ClientRepository;
import org.example.hello.repository.MealRepository;
import org.example.hello.repository.OrderRepository;
import org.example.hello.repository.RestaurantRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    MealRepository mealRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId("4fdsajkl423fjdsak23");
        order.setClientId("jh4k32l1h43123h21");
        Map<String, Integer> hm = new HashMap<>();
        hm.put("gfddgf4325s01583f06eff", 2);
        order.setMeals(hm);
        order.setTotalPrice(40f);
    }

    @Test
    void getOrderById() {
        // Arrange
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(order));

        // Act
        Order result = orderService.getOrderById(order.getId());

        assertEquals(result, order);
    }

    @Test
    void getOrderByIdThrowsOnEmpty() {
        // Arrange
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResponseStatusException.class,
                () -> orderService.getOrderById(order.getId()),
                "Expected getOrderById() to throw a ResponseStatusException, but it didn't."
        );
    }

    @Test
    void getAllOrdersReturnsEmptyList() {
        // Arrange

        // Act
        var result = orderService.getAllOrders();

        // Assert
        assertEquals(result.size(), 0);
    }

    @Test
    void createOrder() {
        // Arrange
        Client client = new Client();
        client.setId("gfdgdf4325s01583f06eff");
        client.setName("John");
        client.setEmail("john@gmail.com");
        client.setAddress("284 Garfield Ave. Hackensack, NJ 07601");
        client.setPhoneNumber("0762476343");

        Meal meal = new Meal();
        meal.setId("gfddgf4325s01583f06eff");
        meal.setName("Spaghetti Carbonara");
        List<String> ingredients = List.of("spaghetti", "eggs", "parmesan", "pepper");
        meal.setIngredients(ingredients);
        meal.setPrice(22f);
        meal.setPortionSize(220);
        meal.setRestaurantId("fdsfadsfewsds2342f");

        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        when(mealRepository.findById(any())).thenReturn(Optional.of(meal));

        // Act
        Order result = orderService.createOrder(order);

        // Assert
        verify(orderRepository, times(1)).save(any());
        assertEquals(result.getTotalPrice(), order.getTotalPrice());
        assertEquals(result.getClientId(), order.getClientId());
        assertEquals(result.getMeals(), order.getMeals());
    }


    @Test
    void deleteOrderById() {
        // Arrange
        when(orderRepository.findById(any())).thenReturn(Optional.ofNullable(order));

        // Act
        Order result = orderService.deleteOrderById(order.getId());

        // Assert
        verify(orderRepository, times(1)).deleteById(eq(order.getId()));
        assertEquals(result, order);
    }
}
