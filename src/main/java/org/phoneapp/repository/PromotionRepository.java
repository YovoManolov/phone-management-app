package org.phoneapp.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.phoneapp.model.Customer;
import org.phoneapp.model.Promotion;

@ApplicationScoped
public class PromotionRepository implements PanacheRepositoryBase<Promotion, Long> {

}