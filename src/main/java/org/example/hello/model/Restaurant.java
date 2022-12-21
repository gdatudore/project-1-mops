package org.example.hello.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Restaurant {

    @Id
    public String id;

    public String name;
    public List<String> menu;

    public Restaurant updateRestaurant(Restaurant other) {
        if (other.getName() != null) {
            this.name = other.name;
        }

        if (other.getMenu() != null) {
            this.menu = other.menu;
        }

        return this;
    }
}
