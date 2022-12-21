package org.example.hello.controller;

import org.example.hello.data.MealEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.example.hello.data.MealEntity;
import org.example.hello.data.MealRepository;
import org.example.hello.dto.Greeting;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MealController {

    @Autowired
    private MealRepository mealRepository;


    @GetMapping("/meal")
    @ResponseBody
    public List<MealEntity> getAllMeals() {
        List<MealEntity> entity = mealRepository.findAll();
        return entity;
    }

    @PostMapping("/meal")
    public MealEntity addNewMeal(@RequestBody MealEntity newMeal) {
        var check = mealRepository.findByName(newMeal.name);
        if(check == null)
            return mealRepository.save(newMeal);
        else
            return check;
    }
}