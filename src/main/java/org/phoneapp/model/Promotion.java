package org.phoneapp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
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
