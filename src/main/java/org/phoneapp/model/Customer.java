package org.phoneapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customer", schema = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use auto-increment (identity column)
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
            name = "customer_subscription", // Name of the join table
            schema = "subscriptions",
            joinColumns = @JoinColumn(name = "customer_id"), // Column in the join table for the customer
            inverseJoinColumns = @JoinColumn(name = "subscription_id") // Column in the join table for the subscription
    )
    private Set<Subscription> subscriptions = new HashSet<>();
}