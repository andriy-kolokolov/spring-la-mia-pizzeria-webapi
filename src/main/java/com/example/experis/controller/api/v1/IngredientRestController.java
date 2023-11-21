package com.example.experis.controller.api.v1;

import com.example.experis.model.Ingredient;
import com.example.experis.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ingredient")
public class IngredientRestController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping()
    public ResponseEntity<List<Ingredient>> getAllIngredients(
            @RequestParam(value = "name", required = false) String name
    ) {
        List<Ingredient> ingredients = (name != null && !name.trim().isEmpty()) ?
                ingredientService.findByName(name) :
                ingredientService.getAll();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id) {
        Optional<Ingredient> ingredient = Optional.ofNullable(ingredientService.getIngredientById(id));
        return ingredient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Ingredient> addIngredient(@RequestBody @Valid Ingredient ingredient) {
        Ingredient savedIngredient = ingredientService.saveGet(ingredient);
        return ResponseEntity.ok(savedIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(
            @PathVariable Long id,
            @RequestBody @Valid Ingredient ingredient
    ) {
        if (!ingredientService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ingredient.setId(id);
        Ingredient updatedIngredient = ingredientService.saveGet(ingredient);
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        if (ingredientService.existsById(id)) {
            ingredientService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
