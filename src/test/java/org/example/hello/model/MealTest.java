package org.example.hello.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealTest {

    private Meal meal;

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
    void updateMeal() {
        // Arrange
        Meal other = new Meal();
        other.setName("Spaghetti Bolognese");
        List<String> ingredients = List.of("spaghetti", "beef", "tomato", "carrot");
        other.setIngredients(ingredients);
        other.setPrice(21f);
        other.setPortionSize(300);
        other.setRestaurantId("fdsfadsssfewsds2342f");

        // Act
        meal.updateMeal(other);

        // Assert
        assertEquals(meal.getName(), other.getName());
        assertEquals(meal.getIngredients(), other.getIngredients());
        assertEquals(meal.getPrice(), other.getPrice());
        assertEquals(meal.getPortionSize(), other.getPortionSize());
        assertEquals(meal.getRestaurantId(), other.getRestaurantId());
    }
}
