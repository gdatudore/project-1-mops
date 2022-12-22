package org.example.hello.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.example.hello.model.Meal;
import org.example.hello.repository.MealRepository;

@Service
public class MealService {
    private final MealRepository repository;

    @Autowired
    MealService(MealRepository mealRepository) {
        this.repository = mealRepository;
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
        repository.save(meal);
        return meal;
    }

    public Meal updateMeal(String mealId, Meal request) {
        Meal meal = this.getMealById(mealId);

        meal.updateMeal(request);

        repository.save(meal);
        return meal;
    }

    public Meal deleteMealById(String mealId) {
        Meal meal = this.getMealById(mealId);

        repository.deleteById(mealId);
        return meal;
    }
}