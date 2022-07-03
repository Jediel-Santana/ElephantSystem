package edu.uniaeso.ElephantSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.uniaeso.ElephantSystem.modelo.Tamanho;
import edu.uniaeso.ElephantSystem.repository.Tamanhos;

@Controller
@RequestMapping("/tamanhos")
public class TamanhoController {
	
	@Autowired
	private Tamanhos tamanhos;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Tamanho> listar() {
		return tamanhos.findAll();
	}
	
	
	
}
