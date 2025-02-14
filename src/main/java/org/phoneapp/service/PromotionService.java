package org.phoneapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.phoneapp.model.Promotion;
import org.phoneapp.repository.PromotionRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PromotionService {

    @Inject
    PromotionRepository promotionRepository;

    // Get all promotions
    public List<Promotion> getAllPromotions() {
        return promotionRepository.listAll();  // Fetch all promotions from the database
    }

    // Get promotion by ID
    public Optional<Promotion> getPromotionById(Long id) {
        return Optional.ofNullable(promotionRepository.findById(id));  // Fetch promotion by ID
    }

    // Create a new promotion
    @Transactional
    public Promotion createPromotion(Promotion promotion) {
        promotionRepository.persist(promotion);  // Save the promotion to the database
        return promotion;
    }

    // Update an existing promotion
    @Transactional
    public Optional<Promotion> updatePromotion(Long id, Promotion updatedPromotion) {
        Promotion existingPromotion = promotionRepository.findById(id);
        if (existingPromotion == null) {
            return Optional.empty();  // Promotion not found
        }

        if (updatedPromotion.getPromocode() != null) existingPromotion.setPromocode(updatedPromotion.getPromocode());
        if (updatedPromotion.getDiscount() != null) existingPromotion.setDiscount(updatedPromotion.getDiscount());
        if (updatedPromotion.getDiscountType() != null) existingPromotion.setDiscountType(updatedPromotion.getDiscountType());
        if (updatedPromotion.getNumberOfVouchers() != null) existingPromotion.setNumberOfVouchers(updatedPromotion.getNumberOfVouchers());

        return Optional.of(existingPromotion);  // Return the updated promotion
    }

    // Delete a promotion
    @Transactional
    public boolean deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id);
        if (promotion != null) {
            promotionRepository.delete(promotion);  // Delete the promotion
            return true;  // Deletion successful
        }
        return false;  // Promotion not found
    }
}