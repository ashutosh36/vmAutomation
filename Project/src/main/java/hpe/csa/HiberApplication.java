package hpe.csa;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import hpe.csa.MailService.MailServiceImpl;
import hpe.csa.MailService.SMTPSession;
import hpe.csa.automatedTasks.ScheduledThreadExecutor;
import hpe.csa.helperUtils.Helper;
import hpe.csa.hibernate.util.EntityManagerHelper;
import hpe.csa.hibernate.util.HibernateUtilities;
import hpe.csa.model.Subscription;
import hpe.csa.model.Users;
import hpe.csa.model.VM;
import hpe.csa.model.Dao.UsersDaoImpl;
import hpe.csa.serviceUtil.ApplicationManagerHelper;
import hpe.csa.serviceUtil.SubscriptionManagerImpl;



public class HiberApplication {

	private Logger logger=Logger.getLogger(HiberApplication.class.getName());
	private EntityManagerHelper emh=new EntityManagerHelper();
	private EntityManager em=emh.getEntityManager();
	private EntityTransaction tx=em.getTransaction();
	private Helper helper=new Helper();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String statement="Select * from Activities";
        System.out.println( "Hello World Java Project!" );
        Logger logger=Logger.getLogger(HiberApplication.class.getName());
        logger.error("Hello world error message");
        HiberApplication ha=new HiberApplication();
        ha.populateVms();
        ha.populateUsers();
        //System.out.println( "Here's your response \n"+ jdbcMsSql.executeSQLStatement(statement));
       // testCommit();
        //testCommit2();
        //storeInDatabase();
        //storeUserInDatabase();
        /*
       ha.populateVms();
       List<VM> listedVms=ha.getByVersion("491");
       for(VM lv:listedVms) {
    	   lv.printVM();
       }
       */
       
        /*
        List<VM> availableVms=ha.getAvailableVms();
        for(VM v:availableVms) {
        	v.printVM();
        }
        */
       
      // 	new MailServiceImpl().sendMail("abc", "123");
       // List<VM> vms=new SubscriptionManagerImpl().deleteExpiredSubscriptions();
        /*
        new ScheduledThreadExecutor().runCleanupService();
        long milliSecs=1000;
        try {
			Thread.sleep(15*milliSecs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
        System.out.println( "Done Here!" );
        System.exit(0);
	}
	public void checkLong(int a,int b) {
		
		long res=(long) Math.pow(a, b);
	}
	
	public void populateVms() {
		Set<VM> vms=new HashSet<VM>();
		Calendar cal=Calendar.getInstance(); 
		Date date=new Date(cal.getTimeInMillis());
		VM vm=new VM("ashu491.csacloud.local","491","administrator","csacpe@0!3",true);
		vm.setSubscription(new Subscription("Ashutosh491",helper.getSqlDateFromToday(0),helper.getSqlDateFromToday(3)));
		VM vm2=new VM("ashu492.csacloud.local","492","administrator","csacpe@0!3",true);
		vm2.setSubscription(new Subscription("Ashutosh491",helper.getSqlDateFromToday(0),helper.getSqlDateFromToday(4)));
		VM vm3=new VM("ashu493.csacloud.local","493","administrator","csacpe@0!3",false);
		VM vm4=new VM("ashu494.csacloud.local","494","administrator","csacpe@0!3",false);
		VM vm5=new VM("ashu494b.csacloud.local","494","administrator","csacpe@0!3",false);
		VM vm6=new VM("ashu494c.csacloud.local","494","administrator","csacpe@0!3",false);
		VM vm7=new VM("ashu482.csacloud.local","482","administrator","csacpe@0!3",false);
		VM vm8=new VM("ashu482b.csacloud.local","482","administrator","csacpe@0!3",false);
		VM vm9=new VM("ashu482c.csacloud.local","482","administrator","csacpe@0!3",false);
		
		vms.add(vm);
		vms.add(vm2);
		vms.add(vm3);
		vms.add(vm4);
		vms.add(vm5);
		vms.add(vm6);
		vms.add(vm7);
		vms.add(vm8);
		vms.add(vm9);
		
		for(VM v:vms) {
			VM res=addVM(v);
		}
		
	}
	
	public void populateUsers() {
		UsersDaoImpl usersDaoImpl=new UsersDaoImpl();
		Set<Users> users=new HashSet<Users>();
		users.add(new Users("ashu","cloud"));
		users.add(new Users("admin","cloud"));
		users.add(new Users("kaushik","cloud"));
		
		try {
			for(Users u:users) {
				usersDaoImpl.addUser(u);
			}
		}catch(Exception e) {
			System.out.println("Couldn't add user"+e.getMessage());
			logger.error("Couldn't add user"+e.getMessage());
		}
		
	}
	
	public static void testCommit() {
		Session session= HibernateUtilities.getSessionFactory().openSession();
		
		session.beginTransaction();
		
		//VM vm=new VM("ashu491.csacloud.local","Ashutosh","492","administrator","csacpe@0!3",true);
		VM vm=new VM("ashu491.csacloud.local","492","administrator","csacpe@0!3",true);
		session.save(vm);
		session.getTransaction().commit();
		
		session.close();
		HibernateUtilities.getSessionFactory().close();
		
	}
	
	public static void testCommit2() {
		Session session= HibernateUtilities.getSessionFactory().openSession();
		
		session.beginTransaction();
		
		//VM vm=new VM("ashu4.csacloud.local","Ashutosh","492","administrator","csacpe@0!3",true);
		VM vm=new VM("ashu4.csacloud.local","492","administrator","csacpe@0!3",true);
		session.save(vm);
		session.getTransaction().commit();
		
		session.close();
		HibernateUtilities.getSessionFactory().close();
		
	}
	
	public VM addVM(VM vm) {
		tx.begin();
		em.persist(vm);
		tx.commit();
		
		return vm;
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
	
}
