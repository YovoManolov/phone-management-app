package org.phoneapp.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phoneapp.model.Promotion;
import org.phoneapp.repository.PromotionRepository;

import java.util.List;
import java.util.Optional;

@RolesAllowed({"user", "admin"})
@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection  // For reflection support if you're using native compilation
public class PromotionResource {

    @Inject
    PromotionRepository promotionRepository;  // Inject your Panache repository

    // Get all promotions
    @GET
    public List<Promotion> getAllPromotions() {
        return promotionRepository.listAll();  // Fetches all promotions
    }

    // Get promotion by id
    @GET
    @Path("/{id}")
    public Response getPromotionById(@PathParam("id") Long id) {
        Optional<Promotion> promotion = promotionRepository.findByIdOptional(id);  // Fetches promotion by ID
        return promotion.map(Response::ok)  // Response.ok() returns a ResponseBuilder
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))  // Only set status for NOT_FOUND
                .build();  // Call build() only once, at the end
    }


    // Create a new promotion
    @POST
    public Response createPromotion(Promotion promotion) {
        promotionRepository.persist(promotion);  // Saves promotion to the database
        return Response.status(Response.Status.CREATED).entity(promotion).build();
    }

    // Update an existing promotion
    @PUT
    @Path("/{id}")
    public Response updatePromotion(@PathParam("id") Long id, Promotion promotion) {
        Optional<Promotion> existingPromotion = promotionRepository.findByIdOptional(id);
        if (existingPromotion.isPresent()) {
            promotion.id = id;  // Ensure the ID remains the same
            promotionRepository.persist(promotion);  // Update the existing promotion
            return Response.ok(promotion).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Delete a promotion
    @DELETE
    @Path("/{id}")
    public Response deletePromotion(@PathParam("id") Long id) {
        Optional<Promotion> promotion = promotionRepository.findByIdOptional(id);
        if (promotion.isPresent()) {
            promotionRepository.delete(promotion.get());  // Deletes the promotion
            return Response.noContent().build();  // Successful deletion
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
