package org.phoneapp.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.phoneapp.model.product.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<Product, Long> {

}