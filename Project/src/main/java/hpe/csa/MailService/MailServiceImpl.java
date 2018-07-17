package hpe.csa.MailService;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailServiceImpl {
	final String username = "hpecsa1234@gmail.com";
	final String password = "csacpe@0!3";
		public void sendMail(String receiver,String mailCategory) {
			/*
			SMTPSession ss=new SMTPSession();
			ss.createSession();
			Session session=ss.getSession();
			*/
			/*
			 System.getProperties().put("proxySet","true");
	         System.getProperties().put("socksProxyPort","8080");
	         System.getProperties().put("socksProxyHost","web-proxy.in.softwaregrp.net");
	         */
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			//props.put("mail.protocol.proxy.host", "web-proxy.in.softwaregrp.net");
			//props.put("mail.protocol.proxy.port", "8080");
			//props.put("mail.smtp.host", "smtp.gmail.com");
			//props.put("mail.smtp.port", "587");
			props.put("mail.smtp.host", "smtp1.hpe.com");
			props.put("mail.smtp.port", "25");

			
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
			
			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("ashutosh.kaushik@hpe.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("ashutosh.kaushik@hpe.com"));
				message.setSubject("Testing Subject");
				message.setText("Dear Mail Crawler,"
					+ "\n\n This is a test email where sender's email id is self-created, test from vm!");

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
		
		public void sendExpiryNotificationMail() {
			
		}
}
