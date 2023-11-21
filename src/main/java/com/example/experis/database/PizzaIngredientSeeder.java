package com.example.experis.database;

import com.example.experis.model.Ingredient;
import com.example.experis.model.Pizza;
import com.example.experis.repository.IngredientRepository;
import com.example.experis.repository.PizzaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class PizzaIngredientSeeder {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;

    private static final Random random = new Random();

    public PizzaIngredientSeeder(PizzaRepository pizzaRepository, IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Bean
    CommandLineRunner seedPizzaIngredients() {
        return args -> {
            List<Pizza> pizzas = pizzaRepository.findAll();
            List<Ingredient> ingredients = ingredientRepository.findAll();

            pizzas.forEach(pizza -> {
                Set<Ingredient> assignedIngredients = random.ints(2, 5)
                        .mapToObj(i -> ingredients.get(random.nextInt(ingredients.size())))
                        .collect(Collectors.toSet());
                pizza.setIngredients(assignedIngredients);
                pizzaRepository.save(pizza);
            });
        };
    }
}
