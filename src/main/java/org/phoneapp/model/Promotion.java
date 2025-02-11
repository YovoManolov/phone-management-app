package org.phoneapp.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String promocode;
    public Integer discount;
    @Column(name = "discount_type")
    public String discountType;
    @Column(name = "number_of_vouchers")
    public Integer numberOfVouchers;
}
