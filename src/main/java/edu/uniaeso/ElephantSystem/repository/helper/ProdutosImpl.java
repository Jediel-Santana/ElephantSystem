package edu.uniaeso.ElephantSystem.repository.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import edu.uniaeso.ElephantSystem.dto.ProdutoDTO;
import edu.uniaeso.ElephantSystem.dto.ProdutoMaisVendido;
import edu.uniaeso.ElephantSystem.dto.ValorItensEstoque;
import edu.uniaeso.ElephantSystem.dto.VendaCategoria;
import edu.uniaeso.ElephantSystem.modelo.Produto;
import edu.uniaeso.ElephantSystem.modelo.ProdutoTamanho;
import edu.uniaeso.ElephantSystem.modelo.Tamanho;
import edu.uniaeso.ElephantSystem.repository.filter.ProdutoFilter;
import edu.uniaeso.ElephantSystem.repository.paginacao.PaginacaoUtil;

public class ProdutosImpl implements ProdutosQueries {

	@Autowired
	private PaginacaoUtil<Produto> paginacaoUtil;

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Produto> maisRecentesDosUltimos30Dias() {
		String jpql = "select p from Produto p ORDER BY p.dataCriacao DESC";
		return manager.createQuery(jpql, Produto.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<ProdutoDTO> filtrar(ProdutoFilter produtoFilter, Pageable pageable) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
		Root<Produto> produto = cq.from(Produto.class);
		List<Predicate> predicates = adicionarFiltro(produtoFilter, cb, cq, produto);
		cq.distinct(true);
		
		if(!predicates.isEmpty())
			cq.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Produto> typedQuery = paginacaoUtil.preparar(manager.createQuery(cq), pageable);
		List<Produto> listaProdutos = typedQuery.getResultList();
		List<ProdutoDTO> listaProdutosDTO = new ArrayList<>();
		
		listaProdutos.stream().forEach(p -> listaProdutosDTO.add(new ProdutoDTO(p)));
		
		return new PageImpl<ProdutoDTO>(listaProdutosDTO, pageable, total(produtoFilter));
	}

	private <T> List<Predicate> adicionarFiltro(ProdutoFilter produtoFilter, CriteriaBuilder cb, CriteriaQuery<T> criteriaQuery, Root<Produto> produto) {
		List<Predicate> predicates = new ArrayList<>();
		
		
		if (Objects.nonNull(produtoFilter)) {
			if (!StringUtils.isEmpty(produtoFilter.getDescricao())) {
				predicates.add(cb.like(produto.get("descricao"), "%" + produtoFilter.getDescricao() + "%"));
			}
			if (!StringUtils.isEmpty(produtoFilter.getCodigoDeBarras())) {
				predicates.add(cb.like(produto.get("codigoDeBarras"), produtoFilter.getCodigoDeBarras() + "%"));
			}
			if (Objects.nonNull(produtoFilter.getGeneros()) && !produtoFilter.getGeneros().isEmpty()) {
				predicates.add(produto.get("genero").in(produtoFilter.getGeneros()));
			}
			if (Objects.nonNull(produtoFilter.getCategorias()) && !produtoFilter.getCategorias().isEmpty()) {
				predicates.add(produto.get("categoria").in(produtoFilter.getCategorias()));
			}
			if (Objects.nonNull(produtoFilter.getTamanhos()) && !produtoFilter.getTamanhos().isEmpty()) {
				Join<Produto, ProdutoTamanho> join = produto.join("produtoTamanhos");
				Path<Tamanho> campoTamanho = join.get("tamanho");
				Path<Object> campoId = campoTamanho.get("id");
				predicates.add(campoTamanho.in(produtoFilter.getTamanhos()));
			}
			if (Objects.nonNull(produtoFilter.getCores()) && !produtoFilter.getCores().isEmpty()) {
				predicates.add(produto.get("cor").in(produtoFilter.getCores()));
			}
			if (Objects.nonNull(produtoFilter.getValorDe())) {
				predicates.add(cb.greaterThanOrEqualTo(produto.get("preco"), produtoFilter.getValorDe()));
			}
			if (Objects.nonNull(produtoFilter.getValorAte())) {
				predicates.add(cb.lessThanOrEqualTo(produto.get("preco"), produtoFilter.getValorAte()));
			}
		}
		return predicates;
	}
	
	@Transactional(readOnly = true)
	public Long total(ProdutoFilter produtoFilter) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Produto> produto = cq.from(Produto.class);
		List<Predicate> predicates = adicionarFiltro(produtoFilter, cb, cq, produto);
		
		
		if(!predicates.isEmpty())
			cq.where(predicates.toArray(new Predicate[0]));
		
		cq.select(cb.countDistinct(produto));
		return manager.createQuery(cq).getSingleResult();
	}
	
	@Transactional(readOnly = true)
	@Override
	public ValorItensEstoque valorItensEstoque() {
		return manager
				.createQuery("select new edu.uniaeso.ElephantSystem.dto.ValorItensEstoque(sum(preco * quantidadeEstoque), sum(quantidadeEstoque)) from Produto",
						ValorItensEstoque.class)
				.getSingleResult();
	}
	
	@Transactional(readOnly = true)
	public List<ProdutoMaisVendido> maisVendidos() {
		List<ProdutoMaisVendido> maisVendidos = manager.createNamedQuery("Produtos.totalVendido", ProdutoMaisVendido.class).getResultList();
		return maisVendidos;
	}
	

}
