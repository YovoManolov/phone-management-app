package org.phoneapp.model.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.phoneapp.model.Product;
import org.phoneapp.model.subscription.Subscription;

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
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String gender;
    private Integer age;

    @Column(name = "email_contact_number")
    private String emailContactNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Product product;

    @ManyToMany
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JoinTable(
            name = "customer_subscription",
            schema = "subscriptions",
            joinColumns = @JoinColumn(name = "customer_id"), // Column in the join table for the customer
            inverseJoinColumns = @JoinColumn(name = "subscription_id") // Column in the join table for the subscription
    )
    private Set<Subscription> subscriptions = new HashSet<>();
}