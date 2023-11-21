package com.example.experis.service;

import com.example.experis.model.Pizza;
import com.example.experis.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> getAll() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizzaById(Long id) {
        return pizzaRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Pizza with id " + id + " does not exist")
                );
    }

    public List<Pizza> findByName(String name) {
        return pizzaRepository.findByNameContainingIgnoreCase(name);
    }

    public void save(Pizza pizza) {
        pizzaRepository.save(pizza);
    }

    public void delete(Long id) {
        pizzaRepository.deleteById(id);
    }

}