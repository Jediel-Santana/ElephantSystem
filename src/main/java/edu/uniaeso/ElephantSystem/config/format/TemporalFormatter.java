package edu.uniaeso.ElephantSystem.config.format;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Locale;

import org.springframework.format.Formatter;

public abstract class TemporalFormatter<T extends Temporal> implements Formatter<T> {

	@Override
	public String print(T object, Locale locale) {
		DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(locale);
		return dateTimeFormatter.format(object);
	}

	@Override
	public T parse(String text, Locale locale) throws ParseException {
		DateTimeFormatter dateTimeFormatter = getDateTimeFormatter(locale);
		return parse(text, dateTimeFormatter);
	}
	
	
	public DateTimeFormatter getDateTimeFormatter(Locale locale) {
		return DateTimeFormatter.ofPattern(getPattern(), locale);
	}

	public abstract String getPattern();
	
	public abstract T parse(String text, DateTimeFormatter dateTimeFormatter);
	
}
