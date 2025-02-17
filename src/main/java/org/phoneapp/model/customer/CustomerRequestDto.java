package org.phoneapp.model.customer;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {
    private String name;
    private String address;
    private String gender;
    private Integer age;
    private String emailContactNumber;
    private Long productId;
    private Set<Long> subscriptionIds;
}
