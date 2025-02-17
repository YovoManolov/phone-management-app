package org.phoneapp.model.subscription;

import lombok.Getter;

@Getter
public enum SubscriptionType {
    PREPAID("prepaid"),
    POSTPAID("postpaid");

    private final String value;

    SubscriptionType(String value) {
        this.value = value;
    }

    public static SubscriptionType fromString(String dbValue) {
        for (SubscriptionType type : SubscriptionType.values()) {
            if (type.value.equalsIgnoreCase(dbValue)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown subscription type: " + dbValue);
    }
}

