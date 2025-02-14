package org.phoneapp.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phoneapp.model.Promotion;
import org.phoneapp.service.PromotionService;

import java.util.List;
import java.util.Optional;

@RolesAllowed({"user", "admin"})
@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection  // For reflection support if you're using native compilation
public class PromotionResource {

    @Inject
    PromotionService promotionService;

    // Get all customers
    @GET
    public List<Promotion> getAllPromotions() {
        return promotionService.getAllPromotions();
    }

    // Get customer by id
    @GET
    @Path("/{id}")
    public Response getPromotionById(@PathParam("id") Long id) {
        Optional<Promotion> result = promotionService.getPromotionById(id);
        return result.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();

    }

    // Create a new customer
    @POST
    @Transactional
    public Response createPromotion(Promotion promotion) {
        Promotion createdPromotion = promotionService.createPromotion(promotion);
        return Response.status(Response.Status.CREATED).entity(createdPromotion).build();
    }

    // Update an existing promotion
    @PUT
    @Path("/{id}")
    public Response updatePromotion(@PathParam("id") Long id, Promotion updatedPromotion) {
        Optional<Promotion> result = promotionService.updatePromotion(id,updatedPromotion);
        return result.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    // Delete a promotion
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePromotion(@PathParam("id") Long id) {
        boolean deleted = promotionService.deletePromotion(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
