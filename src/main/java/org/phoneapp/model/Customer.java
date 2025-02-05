package org.phoneapp.model;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String address;
    public Integer age   ;
    public String emailContactNumber;

    @ManyToOne
    private Product category;

}
