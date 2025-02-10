package org.phoneapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product", schema = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public BigDecimal price;
    public String currency;
    @Column(name="discount_applied")
    public Boolean discountApplied;
}



