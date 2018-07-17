package hpe.csa.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceRequest {
	
	private String vmHostname;
	private String requestor;
	private int durationDays;
	
	public static String INVALID_REQUEST_OBJECT="Invalid request object. Input parameters are"
			+ " vmHostname, requestor and durationDays"; 
	
	public String getVmHostname() {
		return vmHostname;
	}
	public void setVmHostname(String vmHostname) {
		this.vmHostname = vmHostname;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public int getDurationDays() {
		return durationDays;
	}
	public void setDurationDays(int durationDays) {
		this.durationDays = durationDays;
	}
	
	
}
