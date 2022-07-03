package edu.uniaeso.ElephantSystem.config.format;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeFormatter extends TemporalFormatter<LocalDateTime> {

	@Override
	public String getPattern() {
		return "dd/MM/yyyy";
	}

	@Override
	public LocalDateTime parse(String text, DateTimeFormatter dateTimeFormatter) {
		return LocalDateTime.parse(text, dateTimeFormatter);
	}

}
