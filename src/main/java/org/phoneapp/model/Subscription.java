package org.phoneapp.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Table(name = "subscription", schema = "subscriptions")
public class Subscription {

    @Id
    @Schema(hidden = true)
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
