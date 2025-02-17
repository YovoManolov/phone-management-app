package org.phoneapp.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phoneapp.model.Product;
import org.phoneapp.service.ProductService;

import java.util.List;
import java.util.Optional;

@RolesAllowed({"user", "admin"})
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection  // For reflection support if you're using native compilation
public class ProductResource {

    @Inject
    ProductService productService;

    // Get all customers
    @GET
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get customer by id
    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Long id) {
        Optional<Product> result = productService.getProductById(id);
        return result.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();

    }

    // Create a new customer
    @POST
    public Response createProduct(Product product) {
        Product createdProduct = productService.createProduct(product);
        return Response.status(Response.Status.CREATED).entity(createdProduct).build();
    }

    // Update an existing product
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Long id, Product updatedProduct) {
        Optional<Product> result = productService.updateProduct(id,updatedProduct);
        return result.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    // Delete a product
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
