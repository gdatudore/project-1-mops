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
import java.util.Arrays;
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
import org.example.hello.model.Restaurant;
import org.example.hello.service.RestaurantService;

@SpringBootTest
class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private final String API_PATH = "/api/restaurant/";

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
        objectMapper = new ObjectMapper();

        restaurant = new Restaurant();
        restaurant.setId("345nm0sd023j09vdsf");
        restaurant.setName("La Baiatu'");
        restaurant.setMenu(Arrays.asList("Spaghetti Carbonara", "Pizza"));
    }

    @Test
    void getRestaurantById() throws Exception {
        // Arrange
        when(restaurantService.getRestaurantById(any())).thenReturn(restaurant);

        // Act
        MvcResult result = mockMvc.perform(get(API_PATH + restaurant.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(restaurant));
    }

    @Test
    void getAllRestaurantsReturnsEmptyList() throws Exception {
        // Arrange
        when(restaurantService.getAllRestaurants()).thenReturn(new ArrayList<>());

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
        String restaurantId = "gjdfspoas34s01583f06eff";
        when(restaurantService.getRestaurantById(any())).thenThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Restaurant not found."));

        // Act
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> restaurantController.getRestaurantById(restaurantId),
                "Expected getRestaurantById() to throw a ResponseStatusException, but it didn't."
        );

        // Assert
        assertTrue(Objects.requireNonNull(exception.getMessage()).contains("Restaurant not found."));
    }

    @Test
    void registerRestaurant() throws Exception {
        // Arrange
        when(restaurantService.createRestaurant(any())).thenReturn(restaurant);

        // Act
        MvcResult result = mockMvc.perform(post(API_PATH)
                        .content(objectMapper.writeValueAsString(restaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(restaurant));
    }

    @Test
    void updateRestaurant() throws Exception {
        // Arrange
        when(restaurantService.updateRestaurant(any(), any())).thenReturn(restaurant);

        // Act
        MvcResult result = mockMvc.perform(patch(API_PATH + restaurant.getId())
                        .content(objectMapper.writeValueAsString(restaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(restaurant));
    }

    @Test
    void deleteRestaurant() throws Exception {
        // Arrange
        when(restaurantService.deleteRestaurantById(any())).thenReturn(restaurant);

        // Act
        MvcResult result = mockMvc.perform(delete(API_PATH + restaurant.getId())
                        .content(objectMapper.writeValueAsString(restaurant))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(restaurant));
    }
}
