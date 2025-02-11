package org.phoneapp.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Table(name = "customer", schema = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String address;
    public Integer age;

    @Column(name = "email_contact_number")
    public String emailContactNumber;

    @ManyToOne
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "customer_subscription",
            schema = "subscriptions",
            joinColumns = @JoinColumn(name = "customer_id"), // Column in the join table for the customer
            inverseJoinColumns = @JoinColumn(name = "subscription_id") // Column in the join table for the subscription
    )
    private Set<Subscription> subscriptions = new HashSet<>();
}