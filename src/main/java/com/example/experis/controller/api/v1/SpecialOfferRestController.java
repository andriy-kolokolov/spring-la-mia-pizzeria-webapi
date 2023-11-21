package com.example.experis.controller.api.v1;

import com.example.experis.model.SpecialOffer;
import com.example.experis.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/special-offer")
public class SpecialOfferRestController {
    private final SpecialOfferService specialOfferService;

    @Autowired
    public SpecialOfferRestController(SpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<SpecialOffer>> getAllOffers() {
        Iterable<SpecialOffer> offers = specialOfferService.getAll();
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/add")
    public ResponseEntity<SpecialOffer> addOffer(@RequestBody SpecialOffer offer) {
        SpecialOffer savedOffer = specialOfferService.saveOffer(offer);
        return ResponseEntity.ok(savedOffer);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<SpecialOffer> getOffer(@PathVariable Long id) {
        Optional<SpecialOffer> offer = Optional.ofNullable(specialOfferService.findById(id));
        return offer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SpecialOffer> updateOffer(
            @PathVariable Long id,
            @RequestBody SpecialOffer offer
    ) {
        SpecialOffer existingOffer = specialOfferService.findById(id);
        if (existingOffer == null) {
            return ResponseEntity.notFound().build();
        }
        existingOffer.setStartDate(offer.getStartDate());
        existingOffer.setEndDate(offer.getEndDate());
        existingOffer.setTitle(offer.getTitle());
        SpecialOffer updatedOffer = specialOfferService.saveOffer(existingOffer);
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        SpecialOffer offer = specialOfferService.findById(id);
        if (offer != null) {
            specialOfferService.delete(offer);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
