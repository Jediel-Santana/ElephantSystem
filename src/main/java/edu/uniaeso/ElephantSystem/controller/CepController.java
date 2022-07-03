package edu.uniaeso.ElephantSystem.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.gilbertotorrezan.viacep.se.ViaCEPClient;
import com.github.gilbertotorrezan.viacep.shared.ViaCEPEndereco;

@RestController
public class CepController {
	
	
	@GetMapping("/cep/{cep}")
	public ResponseEntity<ViaCEPEndereco> cep(@PathVariable("cep") String cep) {
		ViaCEPClient client = new ViaCEPClient();
		try {
			ViaCEPEndereco endereco = client.getEndereco(cep);
			return ResponseEntity.ok(endereco);
		} catch (IOException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
}
