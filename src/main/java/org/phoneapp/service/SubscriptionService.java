package org.phoneapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.phoneapp.model.subscription.Subscription;
import org.phoneapp.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SubscriptionService {

    @Inject
    SubscriptionRepository subscriptionRepository;

    // Get all customers
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.listAll();  // Fetch all subscriptions from the database
    }

    // Get subscription by ID
    public Optional<Subscription> getSubscriptionById(Long id) {
        return Optional.ofNullable(subscriptionRepository.findById(id));  // Fetch subscription by ID
    }

    // Create a new subscription
    @Transactional
    public Subscription createSubscription(Subscription subscription) {
        subscriptionRepository.persist(subscription);  // Save the subscription to the database
        return subscription;
    }

    // Update an existing subscription
    @Transactional
    public Optional<Subscription> updateSubscription(Long id, Subscription updatedSubscription) {
        Subscription existingSubscription = subscriptionRepository.findById(id);
        if (existingSubscription == null) {
            return Optional.empty();  // Subscription not found
        }

        // Only update non-null fields
        if (updatedSubscription.getName() != null) existingSubscription.setName(updatedSubscription.getName());
        if (updatedSubscription.getPrice() != null) existingSubscription.setPrice(updatedSubscription.getPrice());
        if (updatedSubscription.getCurrency() != null)
            existingSubscription.setCurrency(updatedSubscription.getCurrency());
        if (updatedSubscription.getSubscriptionType() != null)
            existingSubscription.setSubscriptionType(updatedSubscription.getSubscriptionType());
        if (updatedSubscription.getValidity() != null)
            existingSubscription.setValidity(updatedSubscription.getValidity());

        return Optional.of(existingSubscription);  // Return the updated subscription
    }

    // Delete a subscription
    @Transactional
    public boolean deleteSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id);
        if (subscription != null) {
            subscriptionRepository.delete(subscription);  // Delete the subscription
            return true;  // Deletion successful
        }
        return false;  // Subscription not found
    }
}