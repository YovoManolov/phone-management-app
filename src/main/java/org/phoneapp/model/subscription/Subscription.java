package org.phoneapp.model.subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.phoneapp.model.customer.Customer;

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
    private Long id;

    private String name;
    private BigDecimal price;
    private String currency;

    @Column(name="subscription_type")
    @Convert(converter = SubscriptionTypeConverter.class)
    private SubscriptionType subscriptionType;

    private String validity;

    @ManyToMany(mappedBy = "subscriptions")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

}
