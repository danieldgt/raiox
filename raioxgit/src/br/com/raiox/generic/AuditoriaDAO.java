package br.com.raiox.generic;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.raiox.model.Auditoria;

/**
 * Controla as operações com de Auditoria
 * 
 * @author Thiago Queiroz (thiagoqo@gmail.com)
 * @author Daniel Alencar (danieldgt@gmail.com)
 * 
 * @since 23/05/2014
 * 
 */
public class AuditoriaDAO extends GenericDAO<Auditoria> implements Serializable {

	private static final long serialVersionUID = 2404360457246388280L;

	public List<Auditoria> getAuditoriaPorUsuario(Integer idAuditoria) throws Exception {
		Session session = getSession();
		List<Auditoria> lista = session.createQuery("from Auditoria a where a.executedBy.id = ?")
				.setParameter(1, idAuditoria).list();

		session.flush();
		session.close();
		return lista;

	}

	private DetachedCriteria retornaCriteriaBaseFiltros( Map<String,Object> filtros, Class clazz){
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);

		if(filtros != null){
			for (Iterator<String> it = filtros.keySet().iterator(); it
					.hasNext();) {
				String filterProperty = it.next();
				String filterValue = (String) filtros.get(filterProperty);

				if (filterProperty.equals("executedBy.nome")) {
					criteria.createAlias("executedBy", "us").add(Restrictions.like("us.nome", "%" + filterValue + "%"));
				}
				if (filterProperty.equals("operationDate")) {
					criteria.add(Restrictions.like("operationDate", "%" + filterValue + "%"));
				}	else if(!filterProperty.equals("usuario.nome")) {
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

		Integer i = (int) (long)  criteria.getExecutableCriteria(session).uniqueResult();

		session.close();
		return i.intValue();
	}
}
