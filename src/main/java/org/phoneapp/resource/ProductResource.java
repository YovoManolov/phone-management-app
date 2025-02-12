package org.phoneapp.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phoneapp.model.Product;
import org.phoneapp.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@RolesAllowed({"user", "admin"})
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection  // For reflection support if you're using native compilation
public class ProductResource {

    @Inject
    ProductRepository productRepository;  // Inject your Panache repository

    // Get all customers
    @GET
    public List<Product> getAllProducts() {
        return productRepository.listAll();  // Fetches all customers
    }

    // Get customer by id
    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Long id) {
        Optional<Product> product = productRepository.findByIdOptional(id);  // Fetches customer by ID
        return product.map(Response::ok)  // Response.ok() returns a ResponseBuilder
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))  // Only set status for NOT_FOUND
                .build();  // Call build() only once, at the end
    }


    // Create a new customer
    @POST
    public Response createProduct(Product product) {
        productRepository.persist(product);  // Saves product to the database
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    // Update an existing product
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Long id, Product product) {
        Optional<Product> existingProduct = productRepository.findByIdOptional(id);
        if (existingProduct.isPresent()) {
            product.id = id;  // Ensure the ID remains the same
            productRepository.persist(product);  // Update the existing product
            return Response.ok(product).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Delete a product
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        Optional<Product> product = productRepository.findByIdOptional(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());  // Deletes the product
            return Response.noContent().build();  // Successful deletion
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
