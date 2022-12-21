package org.example.hello.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.hello.exception.NotFoundException;
import org.example.hello.model.Meal;
import org.example.hello.repository.MealRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    MealService(MealRepository MealRepository) {
        this.repository = MealRepository;
    }

    public Meal getMealById(String mealId) {
        Optional<Meal> meal = repository.findById(mealId);
        if (meal.isPresent()) {
            return meal.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found.");
    }

    public Meal getMealByName(String mealName) {
        Optional<Meal> meal = repository.findByName(mealName);
        if (meal.isPresent()) {
            return meal.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found.");
    }

    public List<Meal> getAllMeals() {
        return repository.findAll();
    }

    public Meal createMeal(Meal meal) {
        return repository.save(meal);
    }

    public Meal updateMeal(String mealId, Meal request) {
        Optional<Meal> optionalMeal = repository.findById(mealId);
        if (optionalMeal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.");
        }

        Meal Meal = optionalMeal.get();
        Meal.setName(request.name);
        Meal.setIngredients(request.ingredients);
        Meal.setPortionSize(request.portionSize);
        Meal.setPrice(request.price);

        return repository.save(Meal);
    }

    public Meal deleteMealById(String mealId) {
        Optional<Meal> optionalMeal = repository.findById(mealId);
        if (optionalMeal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal not found.");
        }

        Meal meal = optionalMeal.get();

        repository.deleteById(mealId);
        return meal;
    }
}
