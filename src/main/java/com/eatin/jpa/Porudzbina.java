package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the Porudzbina database table.
 * 
 */
@Entity
@NamedQuery(name="Porudzbina.findAll", query="SELECT p FROM Porudzbina p")
public class Porudzbina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PORUDZBINA_IDPORUDZBINE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PORUDZBINA_IDPORUDZBINE_GENERATOR")
	@Column(name="id_porudzbine")
	private int idPorudzbine;

	@Column(name="status_porudzbine")
	private String statusPorudzbine;

	@Column(name="ukupna_cena")
	private BigDecimal ukupnaCena;

	@Column(name="vreme_isporuke_porudzbine")
	private String vremeIsporukePorudzbine;

	@Column(name="vreme_prijema_porudzbine")
	private String vremePrijemaPorudzbine;

	//bi-directional many-to-one association to Dostavljac
	@ManyToOne
	@JoinColumn(name="id_dostavljaca")
	private Dostavljac dostavljac;

	//bi-directional many-to-one association to Klijent
	@ManyToOne
	@JoinColumn(name="id_klijenta")
	private Klijent klijent;

	//bi-directional many-to-one association to Lokacija
	@ManyToOne
	@JoinColumn(name="id_lokacije")
	private Lokacija lokacija;

	//bi-directional many-to-one association to Sadrzi
	@OneToMany(mappedBy="porudzbina")
	private List<Sadrzi> sadrzis;

	public Porudzbina() {
	}

	public int getIdPorudzbine() {
		return this.idPorudzbine;
	}

	public void setIdPorudzbine(int idPorudzbine) {
		this.idPorudzbine = idPorudzbine;
	}

	public String getStatusPorudzbine() {
		return this.statusPorudzbine;
	}

	public void setStatusPorudzbine(String statusPorudzbine) {
		this.statusPorudzbine = statusPorudzbine;
	}

	public BigDecimal getUkupnaCena() {
		return this.ukupnaCena;
	}

	public void setUkupnaCena(BigDecimal ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public String getVremeIsporukePorudzbine() {
		return this.vremeIsporukePorudzbine;
	}

	public void setVremeIsporukePorudzbine(String vremeIsporukePorudzbine) {
		this.vremeIsporukePorudzbine = vremeIsporukePorudzbine;
	}

	public String getVremePrijemaPorudzbine() {
		return this.vremePrijemaPorudzbine;
	}

	public void setVremePrijemaPorudzbine(String vremePrijemaPorudzbine) {
		this.vremePrijemaPorudzbine = vremePrijemaPorudzbine;
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

	public Lokacija getLokacija() {
		return this.lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}

	public List<Sadrzi> getSadrzis() {
		return this.sadrzis;
	}

	public void setSadrzis(List<Sadrzi> sadrzis) {
		this.sadrzis = sadrzis;
	}

	public Sadrzi addSadrzi(Sadrzi sadrzi) {
		getSadrzis().add(sadrzi);
		sadrzi.setPorudzbina(this);

		return sadrzi;
	}

	public Sadrzi removeSadrzi(Sadrzi sadrzi) {
		getSadrzis().remove(sadrzi);
		sadrzi.setPorudzbina(null);

		return sadrzi;
	}

}