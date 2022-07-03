package edu.uniaeso.ElephantSystem.storagel.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import edu.uniaeso.ElephantSystem.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Profile("!prod")
@Component
public class FotoStorageLocal implements FotoStorage {

	private static final Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);

	private static final String THUMBNAIL_PREFIX = "thumbnail.";
	
	@Value("${elephantsystem.foto-storage-local.local}")
	private Path local;
	
	@Value("${elephantsystem.foto-storage-local.url-base}")
	private String urlBase;
	
	@PostConstruct
	private void criarPastas() {
		try {
			Files.createDirectories(local);
			if (logger.isDebugEnabled()) {
				logger.debug("Pasta criadas!");
			}

		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}

	@Override
	public String salvar(MultipartFile[] files) {
		String novoNome = null;
		if (Objects.nonNull(files) && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				arquivo.transferTo(new File(local.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (Exception e) {
				throw new RuntimeException("Erro salvando a foto na pasta temporária!", e);
			}

		}

		try {
			Thumbnails.of(this.local.resolve(novoNome).toString()).size(40, 70).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao redimensionar foto!", e);
		}

		return novoNome;
	}

	@Override
	public byte[] recuperar(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto!");
		}
	}

	@Override
	public byte[] recuperarThumbnail(String nomeFoto) {
		return recuperar(THUMBNAIL_PREFIX + nomeFoto);
	}

	@Override
	public void excluir(String nomeCerveja) {
		try {
			Files.deleteIfExists(this.local.resolve(nomeCerveja));
			Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX + nomeCerveja));
		} catch (Exception e) {
			logger.warn("Erro ao excluir produto", e);
		}
	}

	@Override
	public String getUrl(String foto) {
		return urlBase  + foto;
	}

}
