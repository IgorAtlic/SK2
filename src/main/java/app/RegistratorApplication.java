package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.entities.Admin;
import app.repository.AdminRepository;

@SpringBootApplication
public class RegistratorApplication {

	@Autowired
	private AdminRepository adminRepo;
	
	@Bean
	public static BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder(10);
	}

	public static void main(String[] args) {
		SpringApplication.run(RegistratorApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void addAdmin() {
		Admin admin = new Admin("Admin","12345678");
		adminRepo.saveAndFlush(admin);
	}
	
}
