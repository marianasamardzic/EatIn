package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Zaposleni database table.
 * 
 */
@Entity
@NamedQuery(name="Zaposleni.findAll", query="SELECT z FROM Zaposleni z")
public class Zaposleni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ZAPOSLENI_IDZAPOSLENOG_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ZAPOSLENI_IDZAPOSLENOG_GENERATOR")
	@Column(name="id_zaposlenog")
	private int idZaposlenog;

	@Column(name="funkcija_zaposlenog")
	private String funkcijaZaposlenog;

	//bi-directional one-to-one association to Korisnik
	@OneToOne
	@JoinColumn(name="id_zaposlenog")
	private Korisnik korisnik;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

	public Zaposleni() {
	}

	public int getIdZaposlenog() {
		return this.idZaposlenog;
	}

	public void setIdZaposlenog(int idZaposlenog) {
		this.idZaposlenog = idZaposlenog;
	}

	public String getFunkcijaZaposlenog() {
		return this.funkcijaZaposlenog;
	}

	public void setFunkcijaZaposlenog(String funkcijaZaposlenog) {
		this.funkcijaZaposlenog = funkcijaZaposlenog;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Restoran getRestoran() {
		return this.restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

}