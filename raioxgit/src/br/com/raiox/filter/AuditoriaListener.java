package br.com.raiox.filter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import br.com.raiox.controller.UsuarioLogado;
import br.com.raiox.dao.UsuarioDAO;
import br.com.raiox.exception.DAOException;
import br.com.raiox.generic.AuditoriaDAO;
import br.com.raiox.generic.HibernateUtilAudit;
import br.com.raiox.model.AbstractEntity;
import br.com.raiox.model.Auditoria;
import br.com.raiox.model.Usuario;
import br.com.raiox.util.TransactionType;

/**
 * Monitora as operações de inserção/atualização e remoção das entidades
 * marcadas
 * 
 * insere um registro de auditoria na tabela auditoria
 * 
 * @author Daniel Alencar (danieldgt@gmail.com)
 * 
 * @since 12/08/2014
 *
 */
public class AuditoriaListener extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Auditoria auditoria;
	AuditoriaDAO auditoriaDAO;

	private Set<Object> inserts = new HashSet<Object>();
	private Set<Object> updates = new HashSet<Object>();
	private Set<Object> deletes = new HashSet<Object>();

	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {

		// System.out.println("onSave");

		if (entity instanceof AbstractEntity) {
			inserts.add(entity);
		}
		return false;

	}

	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {

		// System.out.println("onFlushDirty");

		if (entity instanceof AbstractEntity) {
			updates.add(entity);
		}
		return false;

	}

	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

		// System.out.println("onDelete");

		if (entity instanceof AbstractEntity) {
			deletes.add(entity);
		}
	}

	// called before commit into database
	public void preFlush(Iterator iterator) {
//		System.out.println("preFlush");
	}

	// called after committed into database
	public void postFlush(Iterator iterator) {
		// System.out.println("postFlush");
		try {

			for (Iterator it = inserts.iterator(); it.hasNext();) {
				AbstractEntity entity = (AbstractEntity) it.next();
				// System.out.println("postFlush - insert");
				createLog(TransactionType.CREATE, entity);
			}

			for (Iterator it = updates.iterator(); it.hasNext();) {
				AbstractEntity entity = (AbstractEntity) it.next();
				// System.out.println("postFlush - update");
				createLog(TransactionType.UPDATE, entity);
			}

			for (Iterator it = deletes.iterator(); it.hasNext();) {
				AbstractEntity entity = (AbstractEntity) it.next();
				// System.out.println("postFlush - delete");
				createLog(TransactionType.DELETE, entity);
			}

		} finally {
			inserts.clear();
			updates.clear();
			deletes.clear();
		}
	}

	private void createLog(TransactionType transactionType, AbstractEntity c) {
		auditoriaDAO = new AuditoriaDAO();

		Usuario usuario = new Usuario();
		try {
			 FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
			
			UsuarioLogado usuarioLogado = (UsuarioLogado) session.getAttribute("usuarioLogado");
			usuario = usuarioLogado.getUsuario();
			
		} catch (Exception e) {
			// TODO: handle exception
			UsuarioDAO dao = new UsuarioDAO();
			try {
				usuario = dao.find(1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		auditoria = new Auditoria();

		auditoria.setEntityName(c.getClass().getName());
		auditoria.setRegistryId(Long.valueOf(c.getId() != null ? c.getId().toString() : "0"));
		auditoria.setToString(c.toString());
		auditoria.setUsuario(usuario);
		auditoria.setOperationDate(new Date());
		auditoria.setTransactionType(transactionType);

		try {

			EntityManager manager = HibernateUtilAudit.createEntityManager();

			manager.getTransaction().begin();
			try {
				manager.persist(auditoria);
				manager.getTransaction().commit();
			} catch (PersistenceException e) {
				throw new DAOException("ERRO AO SALVAR AUDITORIA!", e);
			} catch (Exception e) {
				manager.getTransaction().rollback();
				throw new DAOException(e);
			} finally {
				manager.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}