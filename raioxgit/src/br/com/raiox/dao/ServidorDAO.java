package br.com.raiox.dao;

import java.text.Normalizer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.raiox.generic.GenericDAO;
import br.com.raiox.model.Servidor;

public class ServidorDAO extends GenericDAO<Servidor> {

	public List<Servidor> findAll() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT e FROM Servidor e");
		return (List<Servidor>) query.getResultList();
	}

	public List<Servidor> getServidoresNivelSuperior() {
		EntityManager entityManager = getEMFactory().createEntityManager();
		List<Servidor> result = entityManager
				.createQuery("SELECT u FROM Servidor u "
						+ "	WHERE u.titulacao != 'SEM DEFINICAO' "
						+ "	and u.titulacao != 'NIVEL M…DIO' "
						+ "	and u.titulacao != 'TECNICO (NIVEL MEDIO COMPLETO)'" 
						+ "	and u.situacao != 'CONT.PROF.SUBSTITUTO' "
						+ "	and u.situacao != 'CONT.PROF.TEMPORARIO' " 
						+ "	and u.situacao != 'CEDIDO' "
						+ "	and u.escolaridade != 'ENSINO MEDIO' "
						)
				// .setMaxResults(10)
				.getResultList();
		return result;
	}
	
	public static void main(String[] args) {
		try {
			ServidorDAO servidorDAO = new ServidorDAO();
			servidorDAO.getServidoresNivelSuperior();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// uorgLotacao
	public List<Servidor> getServidoresPorUorgLotacao(Integer idUorgLotacao) {
		EntityManager entityManager = getEMFactory().createEntityManager();
		List<Servidor> result = entityManager
				.createQuery("SELECT u FROM Servidor u WHERE u.uorgLotacao.id = :idUorgLotacao")
				.setParameter("idUorgLotacao", idUorgLotacao)
				// .setMaxResults(10)
				.getResultList();
		return result;
	}

	private DetachedCriteria retornaCriteriaBaseFiltros(Map<String, Object> filtros, Class clazz) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);

		criteria.createAlias("pessoa", "p");
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		int aliasCount = 1;
		if (filtros != null) {

			for (Iterator<String> it = filtros.keySet().iterator(); it.hasNext();) {
				String filterProperty = it.next();
				String filterValue = (String) filtros.get(filterProperty);

				if (filterProperty.equals("pessoa.nome")) {
					String parameter = Normalizer.normalize(filterValue, Normalizer.Form.NFKD)
							.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toUpperCase();

					criteria.add(Restrictions.sqlRestriction(
							"upper(TRANSLATE(p1_.nome,'¿¡·‡…»ÈËÕÌ”Û“Ú⁄˙„√ı’‚¬Í Ù‘Á«‚¬Í Ù‘','AAaaEEeeIiOoOoUuaAoOaAeEoOcCaAeEoO'))"
									+ "					 LIKE '%" + "" + parameter + "%'"));
				}
				if (filterProperty.equals("tipoUsuario.id")) {
					// criteria.createAlias("tipoUsuario", "ti").add(
					// Restrictions.eq("ti.id", new Integer(filterValue)));
				}
				if (filterProperty.equals("id")) {
					criteria.add(Restrictions.eq("id", new Integer(filterValue)));
				}
				if (!filterProperty.equals("pessoa.cpf") && !filterProperty.equals("pessoa.nome")
						&& !filterProperty.equals("id") && !filterProperty.equals("tipoUsuario.id")
						&& !filterProperty.equals("campus.id")) {
					criteria.add(Restrictions.like(filterProperty, "%" + filterValue + "%"));
				}
			}
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> listarPaginado(int primeiro, int registrosPagina, String camposort, SortOrder campoordenar,
			Map<String, Object> filtros, Class<T> clazz) {

		EntityManager em = getEMFactory().createEntityManager();
		Session session = em.unwrap(Session.class);

		DetachedCriteria criteria = retornaCriteriaBaseFiltros(filtros, clazz);

		if (camposort != null) {
			switch (campoordenar) {
			case ASCENDING:
				criteria.addOrder(Order.asc(camposort));
				break;
			case DESCENDING:
				criteria.addOrder(Order.desc(camposort));
				break;
			}
		}

		criteria.getExecutableCriteria(session).setFirstResult(primeiro).setMaxResults(registrosPagina);

		List<T> lista = criteria.getExecutableCriteria(session).list();

		session.close();
		return lista;

	}

	public <T> int retornaTotalRegistrosConsulta(int primeiro, int registrosPagina, String camposort,
			SortOrder campoordenar, Map<String, Object> filtros, Class<T> clazz) {

		EntityManager em = getEMFactory().createEntityManager();
		Session session = em.unwrap(Session.class);

		DetachedCriteria criteria = retornaCriteriaBaseFiltros(filtros, clazz);

		criteria.setProjection(Projections.rowCount());

		Integer i = (int) (long) criteria.getExecutableCriteria(session).uniqueResult();

		session.close();
		return i.intValue();
	}
}
