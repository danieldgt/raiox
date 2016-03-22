package br.com.raiox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.Cargo;

public class CargoDAO extends GenericDAO<Cargo> {
	public List<Cargo> findAll() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM Cargo e");
		return (List<Cargo>) query.getResultList();
	}

	public Cargo getCargoByDsCargo(String dsCargo) {
		try {
			EntityManager entityManager = getEMFactory().createEntityManager();
			Cargo result = (Cargo) entityManager.createQuery(
					"SELECT u FROM Cargo u WHERE u.dsCargo LIKE :dsCargo")
					.setParameter("dsCargo", dsCargo)
//			    .setMaxResults(10)
					.getSingleResult();
			return result;
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		}
	}
	
}
