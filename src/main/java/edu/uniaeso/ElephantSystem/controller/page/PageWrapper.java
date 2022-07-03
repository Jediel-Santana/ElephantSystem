package edu.uniaeso.ElephantSystem.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	
	
	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
		this.page = page;
		String url = httpServletRequest.getRequestURL().append(httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "").
				toString().replaceAll("\\+", "%20");
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
	}
	
	public List<T> getConteudo() {
		return page.getContent();
	}
	
	public int getTamanhoConteudo() {
		return page.getContent().size();
	}
	
	public boolean isVazia() {
		return page.isEmpty();
	}
	
	public int getTotal() {
		return page.getTotalPages();
	}
	
	public String urlParaPagina(int pagina) {
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toString();
	}
	
	public int getAtual() {
		return page.getNumber();
	}
	
	public int getTotalElementos() {
		return page.getContent().size();
	}
	
	
	
}
