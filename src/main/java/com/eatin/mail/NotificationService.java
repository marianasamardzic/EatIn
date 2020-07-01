package com.eatin.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendNotification(String email, String token) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("eatinco@gmail.com");
		mail.setSubject("Uspesno ste se registrovali");
		mail.setText("Molimo Vas potvrdite registraciju klikom na link : "
				+ "http://localhost:8080/confirm-account?token="
				+ token);

		this.javaMailSender.send(mail);
	}

}
