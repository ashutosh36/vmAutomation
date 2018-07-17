package hpe.csa.model.Dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpe.csa.hibernate.util.EntityManagerHelper;
import hpe.csa.model.VM;

public class SubscriptionDaoImpl {
	
	private EntityManagerHelper entityManagerHelper=new EntityManagerHelper();
	private EntityManager em=entityManagerHelper.getEntityManager();
	private EntityTransaction tx=em.getTransaction();
	
	public List<VM> getAboutToExpireSubs(Timestamp expiryDate){
		Query query=em.createNamedQuery("sr.getAboutToExpireSubs");
		query.setParameter("expiryDate", expiryDate);
		return query.getResultList();
	}

}
