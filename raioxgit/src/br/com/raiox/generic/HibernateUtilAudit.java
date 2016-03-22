package br.com.raiox.generic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtilAudit {
	
	private static EntityManagerFactory emf;

	public static EntityManagerFactory getEmf() {
		if (emf == null) {
			synchronized (HibernateUtilAudit.class) {
				if (emf == null)
					try {
						emf = Persistence.createEntityManagerFactory("audit");
					} catch (RuntimeException ex) {
						throw ex;
					}
			}
		}
		return emf;
	}

	public static EntityManager createEm() {
		try {
			return getEmf().createEntityManager();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	public static EntityManager createEntityManager() {
		EntityManager manager = createEm();
		return manager;
	}
}
