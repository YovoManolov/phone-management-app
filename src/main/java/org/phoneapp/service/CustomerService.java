package org.phoneapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.phoneapp.model.Product;
import org.phoneapp.model.customer.Customer;
import org.phoneapp.model.customer.CustomerRequestDto;
import org.phoneapp.model.subscription.Subscription;
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
    public Customer createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = new Customer();
        customer.setName(customerRequestDto.getName());
        customer.setAddress(customerRequestDto.getAddress());
        customer.setGender(customerRequestDto.getGender());
        customer.setAge(customerRequestDto.getAge());
        customer.setEmailContactNumber(customerRequestDto.getEmailContactNumber());

        if (customerRequestDto.getProductId() != null) {
            Product product = productRepository.findById(customerRequestDto.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("Product with ID " + customerRequestDto.getProductId() + " not found.");
            }
            customer.setProduct(product);
        }

        if (customerRequestDto.getSubscriptionIds() != null && !customerRequestDto.getSubscriptionIds().isEmpty()) {
            Set<Subscription> subscriptions = customerRequestDto.getSubscriptionIds().stream()
                    .map(subscriptionRepository::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            if (subscriptions.size() != customerRequestDto.getSubscriptionIds().size()) {
                throw new IllegalArgumentException("One or more subscriptions not found.");
            }
            customer.setSubscriptions(subscriptions);
        }

        customerRepository.persist(customer);  // Save the customer to the database
        return customer;
    }

    // Update an existing customer
    @Transactional
    public Optional<Customer> updateCustomer(Long id,  CustomerRequestDto customerRequestDto ) {

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
        if (customerRequestDto.getName() != null) existingCustomer.setName(customerRequestDto.getName());
        if (customerRequestDto.getAddress() != null) existingCustomer.setAddress(customerRequestDto.getAddress());
        if (customerRequestDto.getGender() != null) existingCustomer.setGender(customerRequestDto.getGender());
        if (customerRequestDto.getAge() != null) existingCustomer.setAge(customerRequestDto.getAge());
        if (customerRequestDto.getEmailContactNumber() != null) existingCustomer.setEmailContactNumber(
                customerRequestDto.getEmailContactNumber());

        // Handle product relationship
        if (customerRequestDto.getProductId() != null) {
            Product product = productRepository.findById(customerRequestDto.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("Product with ID " + customerRequestDto.getProductId() + " not found.");
            }
            existingCustomer.setProduct(product);
        }

        // Handle subscriptions
        if (customerRequestDto.getSubscriptionIds() != null && !customerRequestDto.getSubscriptionIds().isEmpty()) {
            Set<Subscription> subscriptions = customerRequestDto.getSubscriptionIds().stream()
                    .map(subscriptionRepository::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            if (subscriptions.size() != customerRequestDto.getSubscriptionIds().size()) {
                throw new IllegalArgumentException("One or more subscriptions not found.");
            }

            existingCustomer.setSubscriptions(subscriptions);
        }

        //no need to explicitly call persist. JPA will generate sql statement because of the Transactional annotation.
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