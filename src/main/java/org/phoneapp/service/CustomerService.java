package org.phoneapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.phoneapp.model.product.CustomerProduct;
import org.phoneapp.model.product.Product;
import org.phoneapp.model.customer.Customer;
import org.phoneapp.model.customer.CustomerRequestDto;
import org.phoneapp.model.subscription.Subscription;
import org.phoneapp.repository.CustomerRepository;
import org.phoneapp.repository.ProductRepository;
import org.phoneapp.repository.SubscriptionRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

        // Handle product association through the join table
        if (customerRequestDto.getProductId() != null) {
            Product product = productRepository.findById(customerRequestDto.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("Product with ID " + customerRequestDto.getProductId() + " not found.");
            }

            // Create a new CustomerProduct entry to represent the association
            CustomerProduct customerProduct = new CustomerProduct();
            customerProduct.setCustomer(customer);
            customerProduct.setProduct(product);
            customerProduct.setPurchaseDate(Timestamp.valueOf(LocalDateTime.now())); // Or use the purchase date from DTO if available
            customerProduct.setPurchasePrice(product.getPrice()); // You can set the purchase price from the DTO if needed
            customerProduct.setPromotionCodeApplied(false); // Similarly, set this based on the request DTO if available

            // Add the created CustomerProduct to the customer’s collection
            customer.getCustomerProducts().add(customerProduct);
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

            customer.setSubscriptions(subscriptions);
        }

        // Persist the customer along with its relationships
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
        if (customerRequestDto.getName() != null) existingCustomer.setName(customerRequestDto.getName());
        if (customerRequestDto.getAddress() != null) existingCustomer.setAddress(customerRequestDto.getAddress());
        if (customerRequestDto.getGender() != null) existingCustomer.setGender(customerRequestDto.getGender());
        if (customerRequestDto.getAge() != null) existingCustomer.setAge(customerRequestDto.getAge());
        if (customerRequestDto.getEmailContactNumber() != null) {
            existingCustomer.setEmailContactNumber(customerRequestDto.getEmailContactNumber());
        }

        // Handle product relationship with join table (CustomerProduct)
        if (customerRequestDto.getProductId() != null) {
            Product product = productRepository.findById(customerRequestDto.getProductId());
            if (product == null) {
                throw new IllegalArgumentException("Product with ID " + customerRequestDto.getProductId() + " not found.");
            }

            // Check if product already exists in the customer's customerProducts set
            Optional<CustomerProduct> existingProductAssociation = existingCustomer.getCustomerProducts().stream()
                    .filter(cp -> cp.getProduct().getId().equals(product.getId()))
                    .findFirst();

            if (existingProductAssociation.isPresent()) {
                // If the product association already exists, no need to add a new one
                CustomerProduct customerProduct = existingProductAssociation.get();
                // Optionally, update fields like purchaseDate, purchasePrice, etc.
            } else {
                // If not, create a new association in the join table
                CustomerProduct customerProduct = new CustomerProduct();
                customerProduct.setCustomer(existingCustomer);
                customerProduct.setProduct(product);
                customerProduct.setPurchaseDate(Timestamp.valueOf(LocalDateTime.now())); // or use DTO value
                customerProduct.setPurchasePrice(product.getPrice()); // or use DTO value
                customerProduct.setPromotionCodeApplied(false); // or use DTO value TODO
                existingCustomer.getCustomerProducts().add(customerProduct);
            }
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

        // Persist the updated customer (via the transactional context, the changes will be saved)
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