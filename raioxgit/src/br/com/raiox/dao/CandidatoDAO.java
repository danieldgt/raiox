package br.com.raiox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.Candidato;
import br.com.raiox.model.Servidor;

public class CandidatoDAO extends GenericDAO<Candidato> {

	public List<Candidato> findAll() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM Candidato e");
		return (List<Candidato>) query.getResultList();
	}

	public List<Servidor> loadServidorByCandidato(Integer idCandidato) {

		EntityManager entityManager = getEMFactory().createEntityManager();

		List<Servidor> result = entityManager
				.createQuery("SELECT distinct u.grupoGestor FROM Candidato u join u.grupoGestor WHERE u.id = :idCandidato")
				.setParameter("idCandidato", idCandidato)
				// .setMaxResults(10)
				.getResultList();

		return result;
	}
	
	public static void main(String[] args) {
		try {
			CandidatoDAO candidatoDAO = new CandidatoDAO();
			candidatoDAO.loadServidorByCandidato(1);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
