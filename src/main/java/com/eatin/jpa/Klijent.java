package com.eatin.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 * The persistent class for the Klijent database table.
 * 
 */
@Entity
@NamedQuery(name="Klijent.findAll", query="SELECT k FROM Klijent k")
public class Klijent implements Serializable {
	private static final long serialVersionUID = 1L;

//	@SequenceGenerator(name = "KORISNIK_IDKORISNIKA_GENERATOR", sequenceName = "Korisnik_sequence", allocationSize = 1)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KORISNIK_IDKORISNIKA_GENERATOR")
	@Id
	@Column(name="id_klijenta")
	private int idKlijenta;

	//bi-directional one-to-one association to Korisnik
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="id_klijenta")
	private Korisnik korisnik;

	//bi-directional many-to-one association to Klijent_se_nalazi
	@OneToMany(mappedBy="klijent")
	private List<Klijent_se_nalazi> klijentSeNalazis;

	//bi-directional many-to-one association to Porudzbina
	@OneToMany(mappedBy="klijent")
	private List<Porudzbina> porudzbinas;

	public Klijent() {
	}

	public int getIdKlijenta() {
		return this.idKlijenta;
	}

	public void setIdKlijenta(int idKlijenta) {
		this.idKlijenta = idKlijenta;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public List<Klijent_se_nalazi> getKlijentSeNalazis() {
		return this.klijentSeNalazis;
	}

	public void setKlijentSeNalazis(List<Klijent_se_nalazi> klijentSeNalazis) {
		this.klijentSeNalazis = klijentSeNalazis;
	}

	public Klijent_se_nalazi addKlijentSeNalazi(Klijent_se_nalazi klijentSeNalazi) {
		getKlijentSeNalazis().add(klijentSeNalazi);
		klijentSeNalazi.setKlijent(this);

		return klijentSeNalazi;
	}

	public Klijent_se_nalazi removeKlijentSeNalazi(Klijent_se_nalazi klijentSeNalazi) {
		getKlijentSeNalazis().remove(klijentSeNalazi);
		klijentSeNalazi.setKlijent(null);

		return klijentSeNalazi;
	}

	public List<Porudzbina> getPorudzbinas() {
		return this.porudzbinas;
	}

	public void setPorudzbinas(List<Porudzbina> porudzbinas) {
		this.porudzbinas = porudzbinas;
	}

	public Porudzbina addPorudzbina(Porudzbina porudzbina) {
		getPorudzbinas().add(porudzbina);
		porudzbina.setKlijent(this);

		return porudzbina;
	}

	public Porudzbina removePorudzbina(Porudzbina porudzbina) {
		getPorudzbinas().remove(porudzbina);
		porudzbina.setKlijent(null);

		return porudzbina;
	}

}