package org.phoneapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "subscription", schema = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public BigDecimal price;
    @Column(name="subscription_type")
    public String subscriptionType;
    public String validity;

    @ManyToMany(mappedBy = "subscriptions")
    private Set<Customer> customers = new HashSet<>();

}
