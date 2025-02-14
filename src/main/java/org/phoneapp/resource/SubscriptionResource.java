package org.phoneapp.resource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.phoneapp.model.Subscription;
import org.phoneapp.service.SubscriptionService;

import java.util.List;
import java.util.Optional;

@RolesAllowed({"user", "admin"})
@Path("/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterForReflection  // For reflection support if you're using native compilation
public class SubscriptionResource {

    @Inject
    SubscriptionService subscriptionService;

    // Get all customers
    @GET
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    // Get customer by id
    @GET
    @Path("/{id}")
    public Response getSubscriptionById(@PathParam("id") Long id) {
        Optional<Subscription> result = subscriptionService.getSubscriptionById(id);
        return result.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();

    }

    // Create a new customer
    @POST
    @Transactional
    public Response createSubscription(Subscription subscription) {
        Subscription createdSubscription = subscriptionService.createSubscription(subscription);
        return Response.status(Response.Status.CREATED).entity(createdSubscription).build();
    }

    // Update an existing subscription
    @PUT
    @Path("/{id}")
    public Response updateSubscription(@PathParam("id") Long id, Subscription updatedSubscription) {
        Optional<Subscription> result = subscriptionService.updateSubscription(id, updatedSubscription);
        return result.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    // Delete a subscription
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSubscription(@PathParam("id") Long id) {
        boolean deleted = subscriptionService.deleteSubscription(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
