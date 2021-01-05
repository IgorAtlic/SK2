package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailController {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String to, String topic, String body) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("Servis1");
		mail.setTo(to);
		mail.setSubject(topic);
		mail.setText(body);
		javaMailSender.send(mail);
		
	}
}
