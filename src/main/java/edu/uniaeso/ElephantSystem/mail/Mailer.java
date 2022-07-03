package edu.uniaeso.ElephantSystem.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import edu.uniaeso.ElephantSystem.dto.ItemVendaDTO;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.storage.FotoStorage;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine template;

	@Autowired
	private FotoStorage fotoStorage;

	@Async
	public void enviar(Venda venda) {
		
		List<ItemVendaDTO> itensVenda = ItemVendaDTO.getItensVendaDTO(venda.getItensVenda());
		
		Context context = new Context();
		context.setVariable("venda", venda);
		
		context.setVariable("itensVenda", itensVenda);
		context.setVariable("logo", "logo");

		Map<String, String> produtos = new HashMap<>();
		boolean adicionarmockProduto = false;
		
		for (ItemVendaDTO itemVenda : itensVenda) {
//			Produto produto = itemVenda.getProduto();
			if (itemVenda.temFoto()) {
				String cid = "foto-" + itemVenda.getIdProduto();
				context.setVariable(cid, cid);
				produtos.put(cid, itemVenda.getNomeFoto() + "|" + itemVenda.getContentType());
			} else {
				adicionarmockProduto = true;
				context.setVariable("mockProduto", "mockProduto");
			}

		}

		try {
			String email = template.process("mail/ResumoVendas", context);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			mimeMessageHelper.setFrom("jedielantoniodesantana@gmail.com");
			mimeMessageHelper.setTo(venda.getUsuario().getEmail());
			mimeMessageHelper.setSubject("ElephantSystem - Venda nº" + venda.getId() + " realizada com sucesso!");
			mimeMessageHelper.setText(email, true);

			mimeMessageHelper.addInline("logo", new ClassPathResource("static/layout/images/logo-es.png"));

			if (adicionarmockProduto) {
				mimeMessageHelper.addInline("mockProduto", new ClassPathResource("static/layout/images/mock-produto.jpg"));
			}

			for (String cid : produtos.keySet()) {
				String[] nomeFotoEContentType = produtos.get(cid).split("\\|");
				String nomeFoto = nomeFotoEContentType[0];
				String contentTypeFoto = nomeFotoEContentType[1];
				byte[] fotoArray = fotoStorage.recuperar(nomeFoto);
				mimeMessageHelper.addInline(cid, new ByteArrayResource(fotoArray), contentTypeFoto);
			}

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
