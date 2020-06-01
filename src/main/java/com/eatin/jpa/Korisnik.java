package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="KORISNIK_IDKORISNIKA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KORISNIK_IDKORISNIKA_GENERATOR")
	@Column(name="id_korisnika")
	private int idKorisnika;

	@Column(name="email_korisnika")
	private String emailKorisnika;

	@Column(name="ime_korisnika")
	private String imeKorisnika;

	@Column(name="lozinka_korisnika")
	private String lozinkaKorisnika;

	@Column(name="prezime_korisnika")
	private String prezimeKorisnika;

	@Column(name="telefon_korisnika")
	private String telefonKorisnika;

	//bi-directional one-to-one association to Dostavljac
	@OneToOne(mappedBy="korisnik")
	private Dostavljac dostavljac;

	//bi-directional one-to-one association to Klijent
	@OneToOne(mappedBy="korisnik")
	private Klijent klijent;

	//bi-directional many-to-one association to Uloga
	@ManyToOne
	@JoinColumn(name="id_uloge")
	private Uloga uloga;

	//bi-directional one-to-one association to Zaposleni
	@OneToOne(mappedBy="korisnik")
	private Zaposleni zaposleni;

	public Korisnik() {
	}

	public int getIdKorisnika() {
		return this.idKorisnika;
	}

	public void setIdKorisnika(int idKorisnika) {
		this.idKorisnika = idKorisnika;
	}

	public String getEmailKorisnika() {
		return this.emailKorisnika;
	}

	public void setEmailKorisnika(String emailKorisnika) {
		this.emailKorisnika = emailKorisnika;
	}

	public String getImeKorisnika() {
		return this.imeKorisnika;
	}

	public void setImeKorisnika(String imeKorisnika) {
		this.imeKorisnika = imeKorisnika;
	}

	public String getLozinkaKorisnika() {
		return this.lozinkaKorisnika;
	}

	public void setLozinkaKorisnika(String lozinkaKorisnika) {
		this.lozinkaKorisnika = lozinkaKorisnika;
	}

	public String getPrezimeKorisnika() {
		return this.prezimeKorisnika;
	}

	public void setPrezimeKorisnika(String prezimeKorisnika) {
		this.prezimeKorisnika = prezimeKorisnika;
	}

	public String getTelefonKorisnika() {
		return this.telefonKorisnika;
	}

	public void setTelefonKorisnika(String telefonKorisnika) {
		this.telefonKorisnika = telefonKorisnika;
	}

	public Dostavljac getDostavljac() {
		return this.dostavljac;
	}

	public void setDostavljac(Dostavljac dostavljac) {
		this.dostavljac = dostavljac;
	}

	public Klijent getKlijent() {
		return this.klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public Uloga getUloga() {
		return this.uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public Zaposleni getZaposleni() {
		return this.zaposleni;
	}

	public void setZaposleni(Zaposleni zaposleni) {
		this.zaposleni = zaposleni;
	}

}