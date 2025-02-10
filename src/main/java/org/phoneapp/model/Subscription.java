package org.phoneapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subscription", schema = "subscriptions")
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
    public BigDecimal price;
    public String subscriptionType;
    public String validity;

    @ManyToMany(mappedBy = "subscriptions")
    private Set<Customer> customers = new HashSet<>();

}
