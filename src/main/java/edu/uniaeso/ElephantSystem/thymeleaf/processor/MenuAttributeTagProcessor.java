package edu.uniaeso.ElephantSystem.thymeleaf.processor;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class MenuAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final int precendencia = 1000;
	private static final String NOME_ATRIBUTO = "menu";
	
	
	public MenuAttributeTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ATRIBUTO, true, precendencia,
				true);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		
		IEngineConfiguration configuration = context.getConfiguration();
		IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
		IStandardExpression expression = parser.parseExpression(context, attributeValue);
		String menu = (String) expression.execute(context);

		HttpServletRequest request = ((IWebContext) context).getRequest();
		String uri = request.getRequestURI();

		if (uri.matches(menu)) {
			String classesExistentes = tag.getAttributeValue("class");
			structureHandler.setAttribute("class", classesExistentes + " active");
		}
	}
	

}
