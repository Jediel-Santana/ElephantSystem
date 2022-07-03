package edu.uniaeso.ElephantSystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.uniaeso.ElephantSystem.controller.page.PageWrapper;
import edu.uniaeso.ElephantSystem.modelo.StatusVenda;
import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.Produtos;
import edu.uniaeso.ElephantSystem.repository.Usuarios;
import edu.uniaeso.ElephantSystem.repository.Vendas;
import edu.uniaeso.ElephantSystem.repository.filter.UsuarioFilter;
import edu.uniaeso.ElephantSystem.repository.filter.VendaFilter;

@Controller
@RequestMapping(value = "/adm")
public class AdministracaoController {
	
	@Autowired
	private Vendas vendas;
	
	@Autowired
	private Produtos produtos;
	
	@Autowired
	private Usuarios usuarios;
	
	@GetMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		
		mv.addObject("totalPedidos", vendas.total());
		mv.addObject("ticketMedio", vendas.valorTicketMedio());
		mv.addObject("valorItensEstoque", produtos.valorItensEstoque());
		mv.addObject("vendasPorCategoria", vendas.totalPorCategoria());
		mv.addObject("produtosMaisVendidos", produtos.maisVendidos());
		
		return mv;
	}
	
	@GetMapping("/gerenciar-vendas")
	public ModelAndView gerenciarVendas(VendaFilter vendaFilter, @PageableDefault(size = 7) Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("administracao/GerenciarVendas");
		mv.addObject("pagina", new PageWrapper<Venda>(vendas.filtro(vendaFilter, pageable), request));
		mv.addObject("statusVenda", StatusVenda.values());
		return mv;
	}
	
	@GetMapping("/gerenciar-colaboradores")
	public ModelAndView gerenciarColaboradores(UsuarioFilter usuarioFilter, @PageableDefault(size = 7) Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("administracao/GerenciarColaboradores");
		mv.addObject("pagina", new PageWrapper<Usuario>(usuarios.filtrar(usuarioFilter, pageable), request));
		return mv;
	}
	
	@GetMapping("/estoque")
	public String estoque() {
		return "estoque/Estoque";
	}
	
}
