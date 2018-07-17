package hpe.csa;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import hpe.csa.model.ServiceRequest;
import hpe.csa.model.Users;
import hpe.csa.model.VM;
import hpe.csa.serviceUtil.SubscriptionManagerImpl;
import hpe.csa.helperUtils.AuthenticationManagerImpl;
import hpe.csa.helperUtils.Helper;


@Path("request")
public class ServiceRequestApi {
	private AuthenticationManagerImpl authenticationManager=new AuthenticationManagerImpl();
	private Logger logger=Logger.getLogger(ServiceRequestApi.class.getName());
	
	@POST
	@Path("vm")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response requestVM(ServiceRequest request,@HeaderParam("Authorization")String authorization) {
		VM responseVm=new VM();
		SubscriptionManagerImpl serviceRequestSvc=new SubscriptionManagerImpl();
		String res="";
		System.out.println("Request. Requestore"+request.getRequestor()+" Hostname"+request.getVmHostname());

		
		Response authorizeReponse=authenticationManager.authorizeUser(authorization);
		if(authorizeReponse.getStatusInfo()!=Status.OK) {
			return authorizeReponse;
		}
		
		try {
			ServiceRequest verifiedRequest = new SubscriptionManagerImpl().validateServiceRequest(request,
					authorization);
			VM resultVm = serviceRequestSvc.requestVM(verifiedRequest);
			// VM responseVm=new
			// VM(resultVm.getHostname(),resultVm.getVersion(),resultVm.getVmUser(),resultVm.getPassword(),resultVm.isInUse());
			responseVm.setId(resultVm.getId());
			responseVm.setHostname(resultVm.getHostname());
			responseVm.setVersion(resultVm.getVersion());
			responseVm.setVmUser(resultVm.getVmUser());
			responseVm.setPassword(resultVm.getPassword());
			responseVm.setInUse(resultVm.isInUse());
			System.out.println("Response VM:");
			responseVm.printVM();
		} catch (RuntimeException re) {
			res += re.getMessage();
			logger.error("RuntimeException" + res);
		} catch (Exception e) {
			res += e.getMessage();
		}
		if (res.length() > 0) {
			return Response.ok().entity(res).build();
		}
		
		return Response.ok().entity(responseVm).build();
	}
	
	
	
}
