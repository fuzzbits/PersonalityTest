package com.afranco.personalitest.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/***
 * Converts Char ('Y' or 'N') and converts to boolean
 * 
 * @author André Franco
 *
 */
@Converter(autoApply = true)
public class CharToBooleanConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		return attribute ? "Y": "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		return "Y".equals(dbData);
	}

}