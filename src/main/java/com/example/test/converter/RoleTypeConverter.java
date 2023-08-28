package com.example.test.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.example.test.enumeration.RoleType;

@Converter
public class RoleTypeConverter implements AttributeConverter<RoleType, String>{

	@Override
	public String convertToDatabaseColumn(RoleType attribute) {
		return attribute == null ? null : attribute.getRole();
	}

	@Override
	public RoleType convertToEntityAttribute(String dbData) {
		return RoleType.findByKey(dbData);
	}
	
}// RoleTypeConverter
