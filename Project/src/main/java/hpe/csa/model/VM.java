package hpe.csa.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
	@NamedQuery(name="vm.findAll", query="select v from VM v"),
	@NamedQuery(name="vm.findAvailableVms", query="select v from VM v where v.subscription is null"),
	@NamedQuery(name="vm.findByVersion", query="select v from VM v where inUse=false and v.version=:version"),
	@NamedQuery(name="vm.findByHostname", query="select v from VM v where v.hostname=:hostname")
})
public class VM {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String hostname;
	private String version;
	private String password;
	private String vmUser;
	private boolean inUse; 
	
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn
	private Subscription subscription;

	public VM() {};
	public VM(String hostname,String version,String vmUser,String password,boolean inUse) {
		this.hostname=hostname;
		this.version=version;
		this.password=password;
		this.vmUser=vmUser;
		this.inUse=inUse;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVmUser() {
		return vmUser;
	}
	public void setVmUser(String vmUser) {
		this.vmUser = vmUser;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	public Subscription getSubscription() {
		return subscription;
	}
	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}
	
	public void printVM() {
		String res="hostname"+this.hostname+" vmUser:"+this.vmUser+" password:"+this.password+""
				+ " inUse:"+this.inUse;
		if(this.getSubscription()!=null) {
			res+=" subscriber:"+this.getSubscription().getSubscriber();
		}
		System.out.println(res);
	}
	
	@Override
	public String toString() {
		String res="hostname"+this.hostname+" vmUser:"+this.vmUser+" password:"+this.password+""
				+ " inUse:"+this.inUse;
		if(this.getSubscription()!=null) {
			res+=" subscriber:"+this.getSubscription().getSubscriber();
		}
		return res;
	}

}
