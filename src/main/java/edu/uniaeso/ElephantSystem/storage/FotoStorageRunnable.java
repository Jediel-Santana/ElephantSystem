package edu.uniaeso.ElephantSystem.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import edu.uniaeso.ElephantSystem.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {

	private FotoStorage fotoStorage;
	private MultipartFile[] files;
	private DeferredResult<FotoDTO> resultado;
	private AcaoFotoStorage acao;
	private String nomeFoto;

	public FotoStorageRunnable(DeferredResult<FotoDTO> resultado, MultipartFile[] files, FotoStorage fotoStorage, AcaoFotoStorage acao) {
		this.resultado = resultado;
		this.files = files;
		this.fotoStorage = fotoStorage;
		this.acao = acao;
	}
	
	public FotoStorageRunnable(String nomeFoto, FotoStorage fotoStorage, AcaoFotoStorage acao) {
		this.nomeFoto = nomeFoto;
		this.fotoStorage = fotoStorage;
		this.acao = acao;
	}
	
	@Override
	public void run() {
		switch(acao) {
			case SALVAR:
				salvarFoto();
				break;
			case REMOVER:
				removerFoto();
				break;
		}
	}

	private void removerFoto() {
		fotoStorage.excluir(nomeFoto);
	}

	private void salvarFoto() {
		String nomeFoto = fotoStorage.salvar(files);
		String contentType = files[0].getContentType();
		String urlFoto = fotoStorage.getUrl(nomeFoto);
		resultado.setResult(new FotoDTO(nomeFoto, contentType, urlFoto));
	}
	
	
	
		
}
