package edu.uniaeso.ElephantSystem.repository.helper;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import edu.uniaeso.ElephantSystem.modelo.Grupo;

public class GruposImpl implements GruposQueries {
	
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional(readOnly = true)
	@Override
	public List<Grupo> colaboradores() {
		String jpql = "FROM Grupo g WHERE g.nome in :nomes";
		return manager.createQuery(jpql, Grupo.class)
				.setParameter("nomes", Arrays.asList("Administrador Master", "Administrador"))
				.getResultList();
	}
	
	
}
