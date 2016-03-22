package br.com.raiox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.Eleicao;

public class EleicaoDAO extends GenericDAO<Eleicao> {
	public List<Eleicao> findAll() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM Eleicao e");
		return (List<Eleicao>) query.getResultList();
	}
}
