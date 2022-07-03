package edu.uniaeso.ElephantSystem.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	
	String PREFIX_THUMBNAIL = "thumbnail.";
	
	public String salvar(MultipartFile[] files);
	
	public void excluir(String nomeFoto);
	
	public byte[] recuperar(String nomeFoto);

	public byte[] recuperarThumbnail(String nomeFoto);
	
	public String getUrl(String nomeFoto);
	
	default String renomearArquivo(String originalFilename) {
		return UUID.randomUUID().toString() + "_" + originalFilename;
	}
	
}
