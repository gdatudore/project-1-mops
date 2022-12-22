package org.example.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import org.example.hello.model.Meal;
import org.example.hello.repository.MealRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MealServiceTest {

    private Meal meal;

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    MealService mealService;

    @BeforeEach
    void setUp() {
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
    void getMealById() {
        // Arrange
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));

        // Act
        Meal result = mealService.getMealById(meal.getId());

        // Assert
        assertEquals(result, meal);
    }

    @Test
    void getMealByName() {
        // Arrange
        when(mealRepository.findByName(any())).thenReturn(Optional.ofNullable(meal));

        // Act
        Meal result = mealService.getMealByName(meal.getName());

        // Assert
        assertEquals(result, meal);
    }

    @Test
    void getAllMealsReturnsEmptyList() {
        // Arrange

        // Act
        var result = mealService.getAllMeals();

        // Assert
        assertEquals(result.size(), 0);
    }

    @Test
    void createMeal() {
        // Arrange
        when(mealRepository.save(meal)).thenReturn(meal);

        // Act
        Meal result = mealService.createMeal(meal);

        // Assert
        assertEquals(result.getRestaurantId(), meal.getRestaurantId());
        assertEquals(result.getName(), meal.getName());
        assertEquals(result.getIngredients(), meal.getIngredients());
        assertEquals(result.getPrice(), meal.getPrice());
        assertEquals(result.getPortionSize(), meal.getPortionSize());
    }

    @Test
    void updateMeal() {
        // Arrange
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));

        Meal other = new Meal();
        other.setName("Spaghetti Bolognese");
        List<String> ingredients = List.of("spaghetti", "beef", "parmesan", "tomato");
        other.setIngredients(ingredients);
        other.setPrice(20f);
        other.setPortionSize(250);
        other.setRestaurantId("fdsfadsfewsds2342f");

        // Act
        Meal result = mealService.updateMeal(meal.getId(), other);

        // Assert
        assertEquals(result.getName(), other.getName());
        assertEquals(result.getIngredients(), other.getIngredients());
        assertEquals(result.getPrice(), other.getPrice());
        assertEquals(result.getPortionSize(), other.getPortionSize());
        assertEquals(result.getRestaurantId(), other.getRestaurantId());
    }

    @Test
    void getMealByIdThrowsOnEmpty() {
        when(mealRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> mealService.getMealById(meal.getId()),
                "Expected getMealById() to throw a ResponseStatusException, but it didn't."
        );
    }

    @Test
    void getMealByNameThrowsOnEmpty() {
        when(mealRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> mealService.getMealByName(meal.getName()),
                "Expected getMealByName() to throw a ResponseStatusException, but it didn't."
        );
    }

    @Test
    void deleteMealById() {
        // Arrange
        when(mealRepository.findById(any())).thenReturn(Optional.ofNullable(meal));

        // Act
        Meal result = mealService.deleteMealById(meal.getId());

        // Assert
        verify(mealRepository, times(1)).deleteById(eq(meal.getId()));
        assertEquals(result, meal);
    }
}
