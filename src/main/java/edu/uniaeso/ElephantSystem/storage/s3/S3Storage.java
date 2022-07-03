package edu.uniaeso.ElephantSystem.storage.s3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import edu.uniaeso.ElephantSystem.storage.FotoStorage;
import net.coobird.thumbnailator.Thumbnails;

@Profile("prod")
@Component
public class S3Storage implements FotoStorage {

	private static final String BUCKET = "elephantsystem";

	@Autowired
	private AmazonS3 amazonS3;

	@Override
	public String salvar(MultipartFile[] files) {
		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());

			try {
				AccessControlList acl = new AccessControlList();
				enviarFoto(novoNome, arquivo, acl);
				enviarThumbnail(novoNome, arquivo, acl);
			} catch (Exception e) {
				throw new RuntimeException("Erro ao enviar foto!", e);
			}
		}

		return novoNome;
	}

	private void enviarThumbnail(String novoNome, MultipartFile arquivo, AccessControlList acl) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.of(arquivo.getInputStream()).size(190, 170).toOutputStream(os);
		;
		byte[] array = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(array);

		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentType(arquivo.getContentType());
		metaData.setContentLength(array.length);
		amazonS3.putObject(new PutObjectRequest(BUCKET, PREFIX_THUMBNAIL.concat(novoNome), is, metaData)
				.withAccessControlList(acl));
	}

	private void enviarFoto(String novoNome, MultipartFile arquivo, AccessControlList acl) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.of(arquivo.getInputStream()).size(720, 960).toOutputStream(os);
		;
		byte[] array = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(array);

		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentType(arquivo.getContentType());
		metaData.setContentLength(array.length);
		amazonS3.putObject(new PutObjectRequest(BUCKET, novoNome, is, metaData).withAccessControlList(acl));
	}

	@Override
	public void excluir(String nomeFoto) {
		amazonS3.deleteObject(BUCKET, nomeFoto);
	}

	@Override
	public byte[] recuperar(String nomeFoto) {
		InputStream is = amazonS3.getObject(BUCKET, nomeFoto).getObjectContent();
		try {
			return IOUtils.toByteArray(is);
		} catch (IOException e) {

		}
		return null;
	}

	public byte[] recuperarThumbnail(String nomeFoto) {
		return recuperar(PREFIX_THUMBNAIL + nomeFoto);
	}

	@Override
	public String getUrl(String nomeFoto) {
		if (!StringUtils.isEmpty(nomeFoto))
			return "https://elephantsystem.s3.sa-east-1.amazonaws.com/" + nomeFoto;

		return null;
	}

}
