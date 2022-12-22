package org.example.hello.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.example.hello.model.Restaurant;
import org.example.hello.repository.RestaurantRepository;
@Service
public class RestaurantService {
    private final RestaurantRepository repository;
    @Autowired
    RestaurantService(RestaurantRepository restaurantRepository) {
        this.repository = restaurantRepository;
    }
    public List<Restaurant> getAllRestaurants() {
        return repository.findAll();
    }
    public Restaurant getRestaurantById(String restaurantId) {
        Optional<Restaurant> restaurant = repository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found.");
        }
        return restaurant.get();
    }
    public Restaurant createRestaurant(Restaurant restaurant) {
        if (restaurant.name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Restaurant name cannot be empty.");
        }
        if (restaurant.menu == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Restaurant menu cannot be empty.");
        }
        return repository.save(restaurant);
    }

    public Restaurant updateRestaurant(String restaurantId, Restaurant request) {
        Restaurant restaurant = this.getRestaurantById(restaurantId);

        restaurant.updateRestaurant(request);

        repository.save(restaurant);
        return restaurant;
    }

    public Restaurant deleteRestaurantById(String restaurantId) {
        Optional<Restaurant> optionalRestaurant = repository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found.");
        }
        Restaurant restaurant = optionalRestaurant.get();
        repository.deleteById(restaurantId);
        return restaurant;
    }
}