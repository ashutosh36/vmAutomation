package hpe.csa.hibernate.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {
	
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	
	static {
		try {
			entityManagerFactory=Persistence.createEntityManagerFactory("vmjpa");
			entityManager=entityManagerFactory.createEntityManager();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
