package org.phoneapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String address;
    public Integer age;
    public String emailContactNumber;

    @ManyToOne
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "customer_subscription", // Name of the join table
            joinColumns = @JoinColumn(name = "customer_id"), // Column in the join table for the student
            inverseJoinColumns = @JoinColumn(name = "subscription_id") // Column in the join table for the course
    )
    private Set<Subscription> subscriptions = new HashSet<>();


}
