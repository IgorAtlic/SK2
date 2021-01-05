package app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long user;
	private String ime;
	private String prezime;
	private int brojKartice;
	private int sigurnosniBroj;
	
	public CreditCard(String ime, String prezime, int brojKartice, int sigurnosniBroj,long user) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.brojKartice = brojKartice;
		this.sigurnosniBroj = sigurnosniBroj;
		this.user = user;
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
	public int getBrojKartice() {
		return brojKartice;
	}
	public void setBrojKartice(int brojKartice) {
		this.brojKartice = brojKartice;
	}
	public int getSigurnosniBroj() {
		return sigurnosniBroj;
	}
	public void setSigurnosniBroj(int sigurnosniBroj) {
		this.sigurnosniBroj = sigurnosniBroj;
	}
	public long getUser() {
		return user;
	}
	public void setUser(long user) {
		this.user = user;
	}
	
	
	
	
	
}
