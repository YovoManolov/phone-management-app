package org.phoneapp.model.customer;

import lombok.*;

import org.phoneapp.model.subscription.Subscription;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
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

    // Custom mapper method
    public static CustomerRequestDto fromCustomer(Customer customer) {
        return CustomerRequestDto.builder()
                .name(customer.getName())
                .address(customer.getAddress())
                .gender(customer.getGender())
                .age(customer.getAge())
                .emailContactNumber(customer.getEmailContactNumber())
                .productId(customer.getProduct() != null ? customer.getProduct().getId() : null)
                .subscriptionIds(customer.getSubscriptions() != null ?
                        customer.getSubscriptions().stream()
                                .map(Subscription::getId)
                                .collect(Collectors.toSet()) :
                        null)
                .build();
    }
}
