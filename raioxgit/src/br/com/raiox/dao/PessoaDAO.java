package br.com.raiox.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.Pessoa;

public class PessoaDAO extends GenericDAO<Pessoa> {
	public List<Pessoa> findAll() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM Pessoa e");
		return (List<Pessoa>) query.getResultList();
	}

	public Pessoa getPessoaFromCPF(String cpf) {
		try {
			EntityManager entityManager = getEMFactory().createEntityManager();
			Pessoa result = (Pessoa) entityManager.createQuery("SELECT u FROM Pessoa u WHERE u.cpf LIKE :cpf")
					.setParameter("cpf", cpf)
					// .setMaxResults(10)
					.getSingleResult();
			return result;
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		}

	}
}
