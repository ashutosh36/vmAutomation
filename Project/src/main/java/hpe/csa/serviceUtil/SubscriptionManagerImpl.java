package hpe.csa.serviceUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import hpe.csa.helperUtils.Helper;
import hpe.csa.helperUtils.ServiceProperties;
import hpe.csa.model.ServiceRequest;
import hpe.csa.model.Subscription;
import hpe.csa.model.Users;
import hpe.csa.model.VM;
import hpe.csa.model.Dao.SubscriptionDaoImpl;
import hpe.csa.model.Dao.VMDaoImpl;

public class SubscriptionManagerImpl {
	
	private Logger logger=Logger.getLogger(SubscriptionManagerImpl.class.getName());

	private SubscriptionDaoImpl subscriptionDaoImpl=new SubscriptionDaoImpl();
	private VMDaoImpl vmDaoImpl=new VMDaoImpl();
	private Helper helper=new Helper();
	
	public VM requestVM(ServiceRequest request) throws RuntimeException{
		if(request.getRequestor()==null||request.getDurationDays()<0||request.getRequestor()==null) {
			throw new RuntimeException(ServiceRequest.INVALID_REQUEST_OBJECT);
		}
		
		VM vm=vmDaoImpl.searchByHostname(request.getVmHostname());
		
		if(vm==null) {
			throw new RuntimeException("No vm found with given hostname");
		}
		
		System.out.println("FoundVM");
		vm.printVM();
		if(vm.isInUse()) {
			throw new RuntimeException("Requested vm is in use");
		}else {
			Subscription subscription=new Subscription();
			subscription.setSubscriber(request.getRequestor());
			subscription.setCreatedOn(helper.getSqlDateFromToday(0));
			subscription.setExpiresOn(helper.getSqlDateFromToday(request.getDurationDays()));
			
			vm.setInUse(true);
			vm.setSubscription(subscription);
			vmDaoImpl.add(vm);
			
		}
		
		return vm;
	}
	
	public List<VM> getToBeCleanedSubscriptions() {
		Properties prop=new ServiceProperties().getProperties();
		
		String expiryNotificationDaysString=prop.getProperty("expiryNotificationDays").trim();
		int expiryNotificationDays=Integer.parseInt(expiryNotificationDaysString);
		Timestamp expiryDate=new Helper().getSqlDateFromToday(expiryNotificationDays);
		
		List<VM> aboutToExpireVMs=subscriptionDaoImpl.getAboutToExpireSubs(expiryDate);
		for(VM v:aboutToExpireVMs) {
			logger.info("All VMs are:"+v.toString());
			v.printVM();
		}
		return aboutToExpireVMs;
	}
	
	public List<VM> deleteExpiredSubscriptions() {
		
		List<VM> vms=getToBeCleanedSubscriptions();
		
		for(VM v:vms) {
			logger.info("All VMs are:"+v.toString());
			v.setInUse(false);
			v.setSubscription(null);
		}
		try {
			vmDaoImpl.updateAll(vms);
		}catch (Exception e){
			System.out.println("Couldn't update all objects"+e.getMessage());
			logger.info("Couldn't update all objects"+e.getMessage());
		}
		
		return vms;
	}
	
	public ServiceRequest validateServiceRequest(ServiceRequest request,String authorization) {
		
		if(request.getVmHostname()==null||request.getVmHostname().length()==0) {
			throw new RuntimeException("Invalid Service Request. Wrong hostname property. Use <home>/myVMs/getAvailable to search for available vms");
		}else if(request.getRequestor()==null||request.getRequestor().length()==0) {
			Users u=Helper.getHeaderUser(authorization);
			request.setRequestor(u.getUsername());
		}else if(request.getDurationDays()<1) {
			throw new RuntimeException("Invalid service request duration");
		}
		
		return request;
	}
}
