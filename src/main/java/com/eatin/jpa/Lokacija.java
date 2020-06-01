package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the Lokacija database table.
 * 
 */
@Entity
@NamedQuery(name="Lokacija.findAll", query="SELECT l FROM Lokacija l")
public class Lokacija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOKACIJA_IDLOKACIJE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOKACIJA_IDLOKACIJE_GENERATOR")
	@Column(name="id_lokacije")
	private int idLokacije;

	private String broj;

	private String grad;

	private BigDecimal latitude;

	private BigDecimal longitude;

	@Column(name="postanski_broj")
	private String postanskiBroj;

	private String ulica;

	//bi-directional many-to-one association to Klijent_se_nalazi
	@OneToMany(mappedBy="lokacija")
	private List<Klijent_se_nalazi> klijentSeNalazis;

	//bi-directional many-to-one association to Porudzbina
	@OneToMany(mappedBy="lokacija")
	private List<Porudzbina> porudzbinas;

	//bi-directional many-to-one association to Restoran_se_nalazi
	@OneToMany(mappedBy="lokacija")
	private List<Restoran_se_nalazi> restoranSeNalazis;

	public Lokacija() {
	}

	public int getIdLokacije() {
		return this.idLokacije;
	}

	public void setIdLokacije(int idLokacije) {
		this.idLokacije = idLokacije;
	}

	public String getBroj() {
		return this.broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public String getGrad() {
		return this.grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getPostanskiBroj() {
		return this.postanskiBroj;
	}

	public void setPostanskiBroj(String postanskiBroj) {
		this.postanskiBroj = postanskiBroj;
	}

	public String getUlica() {
		return this.ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public List<Klijent_se_nalazi> getKlijentSeNalazis() {
		return this.klijentSeNalazis;
	}

	public void setKlijentSeNalazis(List<Klijent_se_nalazi> klijentSeNalazis) {
		this.klijentSeNalazis = klijentSeNalazis;
	}

	public Klijent_se_nalazi addKlijentSeNalazi(Klijent_se_nalazi klijentSeNalazi) {
		getKlijentSeNalazis().add(klijentSeNalazi);
		klijentSeNalazi.setLokacija(this);

		return klijentSeNalazi;
	}

	public Klijent_se_nalazi removeKlijentSeNalazi(Klijent_se_nalazi klijentSeNalazi) {
		getKlijentSeNalazis().remove(klijentSeNalazi);
		klijentSeNalazi.setLokacija(null);

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
		porudzbina.setLokacija(this);

		return porudzbina;
	}

	public Porudzbina removePorudzbina(Porudzbina porudzbina) {
		getPorudzbinas().remove(porudzbina);
		porudzbina.setLokacija(null);

		return porudzbina;
	}

	public List<Restoran_se_nalazi> getRestoranSeNalazis() {
		return this.restoranSeNalazis;
	}

	public void setRestoranSeNalazis(List<Restoran_se_nalazi> restoranSeNalazis) {
		this.restoranSeNalazis = restoranSeNalazis;
	}

	public Restoran_se_nalazi addRestoranSeNalazi(Restoran_se_nalazi restoranSeNalazi) {
		getRestoranSeNalazis().add(restoranSeNalazi);
		restoranSeNalazi.setLokacija(this);

		return restoranSeNalazi;
	}

	public Restoran_se_nalazi removeRestoranSeNalazi(Restoran_se_nalazi restoranSeNalazi) {
		getRestoranSeNalazis().remove(restoranSeNalazi);
		restoranSeNalazi.setLokacija(null);

		return restoranSeNalazi;
	}

}