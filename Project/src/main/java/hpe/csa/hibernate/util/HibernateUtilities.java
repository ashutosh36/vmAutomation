package hpe.csa.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtilities {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	
	static {
		try {
			/*
			//Configuration configuration=new Configuration().configure("C:\\Users\\Administrator\\Desktop\\myWorkspace\\mssql\\src\\hibernate.cfg.xml");
			Configuration configuration=new Configuration().configure();
			serviceRegistry=new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory=configuration.buildSessionFactory(serviceRegistry);
			
			//sessionFactory=new Configuration().configure().buildSessionFactory();
			 */
			sessionFactory=new Configuration().configure().buildSessionFactory();
		} catch (HibernateException e) {
			System.out.println("Problem occured HibernateException");
			e.printStackTrace();
		}
	}
	
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
