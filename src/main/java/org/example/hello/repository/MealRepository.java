package org.example.hello.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.example.hello.model.Meal;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {

    Optional<Meal> findByName(String name);
}
