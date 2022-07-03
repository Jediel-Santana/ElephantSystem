package edu.uniaeso.ElephantSystem.modelo.converter;

import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Collections.emptyList;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

	private static final String SPLIT_CHAR = ";";

	@Override
	public String convertToDatabaseColumn(List<String> list) {
		return list != null ? String.join(SPLIT_CHAR, list) : "";
	}

	@Override
	public List<String> convertToEntityAttribute(String list) {
		return list != null ? Arrays.asList(list.split(SPLIT_CHAR)) : emptyList();
	}

}
