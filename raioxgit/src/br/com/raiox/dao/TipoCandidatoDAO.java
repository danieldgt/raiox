package br.com.raiox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.TipoCandidato;

public class TipoCandidatoDAO extends GenericDAO<TipoCandidato> {
	
	public List<TipoCandidato> findAll() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM TipoCandidato e");
		return (List<TipoCandidato>) query.getResultList();
	}
}
