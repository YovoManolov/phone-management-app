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
public class Subscription {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public Double price;
    public String subscriptionType;
    public String validity;

    @OneToMany
    private Subscription subscription;

    @ManyToMany(mappedBy = "customers")
    private Set<Customer> customers = new HashSet<>();

}
