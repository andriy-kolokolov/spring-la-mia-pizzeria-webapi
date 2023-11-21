package com.example.experis.database;

import com.example.experis.model.Ingredient;
import com.example.experis.repository.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngredientSeeder {

    private final IngredientRepository ingredientRepository;

    private static final String[] INGREDIENTS = {
            "Mozzarella", "Tomato Sauce", "Pepperoni", "Onions",
            "Green Peppers", "Mushrooms", "Sausage", "Bacon",
            "Black Olives", "Pineapple", "Ham", "Spinach", "Cheddar Cheese",
            "Feta Cheese", "Gouda Cheese", "Roquefort Cheese", "Camembert Cheese",
    };

    public IngredientSeeder(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Bean
    CommandLineRunner seedIngredients() {
        return args -> {
            long count = ingredientRepository.count();
            if (count >= INGREDIENTS.length) {
                return; // Skip data generation if the threshold is reached
            }

            for (String ingredientName : INGREDIENTS) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientName);
                ingredientRepository.save(ingredient);
            }
        };
    }
}
