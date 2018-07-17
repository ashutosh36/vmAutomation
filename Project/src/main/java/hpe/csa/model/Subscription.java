package hpe.csa.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
	@NamedQuery(name="sr.getAboutToExpireSubs", query="select v from VM v where v.inUse=true and v.subscription.expiresOn<=:expiryDate")
})
public class Subscription {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String subscriber;
	
	@OneToOne(mappedBy="subscription")
	private VM vm;
	private Timestamp createdOn;
	private Timestamp expiresOn;

	public Subscription() {};
	public Subscription(String subscriber,Timestamp createdOn,Timestamp expiresOn) {
		this.subscriber=subscriber;
		this.createdOn=createdOn;
		this.expiresOn=expiresOn;
	};
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}

	public VM getVm() {
		return vm;
	}

	public void setVm(VM vm) {
		this.vm = vm;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public Timestamp getExpiresOn() {
		return expiresOn;
	}
	public void setExpiresOn(Timestamp expiresOn) {
		this.expiresOn = expiresOn;
	}
	
}
