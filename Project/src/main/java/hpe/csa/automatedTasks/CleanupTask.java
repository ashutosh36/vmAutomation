package hpe.csa.automatedTasks;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import hpe.csa.MailService.MailServiceImpl;
import hpe.csa.model.VM;
import hpe.csa.serviceUtil.SubscriptionManagerImpl;

public class CleanupTask implements Runnable{

	private SubscriptionManagerImpl subscriptionManagerImpl=new SubscriptionManagerImpl();
	private MailServiceImpl mailServiceImpl=new MailServiceImpl();
	private Logger logger=Logger.getLogger(CleanupTask.class.getName());
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Cleanup service. Time:"+ new Date());
		logger.error("Cleanup service. Time:"+ new Date());
		List<VM> toBeDeletedVMs=subscriptionManagerImpl.getToBeCleanedSubscriptions();
		System.out.println("\n\nTo be deleted VMs are");
		logger.info("\n\nTo be deleted VMs are");
		for(VM v:toBeDeletedVMs) {
			logger.info(v.toString());
			v.printVM();
			//mailServiceImpl.sendExpiryNotificationMail();
		}
		//List<VM> deletedVms=subscriptionManagerImpl.deleteExpiredSubscriptions();
	}

}
