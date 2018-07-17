package hpe.csa.model.Dao;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpe.csa.hibernate.util.EntityManagerHelper;
import hpe.csa.model.Users;
import hpe.csa.model.VM;

public class UsersDaoImpl {
	
	private EntityManagerHelper entityManagerHelper=new EntityManagerHelper();
	private EntityManager em=entityManagerHelper.getEntityManager();
	private EntityTransaction tx=em.getTransaction();
	
	public Users getUserByName(String username){
		Query query=em.createNamedQuery("users.fundByUsername");
		query.setParameter("username", username);
		return (Users)query.getSingleResult();
	}
	
	public void addUser(Users u) throws RuntimeException{
		try {
			tx.begin();
			em.persist(u);
			tx.commit();
		}catch (Exception e){
			throw new RuntimeException("Couldn't save User "+u.getUsername()+ e.getMessage());
		}
	}

}
