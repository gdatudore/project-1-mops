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
import java.util.List;
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
import org.example.hello.model.Meal;
import org.example.hello.service.MealService;

@SpringBootTest
class MealControllerTest {

    @Mock
    private MealService mealService;

    @InjectMocks
    private MealController mealController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private final String API_PATH = "/api/meal/";

    private Meal meal;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mealController).build();
        objectMapper = new ObjectMapper();

        meal = new Meal();
        meal.setId("gfddgf4325s01583f06eff");
        meal.setName("Spaghetti Carbonara");
        List<String> ingredients = List.of("spaghetti", "eggs", "parmesan", "pepper");
        meal.setIngredients(ingredients);
        meal.setPrice(22f);
        meal.setPortionSize(220);
        meal.setRestaurantId("fdsfadsfewsds2342f");
    }

    @Test
    void getMealById() throws Exception {
        // Arrange
        when(mealService.getMealById(any())).thenReturn(meal);

        // Act
        MvcResult result = mockMvc.perform(get(API_PATH + "/id/" + meal.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(meal));
    }

    @Test
    void getMealByName() throws Exception {
        // Arrange
        when(mealService.getMealByName(any())).thenReturn(meal);

        // Act
        MvcResult result = mockMvc.perform(get(API_PATH + "/name/" + meal.getName()))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(meal));
    }

    @Test
    void controller_PropagatesCascadedException() {
        // Arrange
        String mealId = "gjdfspoas34s01583f06eff";
        when(mealService.getMealById(any())).thenThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Meal not found."));

        // Act
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> mealController.getMealById(mealId),
                "Expected getMealById() to throw a ResponseStatusException, but it didn't."
        );

        // Assert
        assertTrue(Objects.requireNonNull(exception.getMessage()).contains("Meal not found."));
    }

    @Test
    void registerMeal() throws Exception {
        // Arrange
        when(mealService.createMeal(any())).thenReturn(meal);

        // Act
        MvcResult result = mockMvc.perform(post(API_PATH)
                        .content(objectMapper.writeValueAsString(meal))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(meal));
    }

    @Test
    void updateMeal() throws Exception {
        // Arrange
        when(mealService.updateMeal(any(), any())).thenReturn(meal);

        // Act
        MvcResult result = mockMvc.perform(patch(API_PATH + meal.getId())
                        .content(objectMapper.writeValueAsString(meal))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(meal));
    }

    @Test
    void deleteMeal() throws Exception {
        // Arrange
        when(mealService.deleteMealById(any())).thenReturn(meal);

        // Act
        MvcResult result = mockMvc.perform(delete(API_PATH + meal.getId())
                        .content(objectMapper.writeValueAsString(meal))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals(result.getResponse().getContentAsString(),
                objectMapper.writeValueAsString(meal));
    }
}
