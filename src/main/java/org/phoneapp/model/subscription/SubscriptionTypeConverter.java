package org.phoneapp.model.subscription;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SubscriptionTypeConverter implements AttributeConverter<SubscriptionType, String> {

    @Override
    public String convertToDatabaseColumn(SubscriptionType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();  // Store lowercase in DB
    }

    @Override
    public SubscriptionType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return SubscriptionType.fromString(dbData);  // Convert lowercase DB value to Enum
    }
}
