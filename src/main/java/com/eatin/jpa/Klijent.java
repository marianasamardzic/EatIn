package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Klijent database table.
 * 
 */
@Entity
@NamedQuery(name="Klijent.findAll", query="SELECT k FROM Klijent k")
public class Klijent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="KLIJENT_IDKLIJENTA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KLIJENT_IDKLIJENTA_GENERATOR")
	@Column(name="id_klijenta")
	private int idKlijenta;

	//bi-directional one-to-one association to Korisnik
	@OneToOne
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