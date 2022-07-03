package edu.uniaeso.ElephantSystem.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.uniaeso.ElephantSystem.carrinho.CarrinhoSession;
import edu.uniaeso.ElephantSystem.dto.ItemVendaDTO;
import edu.uniaeso.ElephantSystem.dto.VendaCategoria;
import edu.uniaeso.ElephantSystem.dto.VendaMes;
import edu.uniaeso.ElephantSystem.mail.Mailer;
import edu.uniaeso.ElephantSystem.modelo.StatusVenda;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.Vendas;
import edu.uniaeso.ElephantSystem.security.UsuarioSistema;
import edu.uniaeso.ElephantSystem.service.CadastroVendaService;

@Controller
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private CarrinhoSession carrinhoSession;

	@Autowired
	private CadastroVendaService cadastroVendaService;

	@Autowired
	private Vendas vendas;

	@Autowired
	private Mailer mailer;

	@PostAuthorize("isAuthenticated()")
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Venda venda, BindingResult result,
			@AuthenticationPrincipal UsuarioSistema usuarioLogado, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView();

		if (result.hasErrors()) {
			attributes.addFlashAttribute("venda", venda);
			mv.setViewName("redirect:/api/public/carrinho");
			return mv;
		}

		venda.setItensVenda(carrinhoSession.getItens());
		venda.setValorTotal(carrinhoSession.getValorTotalVenda());
		venda.setUsuario(usuarioLogado.getUsuario());

		Venda vendaEmitida = cadastroVendaService.salvar(venda);
		mailer.enviar(vendaEmitida);

		attributes.addFlashAttribute("idVenda", vendaEmitida.getId());
		carrinhoSession.resetar();

		mv.setViewName("redirect:/vendas/compraRealizada");
		return mv;
	}

	@GetMapping("/compraRealizada")
	public ModelAndView compraRealizada() {
		return new ModelAndView("carrinho/CompraRealizada");
	}

	@GetMapping("/totalNoMes")
	public @ResponseBody List<VendaMes> totalNoMes() {
		return vendas.totalNoMes();
	}

	@GetMapping("/totalPorCategoria")
	public @ResponseBody List<VendaCategoria> totalPorCategoria() {
		return vendas.totalPorCategoria();
	}

	@PutMapping("/status/{idVenda}")
	public ModelAndView alterarStatus(@PathVariable Long idVenda, @RequestParam String status) {
		Optional<Venda> vendaOptional = vendas.findById(idVenda);
		ModelAndView mv = new ModelAndView();
		if (vendaOptional.isPresent() && StringUtils.isNotEmpty(status)) {
			try {
				StatusVenda statusVenda = StatusVenda.valueOf(status);

				cadastroVendaService.alterarStatus(vendaOptional.get(), statusVenda);
				
				mv.addObject("venda", vendaOptional.get());
				mv.setStatus(HttpStatus.OK);
				
				if(statusVenda.equals(StatusVenda.ENTREGUE)) {
					mv.setViewName("perfil/DetalheVendaModal :: statusAtual");
					return mv;
				} else {
					mv.setViewName("perfil/DetalheVendaModal :: statusPedido");
					return mv;
				}
			} catch (Exception e) {
				
				mv.setStatus(HttpStatus.BAD_REQUEST);
				return mv;
			}
		} else
			mv.setStatus(HttpStatus.NOT_FOUND);
			return mv;
	}
	
	@GetMapping("/{idVenda}")
	public String detalheVendaModal(@PathVariable("idVenda") Long idVenda, Model model, HttpServletRequest request) {
		Venda venda = vendas.buscarComItens(idVenda);
		
		model.addAttribute("venda", venda);
		model.addAttribute("itensVenda", ItemVendaDTO.getItensVendaDTO(venda.getItensVenda()));
		if (AjaxHelp.AJAX_HEADER_VALUE.equals(request.getHeader(AjaxHelp.AJAX_HEADER_NAME))) {
			return "perfil/DetalheVendaModal :: modal-content";
		} else {
			return "perfil/DetalheVendaModal";
		}
		
	}

}
