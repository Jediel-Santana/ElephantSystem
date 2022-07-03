package edu.uniaeso.ElephantSystem.controller.conversor;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.uniaeso.ElephantSystem.modelo.Tamanho;

@Component
public class TamanhoConverter implements Converter<String, Tamanho> {

	@Override
	public Tamanho convert(String codigo) {
		if(!StringUtils.isEmpty(codigo)) {
			Tamanho tamanho = new Tamanho();
			tamanho.setId(Long.valueOf(codigo));
			return tamanho;
		}
		return null;
	}
}