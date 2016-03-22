package br.com.raiox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.Campus;

public class CampusDAO extends GenericDAO<Campus> {
	public List<Campus> findAll() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM Campus e");
		return (List<Campus>) query.getResultList();
	}
}
