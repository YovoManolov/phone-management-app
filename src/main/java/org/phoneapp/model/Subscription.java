package org.phoneapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Subscription {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public Double price;
    public String subscriptionType;
    public String validity;
}
