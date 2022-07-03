package edu.uniaeso.ElephantSystem.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.uniaeso.ElephantSystem.controller.page.PageWrapper;
import edu.uniaeso.ElephantSystem.dto.DadosProduto;
import edu.uniaeso.ElephantSystem.dto.ProdutoDTO;
import edu.uniaeso.ElephantSystem.modelo.Categoria;
import edu.uniaeso.ElephantSystem.modelo.Genero;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.ProdutoTamanho;
import edu.uniaeso.ElephantSystem.modelo.Tamanho;
import edu.uniaeso.ElephantSystem.repository.Produtos;
import edu.uniaeso.ElephantSystem.repository.Tamanhos;
import edu.uniaeso.ElephantSystem.repository.filter.ProdutoFilter;
import edu.uniaeso.ElephantSystem.service.CadastroProdutoService;

@Controller
@RequestMapping("/api/")
public class ProdutoController {

	private static final String AJAX_HEADER_NAME = "X-Requested-With";
	private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private Produtos produtos;

	@Autowired
	private Tamanhos tamanhos;

	@PostAuthorize("isAuthenticated")
	@PostMapping(value = {"/produtos/novo", "/produtos/{\\d+}" } )
	public ModelAndView salvar(@Valid Produto produto, BindingResult result, RedirectAttributes attribute) {
		if(result.hasErrors()) {
			return novo(produto);
		}
		
		cadastroProdutoService.salvar(produto);
		attribute.addFlashAttribute("mensagem", "Produto cadastrado com sucesso!");
		return new ModelAndView("redirect:/api/produtos/novo");
	}

	
	@GetMapping("/public/produtos")
	public ModelAndView filtro(ProdutoFilter produtoFilter, @PageableDefault(size = 9) Pageable pageable,
			HttpServletRequest request) {
		String valorDe = request.getParameter("valorDe");
		String valorAte = request.getParameter("valorAte");
		
		if(Objects.nonNull(valorDe) && Objects.nonNull(valorAte)) {
			produtoFilter.setValorDe(new BigDecimal(valorDe));
			produtoFilter.setValorAte(new BigDecimal(valorAte));
		}
		
		ModelAndView mv = new ModelAndView("produto/PesquisaProduto");
		mv.addObject("categorias", Categoria.values());
		mv.addObject("generos", Genero.values());
		mv.addObject("tamanhos", tamanhos.findAll());
		mv.addObject("pagina", new PageWrapper<>(produtos.filtrar(produtoFilter, pageable), request));
		mv.addObject("totalItens", produtos.total(produtoFilter));

		return mv;
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/produtos/novo")
	public ModelAndView novo(Produto produto) {
		ModelAndView mv = new ModelAndView("produto/CadastroProduto");

		mv.addObject("categorias", Categoria.values());
		mv.addObject("generos", Genero.values());
		mv.addObject("tamanhos", tamanhos.findAll());
		mv.addObject("produto", produto);

		return mv;
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/produtos/edit/{idProduto}")
	public ModelAndView editar(@PathVariable("idProduto") Produto produto) {
		return novo(produto);
	}
	
	@GetMapping("/public/produtos/{idProduto}")
	public ModelAndView detalheProduto(@PathVariable("idProduto") Produto produto) {
		ModelAndView mv = new ModelAndView("produto/ProdutoDetalhe");
		mv.addObject("produto", new ProdutoDTO(produto));
		mv.addObject("dadosProduto", new DadosProduto());
		return mv;
	}
	
	
	@GetMapping("/recentes")
	public @ResponseBody List<ProdutoDTO> recentes() {
		return produtos.maisRecentesDosUltimos30Dias().stream().map(ProdutoDTO::new).collect(Collectors.toList());
	}

	@PostAuthorize("isAuthenticated()")
	@PostMapping(params = "addFoto", path = "/produtos/fotos")
	public String adicionarFoto(Produto produto, @RequestParam("nomeNovaFoto") String nome, @RequestParam("contentTypeNovaFoto") String contentType,
			@RequestParam("urlFotoNovaFoto") String url, HttpServletRequest request, Model model, BindingResult result) {
		
		produto.adicionarFoto(nome, contentType, url);
		model.addAttribute("produto", produto);
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
			return "produto/CadastroProduto :: gruposFoto";
		} else {
			return "produto/CadastroProduto";
		}
	}
	
	@PostAuthorize("isAuthenticated()")
	@PostMapping(params = "removeFoto", path = "/produtos/fotos")
	public String removerFoto(Produto produto, @RequestParam int index, HttpServletRequest request, Model model) {
		
		produto.removerFoto(index);
		model.addAttribute("produto", produto);
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
			return "produto/CadastroProduto :: gruposFoto";
		} else {
			return "produto/CadastroProduto";
		}
	}
	
	@PostAuthorize("isAuthenticated()")
	@PostMapping(params = "alterarTamanhos", path= "/produtos/tamanhos")
	public String alterarTamanhos(Produto produto, @RequestParam("idsTamanhosUsados") int[] ids, HttpServletRequest request) {
		List<Integer> idsTamanhosUsados = Arrays.stream(ids).boxed().collect(Collectors.toList());
		List<Tamanho> tamanhosCadastrados = produto.getTamanhosCadastrados();
		
		for (Integer idTamanhoUsado : idsTamanhosUsados) {
			Long id = Long.valueOf(idTamanhoUsado);
			if(tamanhosCadastrados.stream().anyMatch(p -> p.getId().equals(id))) {
				continue;
			} else {
				Tamanho novoTamanho = tamanhos.getById(id);
				produto.adicionarProdutoTamanho(novoTamanho, 1);
			}
		}
		
		List<Tamanho> tamanhosParaRemover = new ArrayList<>();
		for (ProdutoTamanho produtoTamanho : produto.getProdutoTamanhos()) {
			String idTamanho = produtoTamanho.getTamanho().getId().toString();
			if(idsTamanhosUsados.stream().anyMatch(idu -> idu.equals(Integer.valueOf(idTamanho)))) {
				continue;
			} else {
				tamanhosParaRemover.add(produtoTamanho.getTamanho());
			}
		}
		tamanhosParaRemover.stream().forEach(tamanho -> produto.removeTamanhoProduto(tamanho));
		produto.getProdutoTamanhos().stream().forEach(pt -> pt.setTamanho(tamanhos.getById(pt.getTamanho().getId())));
		
		if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
			return "produto/CadastroProduto :: tamanhos-usados-lista";
		} else {
			return "produto/CadastroProduto";
		}
	}
	
}
