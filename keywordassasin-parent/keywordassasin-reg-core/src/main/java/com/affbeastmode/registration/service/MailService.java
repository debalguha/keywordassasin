package com.affbeastmode.registration.service;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.affbeastmode.kwassasin.model.User;

public class MailService {
	private Properties smtpProps;
	private String username;
	private String password;
	private String fromAddress;

	public void sendMailTo(User user, String messageStr) throws Exception {
		System.out.println("Message Str:: "+messageStr);
		Session session = Session.getDefaultInstance(smtpProps, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject("Registration Confirmation");
			message.setContent(message, "text/html; charset=utf-8");
			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(messageStr,"UTF-8","html");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			message.setContent(multipart);
			
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void setSmtpProps(Properties smtpProps) {
		this.smtpProps = smtpProps;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

}