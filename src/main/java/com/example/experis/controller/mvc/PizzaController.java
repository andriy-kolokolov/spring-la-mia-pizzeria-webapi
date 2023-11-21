package com.example.experis.controller.mvc;

import com.example.experis.model.Ingredient;
import com.example.experis.service.IngredientService;
import com.example.experis.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.experis.model.Pizza;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    private final PizzaService pizzaService;
    private final IngredientService ingredientService;

    @Autowired
    public PizzaController(PizzaService pizzaService, IngredientService ingredientService) {
        this.pizzaService = pizzaService;
        this.ingredientService = ingredientService;
    }

    @GetMapping()
    public String index(
            @RequestParam(value = "name", required = false) String name,
            Model model
    ) {
        List<Pizza> pizzas;

        if (name != null && !name.trim().isEmpty()) {
            pizzas = pizzaService.findByName(name);
        } else {
            pizzas = pizzaService.getAll();
        }

        model.addAttribute("pizzas", pizzas);
        model.addAttribute("route", "pizza");

        return "pizza/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("pizza", pizzaService.getPizzaById(id));
        model.addAttribute("route", "pizza");

        return "pizza/show";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("route", "pizza");

        return "pizza/create";
    }

    @PostMapping("/add")
    public String addPizza(
            @ModelAttribute @Valid Pizza pizza,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("route", "pizza");

        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", pizza); // Add the Pizza object to the model
            return "pizza/create"; // Return to the form page with validation errors
        }

        pizzaService.save(pizza);
        redirectAttributes.addFlashAttribute("message", "Pizza added successfully!");
        return "redirect:/pizza";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Pizza pizza = pizzaService.getPizzaById(id);
        List<Ingredient> ingredients = ingredientService.getAll();

        if (pizza == null) {
            return "redirect:/pizza";
        }
        model.addAttribute("pizza", pizza);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("route", "pizza");

        return "pizza/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePizza(
            @PathVariable Long id,
            @ModelAttribute @Valid Pizza pizza,
            BindingResult bindingResult,
            Model model,
            @RequestParam(required = false) List<Long> ingredients,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", pizza);
            return "pizza/edit"; // Return to the edit form with validation errors
        }

        Pizza existingPizza = pizzaService.getPizzaById(id);
        if (existingPizza == null) {
            return "redirect:/pizza"; // Redirect if the pizza does not exist
        }

        // Update fields of the existing pizza
        existingPizza.setName(pizza.getName());
        existingPizza.setDescription(pizza.getDescription());
        existingPizza.setUrl(pizza.getUrl());
        existingPizza.setPrice(pizza.getPrice());

        // Handle ingredients
        Set<Ingredient> selectedIngredients = new HashSet<>();
        if (ingredients != null) {
            for (Long ingredientId : ingredients) {
                Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
                if (ingredient != null) {
                    selectedIngredients.add(ingredient);
                }
            }
        }
        existingPizza.setIngredients(selectedIngredients);

        pizzaService.save(existingPizza); // Save the updated pizza

        redirectAttributes.addFlashAttribute("message", "Pizza updated successfully!");
        return "redirect:/pizza";
    }

    @PostMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (id != null) {
            pizzaService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Pizza deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Pizza not found.");
        }
        return "redirect:/pizza";
    }

}
