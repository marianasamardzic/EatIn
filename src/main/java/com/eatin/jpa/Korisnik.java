package com.eatin.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;


/**
 * The persistent class for the Korisnik database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;
//	@Id
//	@SequenceGenerator(name = "ARTIKL_ID_GENERATOR", sequenceName = "ARTIKL_SEQ", allocationSize = 1)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARTIKL_ID_GENERATOR")
	@Id
	@SequenceGenerator(name = "KORISNIK_IDKORISNIKA_GENERATOR", sequenceName = "Korisnik_sequence", allocationSize = 1)
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

}