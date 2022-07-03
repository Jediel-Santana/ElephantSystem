package edu.uniaeso.ElephantSystem.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.uniaeso.ElephantSystem.carrinho.CarrinhoSession;
import edu.uniaeso.ElephantSystem.dto.ItemVendaDTO;
import edu.uniaeso.ElephantSystem.modelo.FormaPagamento;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.Tamanho;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.Produtos;
import edu.uniaeso.ElephantSystem.repository.Tamanhos;

@Controller
@RequestMapping("/api/public/carrinho")
public class CarrinhoController {

	@Autowired
	private CarrinhoSession carrinhoSession;

	@Autowired
	private Produtos produtos;

	@Autowired
	private Tamanhos tamanhos;

	@GetMapping
	public ModelAndView carrinho(Venda venda) {
		ModelAndView mv = new ModelAndView("carrinho/Carrinho");

		List<ItemVendaDTO> itensVenda = carrinhoSession.getItensVendaDTO();
		mv.addObject("itensVenda", itensVenda);
		mv.addObject("valorTotalVenda", carrinhoSession.getValorTotalVenda());
		mv.addObject("venda", venda);
		mv.addObject("formasPagamento", FormaPagamento.values());
		
		return mv;
	}

	@PostMapping(value = "/item/{idProduto}")
	public ResponseEntity<?> adicionarItem(@PathVariable("idProduto") Produto produto, Integer quantidade, Long idTamanho) {
		if (Objects.nonNull(produto) && idTamanho != 0) {
			Tamanho tamanho = tamanhos.getById(idTamanho);
			carrinhoSession.adicionarItem(produto, quantidade, tamanho);
			return ResponseEntity.ok().build();
		} else if(idTamanho == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Selecione um tamanho");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao adicionar produto");
	}
	
	@DeleteMapping("/item/{idProduto}")
	public ResponseEntity<?> removerItem(@PathVariable Long idProduto, Long tamanho) {
		
		if(Objects.nonNull(idProduto) && Objects.nonNull(tamanho)) {
			carrinhoSession.removeItem(idProduto, tamanho);
		}
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/item/{idProduto}/tamanho")
	public ResponseEntity<ItemVendaDTO> alterarTamanhoItem(@PathVariable("idProduto") Produto produto, Long idTamanhoAtual, Long idTamanhoNovo) {
		Tamanho tamanhoAtual = tamanhos.getById(idTamanhoAtual);
		Tamanho tamanhoNovo = tamanhos.getById(idTamanhoNovo);
		ItemVendaDTO itemVendaDTO = carrinhoSession.alterarTamanhoItem(produto, tamanhoAtual, tamanhoNovo);
		
		
		return retornoItemVendaDTO(itemVendaDTO);
	}

	@PutMapping(value = "/item/{idProduto}/quantidade")
	public ResponseEntity<ItemVendaDTO> alterarQuantidadeItem(@PathVariable("idProduto") Produto produto, @RequestParam("idTamanho") Integer idTamanho,  @RequestParam("quantidade") String quantidade) {
		Tamanho tamanho = tamanhos.getById(Long.valueOf(idTamanho));
		ItemVendaDTO itemVendaDTO = carrinhoSession.alterarQuantidadeItem(produto, tamanho, Integer.valueOf(quantidade));
		return retornoItemVendaDTO(itemVendaDTO);
	}
	
	@GetMapping(value = "/itens", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ItemVendaDTO> todosItens() {
		return carrinhoSession.getItensVendaDTO();
	}
	
	@GetMapping("/itens/quantidadeTotal")
	public @ResponseBody int quantidadeTotalItens() {
		return carrinhoSession.getItensVendaDTO().size();
	}
	
	@GetMapping(value = "/itens/total", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody BigDecimal total() {
		return carrinhoSession.getValorTotalVenda();
	}
	
	@GetMapping("/pagamento")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView pagamento() {
		return carrinho(new Venda());
	}
	
	
	private ResponseEntity<ItemVendaDTO> retornoItemVendaDTO(ItemVendaDTO itemVendaDTO) {
		if(Objects.nonNull(itemVendaDTO))
			return ResponseEntity.ok(itemVendaDTO);
		else
			return ResponseEntity.notFound().build();
	}
	
}
