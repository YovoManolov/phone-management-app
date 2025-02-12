package org.phoneapp.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phoneapp.model.Subscription;
import org.phoneapp.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

@RolesAllowed({"user", "admin"})
@Path("/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection  // For reflection support if you're using native compilation
public class SubscriptionResource {

    @Inject
    SubscriptionRepository subscriptionRepository;  // Inject your Panache repository

    // Get all subscriptions
    @GET
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.listAll();  // Fetches all subscriptions
    }

    // Get subscription by id
    @GET
    @Path("/{id}")
    public Response getSubscriptionById(@PathParam("id") Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findByIdOptional(id);  // Fetches subscription by ID
        return subscription.map(Response::ok)  // Response.ok() returns a ResponseBuilder
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND))  // Only set status for NOT_FOUND
                .build();  // Call build() only once, at the end
    }

    // Create a new subscription
    @POST
    public Response createSubscription(Subscription subscription) {
        subscriptionRepository.persist(subscription);  // Saves subscription to the database
        return Response.status(Response.Status.CREATED).entity(subscription).build();
    }

    // Update an existing subscription
    @PUT
    @Path("/{id}")
    public Response updateSubscription(@PathParam("id") Long id, Subscription subscription) {
        Optional<Subscription> existingSubscription = subscriptionRepository.findByIdOptional(id);
        if (existingSubscription.isPresent()) {
            subscription.id = id;  // Ensure the ID remains the same
            subscriptionRepository.persist(subscription);  // Update the existing subscription
            return Response.ok(subscription).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // Delete a subscription
    @DELETE
    @Path("/{id}")
    public Response deleteSubscription(@PathParam("id") Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findByIdOptional(id);
        if (subscription.isPresent()) {
            subscriptionRepository.delete(subscription.get());  // Deletes the subscription
            return Response.noContent().build();  // Successful deletion
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
