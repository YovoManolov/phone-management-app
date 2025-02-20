package org.phoneapp.model.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.phoneapp.model.customer.Customer;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_product", schema = "products")
public class CustomerProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id") // Foreign key to Customer
    @JsonIgnore //fixes recursion issues on retrieval of data
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id") // Foreign key to Product
    @JsonIgnore //fixes recursion issues on retrieval of data
    private Product product;

    @Column(name = "purchase_date")
    private Timestamp purchaseDate;
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    @Column(name = "promotion_code_applied")
    private Boolean promotionCodeApplied;
}