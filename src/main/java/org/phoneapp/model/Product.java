package org.phoneapp.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Table(name = "product", schema = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public BigDecimal price;
    public String currency;
    @Column(name="discount_applied")
    public Boolean discountApplied;
}



