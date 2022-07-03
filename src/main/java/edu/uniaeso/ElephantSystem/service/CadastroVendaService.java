package edu.uniaeso.ElephantSystem.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import edu.uniaeso.ElephantSystem.modelo.StatusVenda;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.Vendas;
import edu.uniaeso.ElephantSystem.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {
	
	@Autowired
	private Vendas vendas;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public Venda salvar(Venda venda) {
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
			venda.setStatus(StatusVenda.APROVADA);
		}
		
		Venda novaVenda = vendas.saveAndFlush(venda);
		publisher.publishEvent(new VendaEvent(venda));
		return novaVenda; 
	}
	
	@Transactional
	public void alterarStatus(Venda venda, StatusVenda status) {
		venda.setStatus(status);
		vendas.save(venda);
	}
	
}
