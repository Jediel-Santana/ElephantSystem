package edu.uniaeso.ElephantSystem.config.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public abstract class NumberFormatter<T extends Number> implements Formatter<T> {

	@Override
	public String print(T object, Locale locale) {
		NumberFormat format = new DecimalFormat(pattern(locale), new DecimalFormatSymbols(locale));
		return format.format(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T parse(String text, Locale locale) throws ParseException {
		DecimalFormat format = new DecimalFormat(pattern(locale), new DecimalFormatSymbols(locale));
		format.setParseBigDecimal(true);
		
		return (T) format.parse(text);
	}
	
	public abstract String pattern(Locale locale);

}
