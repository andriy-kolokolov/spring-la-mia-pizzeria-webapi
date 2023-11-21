package com.example.experis.database;


import com.example.experis.model.Pizza;
import com.example.experis.repository.PizzaRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class PizzaSeeder {

    private final PizzaRepository pizzaRepository;

    private static final Random random = new Random();

    private static final String[] INGREDIENTS = {
            "Mozzarella", "Tomato Sauce", "Pepperoni", "Onions",
            "Green Peppers", "Mushrooms", "Sausage", "Bacon",
            "Black Olives", "Pineapple", "Ham", "Spinach", "Cheddar Cheese",
            "Feta Cheese", "Gouda Cheese", "Roquefort Cheese", "Camembert Cheese",
    };

    // without duplicates list
    private static final String[] PIZZA_NAMES = {
            "Margherita", "Pepperoni", "Hawaiian", "Veggie",
            "Meat Lovers", "Supreme", "BBQ Chicken", "Buffalo Chicken",
            "Mushroom and Spinach", "Four Cheese",
            "Chicken Delight", "Chicken Tikka Masala",
    };

    public PizzaSeeder(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Bean
    CommandLineRunner seedPizzas() {
        return args -> {
            long count = pizzaRepository.count();
            if (count >= PIZZA_NAMES.length) {
                return; // Skip data generation if the threshold is reached
            }

            Faker faker = new Faker(new Locale("en-US"));

            for (int i = 0; i < PIZZA_NAMES.length; i++) {
                String name = PIZZA_NAMES[random.nextInt(PIZZA_NAMES.length)];

                String description = IntStream.range(0, 3 + random.nextInt(3)) // 3 to 5 ingredients
                        .mapToObj(n -> INGREDIENTS[random.nextInt(INGREDIENTS.length)])
                        .collect(Collectors.joining(", "));

                String url = getPizzaImageUrl(random.nextInt(6) + 1); // assuming 6 img are in img folder


                BigDecimal price = BigDecimal.valueOf(faker.number().randomDouble(2, 5, 30));

                Pizza pizza = new Pizza();
                pizza.setName(name);
                pizza.setDescription(description);
                pizza.setUrl(url);
                pizza.setPrice(price);

                pizzaRepository.save(pizza);
            }
        };
    }

    private String getPizzaImageUrl(int imageNumber) {
        return "/img/pizza_" + imageNumber + ".jpg";
    }
}
