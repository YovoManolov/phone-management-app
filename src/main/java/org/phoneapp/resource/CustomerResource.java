package org.phoneapp.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.phoneapp.model.Customer;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phoneapp.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@RolesAllowed({"user", "admin"})
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection
public class CustomerResource {

    @Inject
    CustomerRepository customerRepository;  // Inject your Panache repository

    // Get all customers
    @GET
    public List<Customer> getAllCustomers() {
        return customerRepository.listAll();  // Fetches all customers
    }

    // Get customer by id
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Long id) {
        Optional<Customer> customer = customerRepository.findByIdOptional(id);  // Fetches customer by ID
        return customer.map(Response::ok)  // Response.ok() returns a ResponseBuilder
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))  // Only set status for NOT_FOUND
                .build();  // Call build() only once, at the end
    }

    // Create a new customer
    @POST
    @Transactional
    public Response createCustomer(Customer customer) {
        customerRepository.persist(customer);  // Saves customer to the database
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    // Update an existing customer
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCustomer(@PathParam("id") Long id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByIdOptional(id);
        if (existingCustomer.isPresent()) {
            customer.setId(id);  // Ensure the ID remains the same
            customerRepository.persist(customer);  // Update the existing customer
            return Response.ok(customer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Delete a customer
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteCustomer(@PathParam("id") Long id) {
        Optional<Customer> customer = customerRepository.findByIdOptional(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());  // Deletes the customer
            return Response.noContent().build();  // Successful deletion
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
