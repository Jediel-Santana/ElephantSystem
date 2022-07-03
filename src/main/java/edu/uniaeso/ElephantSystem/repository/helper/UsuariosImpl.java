package edu.uniaeso.ElephantSystem.repository.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;

import edu.uniaeso.ElephantSystem.modelo.Grupo;
import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.modelo.UsuarioGrupo;
import edu.uniaeso.ElephantSystem.repository.filter.UsuarioFilter;
import edu.uniaeso.ElephantSystem.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		String jpql = "FROM Usuario WHERE lower(email) = lower(:email) AND ativo = true";
		return manager.createQuery(jpql, Usuario.class).setParameter("email", email).getResultList().stream()
				.findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		String jpql = "select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p WHERE u = :usuario";

		return manager.createQuery(jpql, String.class).setParameter("usuario", usuario).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		paginacaoUtil.preparar(pageable, criteria);
		adicionarFiltro(usuarioFilter, criteria);
		List<Usuario> usuarios = criteria.list();
		usuarios.forEach(u -> Hibernate.initialize(u.getGrupos()));

		return new PageImpl<>(usuarios, pageable, total(usuarioFilter));
	}

	public Long total(UsuarioFilter usuarioFilter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
//		adicionarFiltro(usuarioFilter, criteria);
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	@Override
	public Optional<Usuario> porEmail(String email) {
		String jpql = "FROM Usuario WHERE lower(email) = lower(:email)";
		return manager.createQuery(jpql, Usuario.class).setParameter("email", email).getResultList().stream()
				.findFirst();
	}

	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}

			criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getId).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.id", codigoGrupo));
					dc.setProjection(Projections.property("id.usuario"));

					subqueries.add(Subqueries.propertyIn("id", dc));
				}

				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}
}
