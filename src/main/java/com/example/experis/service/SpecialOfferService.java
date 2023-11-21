package com.example.experis.service;

import com.example.experis.model.SpecialOffer;
import com.example.experis.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialOfferService {
    private final SpecialOfferRepository specialOfferRepository;

    @Autowired
    public SpecialOfferService(SpecialOfferRepository specialOfferRepository) {
        this.specialOfferRepository = specialOfferRepository;
    }

    public List<SpecialOffer> getOffersByPizzaId(Long pizzaId) {
        return specialOfferRepository.findByPizzaId(pizzaId);
    }

    public SpecialOffer saveOffer(SpecialOffer offer) {
        return specialOfferRepository.save(offer);
    }

    public List<SpecialOffer> getAll() {
        return specialOfferRepository.findAll();
    }

    public SpecialOffer findById(Long id) {
        return specialOfferRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Pizza with id " + id + " does not exist")
        );
    }

    public void delete(SpecialOffer offer) {
        specialOfferRepository.delete(offer);
        specialOfferRepository.flush(); // flush the changes to the database
    }
}
