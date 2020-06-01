package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Uloga database table.
 * 
 */
@Entity
@NamedQuery(name="Uloga.findAll", query="SELECT u FROM Uloga u")
public class Uloga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ULOGA_IDULOGE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ULOGA_IDULOGE_GENERATOR")
	@Column(name="id_uloge")
	private int idUloge;

	@Column(name="naziv_uloge")
	private String nazivUloge;

	//bi-directional many-to-one association to Korisnik
	@OneToMany(mappedBy="uloga")
	private List<Korisnik> korisniks;

	public Uloga() {
	}

	public int getIdUloge() {
		return this.idUloge;
	}

	public void setIdUloge(int idUloge) {
		this.idUloge = idUloge;
	}

	public String getNazivUloge() {
		return this.nazivUloge;
	}

	public void setNazivUloge(String nazivUloge) {
		this.nazivUloge = nazivUloge;
	}

	public List<Korisnik> getKorisniks() {
		return this.korisniks;
	}

	public void setKorisniks(List<Korisnik> korisniks) {
		this.korisniks = korisniks;
	}

	public Korisnik addKorisnik(Korisnik korisnik) {
		getKorisniks().add(korisnik);
		korisnik.setUloga(this);

		return korisnik;
	}

	public Korisnik removeKorisnik(Korisnik korisnik) {
		getKorisniks().remove(korisnik);
		korisnik.setUloga(null);

		return korisnik;
	}

}