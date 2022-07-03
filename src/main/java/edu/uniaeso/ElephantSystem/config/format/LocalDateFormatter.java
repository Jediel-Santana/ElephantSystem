package edu.uniaeso.ElephantSystem.config.format;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class LocalDateFormatter extends TemporalFormatter<LocalDate> {

	@Override
	public String getPattern() {
		return "yyyy-MM-dd";
	}

	@Override
	public LocalDate parse(String text, DateTimeFormatter dateTimeFormatter) {
		return LocalDate.parse(text, dateTimeFormatter);
	}

}
