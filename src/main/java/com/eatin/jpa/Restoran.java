package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Restoran database table.
 * 
 */
@Entity
@NamedQuery(name="Restoran.findAll", query="SELECT r FROM Restoran r")
public class Restoran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RESTORAN_IDRESTORANA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTORAN_IDRESTORANA_GENERATOR")
	@Column(name="id_restorana")
	private int idRestorana;

	@Column(name="naziv_restorana")
	private String nazivRestorana;

	@Column(name="opis_restorana")
	private String opisRestorana;

	@Column(name="pib_restorana")
	private int pibRestorana;

	@Column(name="slika_restorana")
	private String slikaRestorana;

	@Column(name="telefon_restorana")
	private String telefonRestorana;

	//bi-directional many-to-one association to Artikl
	@OneToMany(mappedBy="restoran")
	private List<Artikl> artikls;

	//bi-directional many-to-one association to Je_tipa
	@OneToMany(mappedBy="restoran")
	private List<Je_tipa> jeTipas;

	//bi-directional many-to-one association to Radi
	@OneToMany(mappedBy="restoran")
	private List<Radi> radis;

	//bi-directional many-to-one association to Restoran_se_nalazi
	@OneToMany(mappedBy="restoran")
	private List<Restoran_se_nalazi> restoranSeNalazis;

	//bi-directional many-to-one association to Zaposleni
	@OneToMany(mappedBy="restoran")
	private List<Zaposleni> zaposlenis;

	public Restoran() {
	}

	public int getIdRestorana() {
		return this.idRestorana;
	}

	public void setIdRestorana(int idRestorana) {
		this.idRestorana = idRestorana;
	}

	public String getNazivRestorana() {
		return this.nazivRestorana;
	}

	public void setNazivRestorana(String nazivRestorana) {
		this.nazivRestorana = nazivRestorana;
	}

	public String getOpisRestorana() {
		return this.opisRestorana;
	}

	public void setOpisRestorana(String opisRestorana) {
		this.opisRestorana = opisRestorana;
	}

	public int getPibRestorana() {
		return this.pibRestorana;
	}

	public void setPibRestorana(int pibRestorana) {
		this.pibRestorana = pibRestorana;
	}

	public String getSlikaRestorana() {
		return this.slikaRestorana;
	}

	public void setSlikaRestorana(String slikaRestorana) {
		this.slikaRestorana = slikaRestorana;
	}

	public String getTelefonRestorana() {
		return this.telefonRestorana;
	}

	public void setTelefonRestorana(String telefonRestorana) {
		this.telefonRestorana = telefonRestorana;
	}

	public List<Artikl> getArtikls() {
		return this.artikls;
	}

	public void setArtikls(List<Artikl> artikls) {
		this.artikls = artikls;
	}

	public Artikl addArtikl(Artikl artikl) {
		getArtikls().add(artikl);
		artikl.setRestoran(this);

		return artikl;
	}

	public Artikl removeArtikl(Artikl artikl) {
		getArtikls().remove(artikl);
		artikl.setRestoran(null);

		return artikl;
	}

	public List<Je_tipa> getJeTipas() {
		return this.jeTipas;
	}

	public void setJeTipas(List<Je_tipa> jeTipas) {
		this.jeTipas = jeTipas;
	}

	public Je_tipa addJeTipa(Je_tipa jeTipa) {
		getJeTipas().add(jeTipa);
		jeTipa.setRestoran(this);

		return jeTipa;
	}

	public Je_tipa removeJeTipa(Je_tipa jeTipa) {
		getJeTipas().remove(jeTipa);
		jeTipa.setRestoran(null);

		return jeTipa;
	}

	public List<Radi> getRadis() {
		return this.radis;
	}

	public void setRadis(List<Radi> radis) {
		this.radis = radis;
	}

	public Radi addRadi(Radi radi) {
		getRadis().add(radi);
		radi.setRestoran(this);

		return radi;
	}

	public Radi removeRadi(Radi radi) {
		getRadis().remove(radi);
		radi.setRestoran(null);

		return radi;
	}

	public List<Restoran_se_nalazi> getRestoranSeNalazis() {
		return this.restoranSeNalazis;
	}

	public void setRestoranSeNalazis(List<Restoran_se_nalazi> restoranSeNalazis) {
		this.restoranSeNalazis = restoranSeNalazis;
	}

	public Restoran_se_nalazi addRestoranSeNalazi(Restoran_se_nalazi restoranSeNalazi) {
		getRestoranSeNalazis().add(restoranSeNalazi);
		restoranSeNalazi.setRestoran(this);

		return restoranSeNalazi;
	}

	public Restoran_se_nalazi removeRestoranSeNalazi(Restoran_se_nalazi restoranSeNalazi) {
		getRestoranSeNalazis().remove(restoranSeNalazi);
		restoranSeNalazi.setRestoran(null);

		return restoranSeNalazi;
	}

	public List<Zaposleni> getZaposlenis() {
		return this.zaposlenis;
	}

	public void setZaposlenis(List<Zaposleni> zaposlenis) {
		this.zaposlenis = zaposlenis;
	}

	public Zaposleni addZaposleni(Zaposleni zaposleni) {
		getZaposlenis().add(zaposleni);
		zaposleni.setRestoran(this);

		return zaposleni;
	}

	public Zaposleni removeZaposleni(Zaposleni zaposleni) {
		getZaposlenis().remove(zaposleni);
		zaposleni.setRestoran(null);

		return zaposleni;
	}

}