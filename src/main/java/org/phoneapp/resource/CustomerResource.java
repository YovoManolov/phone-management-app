package org.phoneapp.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phoneapp.model.customer.CustomerRequestDto;
import org.phoneapp.model.customer.Customer;
import org.phoneapp.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RolesAllowed({"user", "admin"})
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection
public class CustomerResource {

    @Inject
    CustomerService customerService;  // Inject your Panache repository

    // Get all customers
    @GET
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Get customer by id
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Long id) {
        Optional<Customer> result = customerService.getCustomerById(id);
        return result.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    // Create a new customer
    @POST
    public Response createCustomer(CustomerRequestDto customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return Response.status(Response.Status.CREATED).entity(createdCustomer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Long id, CustomerRequestDto updatedCustomer) {
        Optional<Customer> result = customerService.updateCustomer(id, updatedCustomer);
        return result.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        boolean deleted = customerService.deleteCustomer(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

}
