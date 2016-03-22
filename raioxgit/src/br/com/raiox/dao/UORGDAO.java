package br.com.raiox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.UORG;
import br.com.raiox.model.UORG;

public class UORGDAO extends GenericDAO<UORG> {
	public List<UORG> findAll() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM UORG e");
		return (List<UORG>) query.getResultList();
	}

	public List<UORG> getUORGsFromCampus(Integer idCampus) {
		EntityManager entityManager = getEMFactory().createEntityManager();
		 List<UORG> result = entityManager.createQuery(
			    "SELECT u FROM UORG u WHERE u.campus.id = :idCampus")
			    .setParameter("idCampus", idCampus)
//			    .setMaxResults(10)
			    .getResultList();
		 return result;
	}
	
	public UORG getUORGByDsUORG(String dsUORG) {
		try {
			EntityManager entityManager = getEMFactory().createEntityManager();
			UORG result = (UORG) entityManager.createQuery(
					"SELECT u FROM UORG u WHERE u.dsUORG LIKE :dsUORG")
					.setParameter("dsUORG", dsUORG)
					.getSingleResult();
			return result;
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		}
	}
	
}
