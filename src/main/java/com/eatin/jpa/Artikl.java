package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the Artikl database table.
 * 
 */
@Entity
@NamedQuery(name="Artikl.findAll", query="SELECT a FROM Artikl a")
public class Artikl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ARTIKL_IDARTIKLA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARTIKL_IDARTIKLA_GENERATOR")
	@Column(name="id_artikla")
	private int idArtikla;

	@Column(name="cena_artikla")
	private BigDecimal cenaArtikla;

	@Column(name="naziv_artikla")
	private String nazivArtikla;

	@Column(name="slika_artikla")
	private String slikaArtikla;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

	//bi-directional many-to-one association to Tip_artikla
	@ManyToOne
	@JoinColumn(name="id_tipa_artikla")
	private Tip_artikla tipArtikla;

	//bi-directional many-to-one association to Moze_biti_mere
	@OneToMany(mappedBy="artikl")
	private List<Moze_biti_mere> mozeBitiMeres;

	//bi-directional many-to-one association to Moze_sadrzati_priloge
	@OneToMany(mappedBy="artikl")
	private List<Moze_sadrzati_priloge> mozeSadrzatiPriloges;

	//bi-directional many-to-one association to Sadrzi
	@OneToMany(mappedBy="artikl")
	private List<Sadrzi> sadrzis;

	public Artikl() {
	}

	public int getIdArtikla() {
		return this.idArtikla;
	}

	public void setIdArtikla(int idArtikla) {
		this.idArtikla = idArtikla;
	}

	public BigDecimal getCenaArtikla() {
		return this.cenaArtikla;
	}

	public void setCenaArtikla(BigDecimal cenaArtikla) {
		this.cenaArtikla = cenaArtikla;
	}

	public String getNazivArtikla() {
		return this.nazivArtikla;
	}

	public void setNazivArtikla(String nazivArtikla) {
		this.nazivArtikla = nazivArtikla;
	}

	public String getSlikaArtikla() {
		return this.slikaArtikla;
	}

	public void setSlikaArtikla(String slikaArtikla) {
		this.slikaArtikla = slikaArtikla;
	}

	public Restoran getRestoran() {
		return this.restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

	public Tip_artikla getTipArtikla() {
		return this.tipArtikla;
	}

	public void setTipArtikla(Tip_artikla tipArtikla) {
		this.tipArtikla = tipArtikla;
	}

	public List<Moze_biti_mere> getMozeBitiMeres() {
		return this.mozeBitiMeres;
	}

	public void setMozeBitiMeres(List<Moze_biti_mere> mozeBitiMeres) {
		this.mozeBitiMeres = mozeBitiMeres;
	}

	public Moze_biti_mere addMozeBitiMere(Moze_biti_mere mozeBitiMere) {
		getMozeBitiMeres().add(mozeBitiMere);
		mozeBitiMere.setArtikl(this);

		return mozeBitiMere;
	}

	public Moze_biti_mere removeMozeBitiMere(Moze_biti_mere mozeBitiMere) {
		getMozeBitiMeres().remove(mozeBitiMere);
		mozeBitiMere.setArtikl(null);

		return mozeBitiMere;
	}

	public List<Moze_sadrzati_priloge> getMozeSadrzatiPriloges() {
		return this.mozeSadrzatiPriloges;
	}

	public void setMozeSadrzatiPriloges(List<Moze_sadrzati_priloge> mozeSadrzatiPriloges) {
		this.mozeSadrzatiPriloges = mozeSadrzatiPriloges;
	}

	public Moze_sadrzati_priloge addMozeSadrzatiPriloge(Moze_sadrzati_priloge mozeSadrzatiPriloge) {
		getMozeSadrzatiPriloges().add(mozeSadrzatiPriloge);
		mozeSadrzatiPriloge.setArtikl(this);

		return mozeSadrzatiPriloge;
	}

	public Moze_sadrzati_priloge removeMozeSadrzatiPriloge(Moze_sadrzati_priloge mozeSadrzatiPriloge) {
		getMozeSadrzatiPriloges().remove(mozeSadrzatiPriloge);
		mozeSadrzatiPriloge.setArtikl(null);

		return mozeSadrzatiPriloge;
	}

	public List<Sadrzi> getSadrzis() {
		return this.sadrzis;
	}

	public void setSadrzis(List<Sadrzi> sadrzis) {
		this.sadrzis = sadrzis;
	}

	public Sadrzi addSadrzi(Sadrzi sadrzi) {
		getSadrzis().add(sadrzi);
		sadrzi.setArtikl(this);

		return sadrzi;
	}

	public Sadrzi removeSadrzi(Sadrzi sadrzi) {
		getSadrzis().remove(sadrzi);
		sadrzi.setArtikl(null);

		return sadrzi;
	}

}