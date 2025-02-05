package org.phoneapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
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
    public String price;
    public String currency;
    public String discountApplied;
}
