package edu.uniaeso.ElephantSystem.repository.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import edu.uniaeso.ElephantSystem.dto.VendaCategoria;
import edu.uniaeso.ElephantSystem.dto.VendaMes;
import edu.uniaeso.ElephantSystem.modelo.Categoria;
import edu.uniaeso.ElephantSystem.modelo.StatusVenda;
import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.filter.VendaFilter;
import edu.uniaeso.ElephantSystem.repository.paginacao.PaginacaoUtil;

public class VendasImpl implements VendasQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Transactional(readOnly = true)
	@Override
	public List<Venda> porUsuario(Usuario usuario) {
		String jpql = "FROM Venda v WHERE v.usuario = :usuario";
		return manager.createQuery(jpql, Venda.class).setParameter("usuario", usuario).getResultList();
	}
	
	@SuppressWarnings("deprecation")
	@Transactional(readOnly = true)
	@Override
	public Venda buscarComItens(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		criteria.createAlias("itensVenda", "i", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("id", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Venda) criteria.uniqueResult();
	}

	@Transactional(readOnly = true)
	@Override
	public Long total() {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Venda> venda = cq.from(Venda.class);
		
		
		cq.select(cb.count(venda));
		return manager.createQuery(cq).getSingleResult();
	}
	
	@Transactional(readOnly = true)
	@Override
	public BigDecimal valorTicketMedio() {
		Optional<BigDecimal> optional =
				Optional.ofNullable(
						manager.createQuery("select sum(valorTotal)/count(*) from Venda where year(dataCriacao) = :ano and status in :status", BigDecimal.class)
								.setParameter("ano", Year.now().getValue())
								.setParameter("status", Arrays.asList(StatusVenda.APROVADA, StatusVenda.DESPACHADO, StatusVenda.ENTREGUE))
								.getSingleResult());
		BigDecimal valor = optional.orElse(BigDecimal.ZERO);
		return valor.setScale(2, RoundingMode.HALF_UP);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<VendaMes> totalNoMes() {
		List<VendaMes> vendasMes = manager.createNamedQuery("Vendas.totalPorMes", VendaMes.class).getResultList();

		LocalDate hoje = LocalDate.now();
		for (int i = 1; i <= 6; i++) {
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());

			boolean possuiMes = vendasMes.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
			if (!possuiMes) {
				vendasMes.add(i - 1, new VendaMes(mesIdeal, 0));
			}

			hoje = hoje.minusMonths(1);
		}

		return vendasMes;
	}

	@Override
	public List<VendaCategoria> totalPorCategoria() {
		List<VendaCategoria> vendasCategoria = manager.createNamedQuery("Vendas.totalPorCategoria", VendaCategoria.class).getResultList();
		List<Categoria> categorias = new ArrayList<>();
		vendasCategoria.stream().forEach(v -> categorias.add(Categoria.valueOf(v.getCategoria())));
		
		for (Categoria categoria : Categoria.values()) {
			if(!categorias.stream().anyMatch(c -> c.equals(categoria))) {
				vendasCategoria.add(new VendaCategoria(categoria.toString(), BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)));
			}
		}
		
		return vendasCategoria;
	}

	@Override
	public Page<Venda> filtro(VendaFilter filter, Pageable pageable) {
		String jpql = "SELECT v FROM Venda v";
		List<Venda> lista = manager.createQuery(jpql, Venda.class).getResultList();
		//paginacaoUtil.preparar(pageable, null);
		
		return new PageImpl<Venda>(lista, pageable, lista.size());
	}
	
	
}
