package org.phoneapp.repository;


import org.phoneapp.model.customer.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, Long> {

}