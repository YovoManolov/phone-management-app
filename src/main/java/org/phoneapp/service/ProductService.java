package org.phoneapp.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.phoneapp.model.Product;
import org.phoneapp.model.customer.Customer;
import org.phoneapp.model.customer.CustomerRequestDto;
import org.phoneapp.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    PromotionService promotionService;

    @Inject
    CustomerService customerService;


    // Get all customers
    public List<Product> getAllProducts() {
        return productRepository.listAll();  // Fetch all customers from the database
    }

    // Get customer by ID
    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(productRepository.findById(id));  // Fetch customer by ID
    }

    // Create a new customer
    @Transactional
    public Product createProduct(Product customer) {
        productRepository.persist(customer);  // Save the customer to the database
        return customer;
    }

    // Update an existing customer
    @Transactional
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id);
        if (existingProduct == null) {
            return Optional.empty();  // Product not found
        }

        // Only update non-null fields
        if (updatedProduct.getName() != null) existingProduct.setName(updatedProduct.getName());
        if (updatedProduct.getPrice() != null) existingProduct.setPrice(updatedProduct.getPrice());
        if (updatedProduct.getCurrency() != null) existingProduct.setCurrency(updatedProduct.getCurrency());
        if (updatedProduct.getDiscountApplied() != null) existingProduct.setDiscountApplied(updatedProduct.getDiscountApplied());

        return Optional.of(existingProduct);  // Return the updated customer
    }

    // Delete a customer
    @Transactional
    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id);
        if (product != null) {
            productRepository.delete(product);  // Delete the customer
            return true;
        }
        return false;
    }

    @Transactional
    public void purchaseProduct(Long customerId, Long productId, Long promotionId) {
        // Fetch the Customer
        promotionService.decrementNumberOfVouchersByPromotionId(promotionId);

        Optional<Customer> optCustomer = customerService.getCustomerById(customerId);
        if (optCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " not found.");
        }
        Customer customer = optCustomer.get();
        CustomerRequestDto customerRequestDto = CustomerRequestDto.fromCustomer(customer);


        // Replace the existing product or assign a new one
        customerRequestDto.setProductId(productId);

        // Persist the updated customer
        customerService.updateCustomer(customerId ,customerRequestDto);
    }
}