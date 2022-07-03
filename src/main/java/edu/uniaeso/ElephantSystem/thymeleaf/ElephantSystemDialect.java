package edu.uniaeso.ElephantSystem.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import edu.uniaeso.ElephantSystem.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import edu.uniaeso.ElephantSystem.thymeleaf.processor.MenuAttributeTagProcessor;
import edu.uniaeso.ElephantSystem.thymeleaf.processor.MessageElementTagProcessor;
import edu.uniaeso.ElephantSystem.thymeleaf.processor.PaginationElementTagProcessor;

@Component
public class ElephantSystemDialect extends AbstractProcessorDialect {
	
	protected ElephantSystemDialect() {
		super("Elephant System", "es", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<IProcessor>();
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		return processadores;
	}

}
