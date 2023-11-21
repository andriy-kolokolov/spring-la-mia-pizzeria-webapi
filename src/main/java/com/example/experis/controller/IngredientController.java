package com.example.experis.controller;

import com.example.experis.model.Ingredient;
import com.example.experis.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping()
    public String index(
            @RequestParam(value = "name", required = false) String name,
            Model model
    ) {
        List<Ingredient> ingredients;

        if (name != null && !name.trim().isEmpty()) {
            ingredients = ingredientService.findByName(name);
        } else {
            ingredients = ingredientService.getAll();
        }

        model.addAttribute("ingredients", ingredients);
        model.addAttribute("route", "ingredient");

        return "ingredient/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if (ingredient == null) {
            return "redirect:/ingredient";
        }
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("route", "ingredient");

        return "ingredient/show";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("route", "ingredient");

        return "ingredient/create";
    }

    @PostMapping("/add")
    public String addIngredient(
            @ModelAttribute @Valid Ingredient ingredient,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("route", "ingredient");

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredient", ingredient);
            return "ingredient/create";
        }

        ingredientService.save(ingredient);
        redirectAttributes.addFlashAttribute("message", "Ingredient added successfully!");
        return "redirect:/ingredient";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        if (ingredient == null) {
            return "redirect:/ingredient";
        }
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("route", "ingredient");
        return "ingredient/edit";
    }

    @PostMapping("/update/{id}")
    public String updateIngredient(
            @PathVariable Long id,
            @ModelAttribute @Valid Ingredient ingredient,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredient", ingredient);
            return "ingredient/edit";
        }

        Ingredient existingIngredient = ingredientService.getIngredientById(id);
        if (existingIngredient == null) {
            return "redirect:/ingredient";
        }
        existingIngredient.setName(ingredient.getName());

        ingredientService.save(existingIngredient);
        redirectAttributes.addFlashAttribute("message", "Ingredient updated successfully!");
        return "redirect:/ingredient";
    }

    @PostMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (id != null && ingredientService.existsById(id)) {
            ingredientService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Ingredient deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Ingredient not found.");
        }
        return "redirect:/ingredient";
    }

}