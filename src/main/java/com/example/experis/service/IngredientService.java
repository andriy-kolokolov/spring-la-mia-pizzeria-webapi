package com.example.experis.service;

import com.example.experis.model.Ingredient;
import com.example.experis.model.Pizza;
import com.example.experis.repository.IngredientRepository;
import com.example.experis.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Ingredient with id " + id + " does not exist")
                );
    }

    public boolean existsById(Long id) {
        return ingredientRepository.existsById(id);
    }

    //findByName
    public List<Ingredient> findByName(String name) {
        return ingredientRepository.findByName(name);
    }

    public void save(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }

}