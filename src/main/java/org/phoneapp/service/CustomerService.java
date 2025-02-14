package org.phoneapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.phoneapp.model.Customer;
import org.phoneapp.model.Product;
import org.phoneapp.model.Subscription;
import org.phoneapp.repository.CustomerRepository;
import org.phoneapp.repository.ProductRepository;
import org.phoneapp.repository.SubscriptionRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    SubscriptionRepository subscriptionRepository;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.listAll();  // Fetch all customers from the database
    }

    // Get customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id));  // Fetch customer by ID
    }

    // Create a new customer
    @Transactional
    public Customer createCustomer(Customer customer) {
        customerRepository.persist(customer);  // Save the customer to the database
        return customer;
    }

    // Update an existing customer
    @Transactional
    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id);
        if (existingCustomer == null) {
            return Optional.empty();  // Customer not found
        }

        // Only update non-null fields
        /* side note:
        *    Some frameworks (e.g., JPA/Hibernate) automatically optimize updates
        *    by generating SQL only for changed fields. In such cases, blind updates
        *    at the application level might not result in unnecessary database writes.
        * */
        if (updatedCustomer.getName() != null) existingCustomer.setName(updatedCustomer.getName());
        if (updatedCustomer.getAddress() != null) existingCustomer.setAddress(updatedCustomer.getAddress());
        if (updatedCustomer.getGender() != null) existingCustomer.setGender(updatedCustomer.getGender());
        if (updatedCustomer.getAge() != null) existingCustomer.setAge(updatedCustomer.getAge());
        if (updatedCustomer.getEmailContactNumber() != null) existingCustomer.setEmailContactNumber(updatedCustomer.getEmailContactNumber());

        // Handle product relationship
        if (updatedCustomer.getProduct() != null) {
            Product product = productRepository.findById(updatedCustomer.getProduct().getId());
            if (product != null) {
                existingCustomer.setProduct(product);
            }
        }

        // Handle subscriptions
        if (updatedCustomer.getSubscriptions() != null && !updatedCustomer.getSubscriptions().isEmpty()) {
            Set<Subscription> subscriptions = updatedCustomer.getSubscriptions()
                    .stream()
                    .map(sub -> subscriptionRepository.findById(sub.getId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            existingCustomer.setSubscriptions(subscriptions);
        }

        return Optional.of(existingCustomer);  // Return the updated customer
    }

    // Delete a customer
    @Transactional
    public boolean deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id);
        if (customer != null) {
            customerRepository.delete(customer);  // Delete the customer
            return true;  // Deletion successful
        }
        return false;  // Customer not found
    }
}