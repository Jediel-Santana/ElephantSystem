package edu.uniaeso.ElephantSystem.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class ClassForErrorAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String NOME_ELEMENTO = "classforerror";
	private static final int PRECEDENCIA = 1000;

	public ClassForErrorAttributeTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ELEMENTO, true, PRECEDENCIA, true);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue,
			IElementTagStructureHandler structureHandler) {
		 boolean hasError = FieldUtils.hasErrors(context, attributeValue);

		if (hasError) {
			String classesExistentes = tag.getAttributeValue("class");
			structureHandler.setAttribute("class", classesExistentes + " has-error");
		}

	}

}
