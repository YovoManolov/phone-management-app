package org.phoneapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Promotion {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public Integer discount;
    public String discountType;
    public Integer numberOfVouchers;
}
