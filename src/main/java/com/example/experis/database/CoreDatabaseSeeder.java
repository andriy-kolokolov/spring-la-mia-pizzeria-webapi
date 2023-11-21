package com.example.experis.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreDatabaseSeeder {

    @Autowired
    private PizzaSeeder pizzaSeeder;

    @Autowired
    private IngredientSeeder ingredientSeeder;

    @Autowired
    private PizzaIngredientSeeder pizzaIngredientSeeder;

    @Bean
    CommandLineRunner runCoreSeeder() {
        return args -> {
            pizzaSeeder.seedPizzas();
            ingredientSeeder.seedIngredients();
            pizzaIngredientSeeder.seedPizzaIngredients();
        };
    }
}
