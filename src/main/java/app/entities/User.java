package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String ime;
	private String prezime;
	private String email;
	private String password;
	private int brjPasosa;
	private String rank = "Bronza";
	private int milje = 0;
	
	public User() {

	}

	public User(String ime, String prezime, String email, String password,int brjPasosa) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.password = password;
		this.brjPasosa = brjPasosa;
	}

	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getMilje() {
		return milje;
	}

	public void setMilje(int milje) {
		this.milje = milje;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBrjPasosa() {
		return brjPasosa;
	}

	public void setBrjPasosa(int brjPasosa) {
		this.brjPasosa = brjPasosa;
	}

}
