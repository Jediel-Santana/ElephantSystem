package edu.uniaeso.ElephantSystem.repository.listener;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PostLoad;

import edu.uniaeso.ElephantSystem.ElephantSystemApplication;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.storage.FotoStorage;

public class ProdutoEntityListener {
	
	
	@PostLoad
	public void postLoad(Produto produto) {
		FotoStorage fotoStorage = ElephantSystemApplication.getBean(FotoStorage.class);
		
		List<String> fotos = produto.getFoto();
		List<String> listaUrlFotos = fotos.stream().map(foto -> fotoStorage.getUrl(foto)).collect(Collectors.toList());
		
		produto.setUrlFoto(listaUrlFotos);
//		produto.setUrlFoto(fotoStorage.getUrl(produto.getFotosOuMock()));
	}
	
	
}
