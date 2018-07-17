package hpe.csa.model.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpe.csa.hibernate.util.EntityManagerHelper;
import hpe.csa.model.VM;

public class VMDaoImpl {
	
	private EntityManagerHelper entityManagerHelper=new EntityManagerHelper();
	private EntityManager em=entityManagerHelper.getEntityManager();
	private EntityTransaction tx=em.getTransaction();
	
	public VM searchByHostname(String hostname) {
		
		Query query=em.createNamedQuery("vm.findByHostname");
		query.setParameter("hostname", hostname);
		VM vm=(VM)query.getSingleResult();
		
		return vm;
	}

	public void add(VM vm) throws RuntimeException{
		// TODO Auto-generated method stub
		try {
			tx.begin();
			em.persist(vm);
			tx.commit();
		}catch (Exception e){
			throw new RuntimeException("Couldn't save VM object."+ e.getMessage());
		}
	}
	
	public List<VM> getAvailableVms(){
		Query query=em.createNamedQuery("vm.findAvailableVms");
		List<VM> vms=(List<VM>)query.getResultList();
		return vms;
	}

	public List<VM> getAll() {
		// TODO Auto-generated method stub
		Query query=em.createNamedQuery("vm.findAll");
		List<VM> vms=(List<VM>)query.getResultList();
		return vms;
	}


	public List<VM> getByVersion(String version) {
		Query query=em.createNamedQuery("vm.findByVersion");
		query.setParameter("version", version);
		List<VM> vmsByVersion=(List<VM>)query.getResultList();
		return vmsByVersion;
	}

	public void updateAll(List<VM> vms) {
		
		try {
			tx.begin();
			for(VM v:vms) {
				em.persist(v);
			}
			tx.commit();
		}catch (Exception e){
			throw new RuntimeException("Couldn't update all objects"+e.getMessage());
		}
		
	}
}
