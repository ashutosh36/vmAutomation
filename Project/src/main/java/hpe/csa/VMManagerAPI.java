package hpe.csa;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import hpe.csa.helperUtils.AuthenticationManagerImpl;
import hpe.csa.helperUtils.Helper;
import hpe.csa.hibernate.util.HibernateUtilities;
import hpe.csa.model.VM;
import hpe.csa.serviceUtil.VMManagerSvc;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myVms")
public class VMManagerAPI {

	private HiberApplication hu=new HiberApplication();
	private VMManagerSvc vMManagerSvc=new VMManagerSvc();
	private static AuthenticationManagerImpl authenticationManager=new AuthenticationManagerImpl();
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("activity")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	hu.testCommit();
        return "Got it!";
    }
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
    	List<VM> allVms=vMManagerSvc.getAll();
    	return Response.ok()
    			.entity(new GenericEntity<List<VM>>(allVms){})
    			.build();
    }
    
    @GET
    @Path("getAvailable")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailabeVms() {
    	List<VM> availableVms=vMManagerSvc.getAvailableVms();
    	for(VM v: availableVms) {
			v.setSubscription(null);
		}
    	return Response.ok()
    			.entity(new GenericEntity<List<VM>>(availableVms){})
    			.build();
    }
    
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addVM(VM vm) {
    	VM responseVm=vMManagerSvc.addVM(vm);
    	return Response.ok().entity(responseVm).build();
    }
    

	@GET
	@Path("search")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response searchByVersion(@QueryParam(value="version") String version) {
		System.out.println("Version:"+version);
		List<VM> vms=vMManagerSvc.getByVersion(version);
		for(VM v: vms) {
			v.setSubscription(null);
		}
		return Response.ok()
				.entity(new GenericEntity<List<VM>>(vms){})
				.build();
	}
	
	@GET
	@Path("header")
	@Produces({MediaType.APPLICATION_JSON})
	public Response verifyHeader(@HeaderParam(value="Authorization") String authorization) {
		return  authenticationManager.authorizeUser(authorization);
	}
}
