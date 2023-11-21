package com.example.experis.controller;

import com.example.experis.model.Pizza;
import com.example.experis.model.SpecialOffer;
import com.example.experis.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/special-offer")
public class SpecialOfferController {
    private final SpecialOfferService specialOfferService;

    @Autowired
    public SpecialOfferController(SpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }

    @GetMapping("/create")
    public String createOfferForm(@RequestParam("pizzaId") Long pizzaId, Model model) {
        model.addAttribute("specialOffer", new SpecialOffer());
        model.addAttribute("route", "pizza");
        model.addAttribute("pizzaId", pizzaId);
        return "specialOffer/create";
    }

    @PostMapping("/add")
    public String addOffer(@ModelAttribute SpecialOffer offer, Model model) {
        specialOfferService.saveOffer(offer);
        return "redirect:/pizza/" + offer.getPizza().getId(); // Redirect to the pizza's detail page
    }

    @GetMapping("/edit/{id}")
    public String editOfferForm(@PathVariable Long id, Model model) {
        SpecialOffer offer = specialOfferService.findById(id);
        if (offer == null) {
            return "redirect:/pizza"; // Redirect or show an error page if the offer is not found
        }
        model.addAttribute("route", "pizza");
        model.addAttribute("specialOffer", offer);
        return "specialOffer/edit";
    }

    @PostMapping("/update/{id}")
    public String updateOffer(
            @PathVariable Long id,
            @ModelAttribute SpecialOffer offer,
            RedirectAttributes redirectAttributes
    ) {
        SpecialOffer existingOffer = specialOfferService.findById(id);
        if (existingOffer == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Special offer not found.");
            return "redirect:/pizza";
        }
        // Update the existing offer with new values
        existingOffer.setStartDate(offer.getStartDate());
        existingOffer.setEndDate(offer.getEndDate());
        existingOffer.setTitle(offer.getTitle());
        specialOfferService.saveOffer(existingOffer);

        redirectAttributes.addFlashAttribute("message", "Special offer updated successfully!");
        return "redirect:/pizza/" + existingOffer.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteOffer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        SpecialOffer offer = specialOfferService.findById(id);
        if (offer != null) {
            specialOfferService.delete(offer);
            redirectAttributes.addFlashAttribute("message", "Special offer deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Special offer not found.");
        }
        return "redirect:/pizza";
    }
}
