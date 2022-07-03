package edu.uniaeso.ElephantSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import edu.uniaeso.ElephantSystem.dto.FotoDTO;
import edu.uniaeso.ElephantSystem.storage.AcaoFotoStorage;
import edu.uniaeso.ElephantSystem.storage.FotoStorage;
import edu.uniaeso.ElephantSystem.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> resultado = new DeferredResult<>();
		Thread thread = new Thread(new FotoStorageRunnable(resultado, files, fotoStorage, AcaoFotoStorage.SALVAR));
		thread.start();
		
		return resultado;
	}
	
	
	@DeleteMapping("/{nomeFoto:.*}")
	public ResponseEntity<?> deletar(@PathVariable String nomeFoto) {
		
		try {
			Thread thread = new Thread(new FotoStorageRunnable(nomeFoto, fotoStorage, AcaoFotoStorage.REMOVER));
			thread.start();
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	
	}

}
