package org.phoneapp.model.product;

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
@Table(name = "product", schema = "products")
public class Product {

    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    @Column(length = 3, nullable = false)
    private String currency;
    @Column(name="discount_applied")
    private Boolean discountApplied;

    /*
    ✅ orphanRemoval = true in @OneToMany: Ensures when a CustomerProduct
       is removed, it is automatically deleted from the database.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CustomerProduct> customerProducts = new HashSet<>();
}



