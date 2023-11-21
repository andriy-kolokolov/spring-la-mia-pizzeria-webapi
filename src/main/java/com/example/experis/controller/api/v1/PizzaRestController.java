package com.example.experis.controller.api.v1;

import com.example.experis.model.Ingredient;
import com.example.experis.model.Pizza;
import com.example.experis.request.UpdatePizzaRequest;
import com.example.experis.service.IngredientService;
import com.example.experis.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/pizza")
public class PizzaRestController {

    private final PizzaService pizzaService;
    private final IngredientService ingredientService;

    @Autowired
    public PizzaRestController(PizzaService pizzaService, IngredientService ingredientService) {
        this.pizzaService = pizzaService;
        this.ingredientService = ingredientService;
    }

    @GetMapping()
    public ResponseEntity<List<Pizza>> getAllPizzas(
            @RequestParam(value = "name", required = false) String name
    ) {
        List<Pizza> pizzas = (name != null && !name.trim().isEmpty()) ?
                pizzaService.findByName(name) :
                pizzaService.getAll();
        return ResponseEntity.ok(pizzas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable Long id) {
        Pizza pizza = pizzaService.getPizzaById(id);
        return pizza != null ? ResponseEntity.ok(pizza) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPizza(@RequestBody @Valid Pizza pizza) {
        pizzaService.save(pizza);
        return ResponseEntity.ok("Pizza added successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePizza(
            @PathVariable Long id, @RequestBody @Valid UpdatePizzaRequest updateRequest
    ) {
        Pizza existingPizza = pizzaService.getPizzaById(id);
        if (existingPizza == null) {
            return ResponseEntity.notFound().build();
        }

        existingPizza.setName(updateRequest.getPizza().getName());
        existingPizza.setDescription(updateRequest.getPizza().getDescription());
        existingPizza.setUrl(updateRequest.getPizza().getUrl());
        existingPizza.setPrice(updateRequest.getPizza().getPrice());

        // Handle ingredients
        Set<Ingredient> selectedIngredients = new HashSet<>();
        for (Long ingredientId : updateRequest.getIngredientIds()) {
            Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
            if (ingredient != null) {
                selectedIngredients.add(ingredient);
            }
        }
        existingPizza.setIngredients(selectedIngredients);

        pizzaService.save(existingPizza);
        return ResponseEntity.ok("Pizza updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePizza(@PathVariable Long id) {
        if (pizzaService.getPizzaById(id) != null) {
            pizzaService.delete(id);
            return ResponseEntity.ok("Pizza deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
