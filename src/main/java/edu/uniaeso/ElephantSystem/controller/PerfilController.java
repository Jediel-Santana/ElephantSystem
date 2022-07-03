package edu.uniaeso.ElephantSystem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.uniaeso.ElephantSystem.carrinho.CarrinhoSession;
import edu.uniaeso.ElephantSystem.controller.page.PageWrapper;
import edu.uniaeso.ElephantSystem.modelo.StatusVenda;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.Vendas;
import edu.uniaeso.ElephantSystem.security.UsuarioSistema;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
	
	
	@Autowired
	private Vendas vendas;
	
	@Autowired
	private CarrinhoSession carrinho;
	
	@GetMapping
	public ModelAndView perfil(@AuthenticationPrincipal UsuarioSistema usuarioSistema, Model model) {
		ModelAndView mv = new ModelAndView("perfil/MeuPerfil");
		mv.addObject("usuario", usuarioSistema.getUsuario());
		mv.addObject("statusVenda", StatusVenda.values());
		return mv;
	}
	
	@PreAuthorize("hasRole('CLIENTE')")
	@GetMapping("/pedidos")
	public ModelAndView pedidos(@AuthenticationPrincipal UsuarioSistema usuario, @PageableDefault(size = 7) Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("perfil/Pedidos");
		List<Venda> vendasUsuario = vendas.porUsuario(usuario.getUsuario());
		mv.addObject("pagina", new PageWrapper<Venda>(new PageImpl<Venda>(vendasUsuario, pageable, vendasUsuario.size()), request));
		return mv;
	}
	
	@PostMapping
	public void atualizar() {
		
	}

}
