package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;
import static app.security.SecurityConstants.SECRET;
import static app.security.SecurityConstants.TOKEN_PREFIX;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import app.entities.Admin;
import app.entities.CreditCard;
import app.entities.User;
import app.forms.ChangeForm;
import app.forms.CreditCardForm;
import app.forms.RegistrationForm;
import app.repository.AdminRepository;
import app.repository.CreditCardRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("")
public class Controller {

	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;
	private CreditCardRepository cardRepo;
	private AdminRepository adminRepo;
	private EmailController emailController;
	
	@Autowired
	public Controller(BCryptPasswordEncoder encoder, UserRepository userRepo,CreditCardRepository cardRepo ,AdminRepository adminRepo ,EmailController emailController) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.cardRepo = cardRepo;
		this.adminRepo = adminRepo;
		this.emailController = emailController;
	}

	@PostMapping("/register")
	public ResponseEntity<String> subtractionPost(@RequestBody RegistrationForm registrationForm) {

		try {

			// iscitavamo entitet iz registracione forme
			User user = new User(registrationForm.getIme(), registrationForm.getPrezime(), registrationForm.getEmail(),
					encoder.encode(registrationForm.getPassword()),registrationForm.getBrjPasosa());
			
			//emailController.sendEmail(registrationForm.getEmail(), "Servis1", "Hvala sto ste koristili nasu aplikaciju");

			// cuvamo u nasoj bazi ovaj entitet
			userRepo.saveAndFlush(user);	        

			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping("/addCard")
	public ResponseEntity<String> addCard(@RequestBody CreditCardForm creditCardForm,@RequestHeader(value = HEADER_STRING) String token) {

		try {
			
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);

			// iscitavamo entitet iz registracione forme
			CreditCard card = new CreditCard(creditCardForm.getIme(), creditCardForm.getPrezime(), creditCardForm.getBrojKartice(), creditCardForm.getSigurnosniBroj(), user.getId());
			
			cardRepo.saveAndFlush(card);	        

			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/whoAmI")
	public ResponseEntity<String> whoAmI(@RequestHeader(value = HEADER_STRING) String token) {
		try {

			// izvlacimo iz tokena subject koj je postavljen da bude email
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			
			
			User user = userRepo.findByEmail(email);
			if (user == null) {
				Admin admin = adminRepo.findByUsername(email);
				return new ResponseEntity<>(admin.getUsername(), HttpStatus.ACCEPTED);
			}
			
			ArrayList<CreditCard> card = cardRepo.findByUser(user.getId());
			
			String tem = user.getIme() + "/"+ user.getPrezime() + "/"+ user.getEmail() + "/" + user.getBrjPasosa()+ "/" + user.getRank() +"/"+ user.getMilje();
			
			for (CreditCard c :card) {
				
				tem = tem +"/"+ c.getBrojKartice();
			}
			return new ResponseEntity<>(tem, HttpStatus.ACCEPTED);
			

			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/changeUser")
	public ResponseEntity<String> changeUser(@RequestBody RegistrationForm registrationForm, @RequestHeader(value = HEADER_STRING) String token) {
		try {

			// izvlacimo iz tokena subject koj je postavljen da bude email
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);

			if (registrationForm.getIme() != null){
				
				user.setIme(registrationForm.getIme());
			}
			if(registrationForm.getPrezime()!= null) {
				
				user.setPrezime(registrationForm.getPrezime());
			}
			if(registrationForm.getPassword()!=null) {
				
				user.setPassword(registrationForm.getPassword());
			}
			if(registrationForm.getEmail()!=null) {
				
				user.setEmail(registrationForm.getEmail());
				emailController.sendEmail(registrationForm.getEmail(), "Servis1", "Hvala sto ste koristili nasu aplikaciju(promena Email adrese)");
			}
			if(registrationForm.getBrjPasosa() != 0) {
				
				user.setBrjPasosa(registrationForm.getBrjPasosa());
			}
			userRepo.saveAndFlush(user);	        
			return new ResponseEntity<>(user.getIme() + " " + user.getPrezime(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/addMile")
	public ResponseEntity<String> changeUser(@RequestBody ChangeForm changeForm, @RequestHeader(value = HEADER_STRING) String token) {
		try {

			// izvlacimo iz tokena subject koj je postavljen da bude email
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);
			
			String rank = user.getRank();
			if (changeForm.getMilje() != 0){
				
				user.setMilje(changeForm.getMilje() + user.getMilje());
				
				if (rank != "Zlato") {
					if (user.getMilje() > 10000) {
						user.setRank("Zlato");
					}else if(user.getMilje() > 1000) {
						
						user.setRank("Srebro");
					}
					
				}
			}
			userRepo.saveAndFlush(user);	        
			return new ResponseEntity<>(user.getIme() + " " + user.getRank(), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
