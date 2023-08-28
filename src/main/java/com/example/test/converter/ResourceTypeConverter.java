package com.example.test.converter;

import com.example.test.enumeration.ResourceType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ResourceTypeConverter implements AttributeConverter<ResourceType, String> {

    @Override
    public String convertToDatabaseColumn(ResourceType attribute) {
        return attribute.getType();
    }

    @Override
    public ResourceType convertToEntityAttribute(String dbData) {
        return ResourceType.findOfKey(dbData);
    }
}// ResourceTypeConverter
