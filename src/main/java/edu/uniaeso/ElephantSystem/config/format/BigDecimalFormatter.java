package edu.uniaeso.ElephantSystem.config.format;

import java.math.BigDecimal;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class BigDecimalFormatter extends NumberFormatter<BigDecimal> {

	@Override
	public String pattern(Locale locale) {
		return "#,##0.00";
	}
	

}
