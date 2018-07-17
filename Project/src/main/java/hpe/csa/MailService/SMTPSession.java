package hpe.csa.MailService;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SMTPSession {
	
	private Session session;
	
	final String username = "hpecsa1234@gmail.com";
	final String password = "csacpe@0!3";
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}


	public void createSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.host", "smtp.gmail.com");
		//props.put("mail.smtp.port", "587");
		props.put("mail.smtp.host", "smtp1.hpe.com");
		props.put("mail.smtp.port", "25");

		
		Session createdSession = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		session=createdSession;
	}
	
}
