package org.phoneapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Promotion {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public Integer discount;
    public String discountType;
    public Integer numberOfVouchers;
}
