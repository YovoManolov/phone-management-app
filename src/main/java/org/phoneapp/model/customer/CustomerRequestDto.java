package org.phoneapp.model.customer;

import lombok.*;

import org.phoneapp.model.subscription.Subscription;

import java.util.Collections;
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

        // Safely fetch the product ID (only if there's at least one product)
        Long productId = customer.getCustomerProducts().stream()
                .findFirst()
                .map(customerProduct -> customerProduct.getProduct().getId())
                .orElse(null); // Return null if no products exist

        // Map subscription IDs safely, ensuring null safety for subscriptions
        Set<Long> subscriptionIds = customer.getSubscriptions() == null || customer.getSubscriptions().isEmpty() ?
                Collections.emptySet() :
                customer.getSubscriptions().stream()
                        .map(Subscription::getId)
                        .collect(Collectors.toSet());

        return CustomerRequestDto.builder()
                .name(customer.getName())
                .address(customer.getAddress())
                .gender(customer.getGender())
                .age(customer.getAge())
                .emailContactNumber(customer.getEmailContactNumber())
                .productId(productId)
                .subscriptionIds(subscriptionIds)
                .build();
    }

}
