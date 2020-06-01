package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Klijent_se_nalazi database table.
 * 
 */
@Entity
@NamedQuery(name="Klijent_se_nalazi.findAll", query="SELECT k FROM Klijent_se_nalazi k")
public class Klijent_se_nalazi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="KLIJENT_SE_NALAZI_IDKLIJENTSENALAZI_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KLIJENT_SE_NALAZI_IDKLIJENTSENALAZI_GENERATOR")
	@Column(name="id_klijent_se_nalazi")
	private int idKlijentSeNalazi;

	@Column(name="aktuelna_adresa")
	private boolean aktuelnaAdresa;

	//bi-directional many-to-one association to Klijent
	@ManyToOne
	@JoinColumn(name="id_klijenta")
	private Klijent klijent;

	//bi-directional many-to-one association to Lokacija
	@ManyToOne
	@JoinColumn(name="id_lokacije")
	private Lokacija lokacija;

	public Klijent_se_nalazi() {
	}

	public int getIdKlijentSeNalazi() {
		return this.idKlijentSeNalazi;
	}

	public void setIdKlijentSeNalazi(int idKlijentSeNalazi) {
		this.idKlijentSeNalazi = idKlijentSeNalazi;
	}

	public boolean getAktuelnaAdresa() {
		return this.aktuelnaAdresa;
	}

	public void setAktuelnaAdresa(boolean aktuelnaAdresa) {
		this.aktuelnaAdresa = aktuelnaAdresa;
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

}