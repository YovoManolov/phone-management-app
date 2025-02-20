package org.phoneapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.phoneapp.model.customer.Customer;
import org.phoneapp.model.subscription.Subscription;
import org.phoneapp.model.subscription.SubscriptionType;
import org.phoneapp.repository.CustomerRepository;
import org.phoneapp.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class SubscriptionService {

    @Inject
    SubscriptionRepository subscriptionRepository;

    @Inject
    CustomerRepository customerRepository;

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

    @Transactional
    public void subscribeToSubscription(Long customerId, Long subscriptionId) {
        // Find the customer and subscription
        Customer customer = customerRepository.findById(customerId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId);

        // Check if subscription exists and has a valid type
        if (subscription == null) {
            throw new IllegalArgumentException("Subscription not found");
        }

        // If the subscription is postpaid, we need to replace existing subscriptions
        if (subscription.getSubscriptionType() == SubscriptionType.POSTPAID) {
            // Remove all existing subscriptions, including prepaid
            customer.getSubscriptions().clear();

            // Add the postpaid subscription
            customer.getSubscriptions().add(subscription);
        } else if (subscription.getSubscriptionType() == SubscriptionType.PREPAID) {
            // If the subscription is prepaid, we can have multiple subscriptions
            // But if there are any postpaid subscriptions, we need to remove them
            Set<Subscription> subscriptions = customer.getSubscriptions();

            // Remove all postpaid subscriptions if there's a prepaid one
            subscriptions.removeIf(s -> s.getSubscriptionType() == SubscriptionType.POSTPAID);

            // Add the prepaid subscription
            subscriptions.add(subscription);
        }

        // Save the updated customer and subscriptions
        customerRepository.persist(customer);
    }
}