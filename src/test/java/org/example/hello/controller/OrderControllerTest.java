package org.example.hello.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import org.example.hello.model.Order;
import org.example.hello.service.OrderService;

@SpringBootTest
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private final String API_PATH = "/api/order/";

    private Order order;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();

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
    void getOrderById() throws Exception {
        // Arrange
        when(orderService.getOrderById(any())).thenReturn(order);

        // Act
        MvcResult result = mockMvc.perform(get(API_PATH + order.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(order));
    }

    @Test
    void getAllOrdersReturnsEmptyList() throws Exception {
        // Arrange
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());

        // Act
        MvcResult result = mockMvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(), "[]");
    }

    @Test
    void controller_PropagatesCascadedException() {
        // Arrange
        String orderId = "gjdfspoas34s01583f06eff";
        when(orderService.getOrderById(any())).thenThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Order not found."));

        // Act
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> orderController.getOrderById(orderId),
                "Expected getOrderById() to throw a ResponseStatusException, but it didn't."
        );

        // Assert
        assertTrue(Objects.requireNonNull(exception.getMessage()).contains("Order not found."));
    }

    @Test
    void registerOrder() throws Exception {
        // Arrange
        when(orderService.createOrder(any())).thenReturn(order);

        // Act
        MvcResult result = mockMvc.perform(post(API_PATH)
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(order));
    }

    @Test
    void deleteOrder() throws Exception {
        // Arrange
        when(orderService.deleteOrderById(any())).thenReturn(order);

        // Act
        MvcResult result = mockMvc.perform(delete(API_PATH + order.getId())
                        .content(objectMapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(order));
    }
}