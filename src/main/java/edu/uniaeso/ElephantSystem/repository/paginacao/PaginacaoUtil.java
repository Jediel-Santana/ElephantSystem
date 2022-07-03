package edu.uniaeso.ElephantSystem.repository.paginacao;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
public class PaginacaoUtil<T> {
	
	
	public void preparar(Pageable pageable, Criteria criteria) {
		
		int paginaAtual = pageable.getPageNumber();
		int quantidadeDeRegistros = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * quantidadeDeRegistros;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(quantidadeDeRegistros);
		
		Sort sort = pageable.getSort();
		
		if(sort != null && sort.isSorted()) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
		
		
	}

	public TypedQuery<T> preparar(TypedQuery<T> tq, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int quantidadeDeRegistros = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * quantidadeDeRegistros;
		return tq.setFirstResult(primeiroRegistro).setMaxResults(quantidadeDeRegistros);
	}
	
		
}
