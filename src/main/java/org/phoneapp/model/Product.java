package org.phoneapp.model;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String price;
    public String currency;
    public String discountApplied;
}
