package org.phoneapp.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Table(name = "promotion", schema = "promotions")
public class Promotion {

    @Id
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String promocode;
    private Integer discount;
    @Column(name = "discount_type")
    private String discountType;
    @Column(name = "number_of_vouchers")
    private Integer numberOfVouchers;
}
