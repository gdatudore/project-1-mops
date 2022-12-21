package org.example.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.example.hello.model.Restaurant;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

}