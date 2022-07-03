package edu.uniaeso.ElephantSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@Autowired
	private ProdutoController produtoController;
	
	@GetMapping("/")
	public String principal() {
		return "redirect:/api/public/home";
	}
	
	@GetMapping("/api/public/home")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("produtosRecentes", produtoController.recentes());
		return mv;
	}
	
	
	@GetMapping("/api/public/sobre")
	public ModelAndView sobre() {
		ModelAndView mv = new ModelAndView("Sobre");
		return mv;
	}
	
	
	@GetMapping("/api/public/contato")
	public ModelAndView contato() {
		ModelAndView mv = new ModelAndView("Contato");
		return mv;
	}
	
}
