package org.phoneapp.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.phoneapp.model.subscription.Subscription;

@ApplicationScoped
public class SubscriptionRepository implements PanacheRepositoryBase<Subscription, Long> {

}