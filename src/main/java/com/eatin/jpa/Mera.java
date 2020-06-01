package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Mera database table.
 * 
 */
@Entity
@NamedQuery(name="Mera.findAll", query="SELECT m FROM Mera m")
public class Mera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MERA_IDMERE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MERA_IDMERE_GENERATOR")
	@Column(name="id_mere")
	private int idMere;

	@Column(name="opis_mere")
	private String opisMere;

	//bi-directional many-to-one association to Moze_biti_mere
	@OneToMany(mappedBy="mera")
	private List<Moze_biti_mere> mozeBitiMeres;

	//bi-directional many-to-one association to Sadrzi
	@OneToMany(mappedBy="mera")
	private List<Sadrzi> sadrzis;

	public Mera() {
	}

	public int getIdMere() {
		return this.idMere;
	}

	public void setIdMere(int idMere) {
		this.idMere = idMere;
	}

	public String getOpisMere() {
		return this.opisMere;
	}

	public void setOpisMere(String opisMere) {
		this.opisMere = opisMere;
	}

	public List<Moze_biti_mere> getMozeBitiMeres() {
		return this.mozeBitiMeres;
	}

	public void setMozeBitiMeres(List<Moze_biti_mere> mozeBitiMeres) {
		this.mozeBitiMeres = mozeBitiMeres;
	}

	public Moze_biti_mere addMozeBitiMere(Moze_biti_mere mozeBitiMere) {
		getMozeBitiMeres().add(mozeBitiMere);
		mozeBitiMere.setMera(this);

		return mozeBitiMere;
	}

	public Moze_biti_mere removeMozeBitiMere(Moze_biti_mere mozeBitiMere) {
		getMozeBitiMeres().remove(mozeBitiMere);
		mozeBitiMere.setMera(null);

		return mozeBitiMere;
	}

	public List<Sadrzi> getSadrzis() {
		return this.sadrzis;
	}

	public void setSadrzis(List<Sadrzi> sadrzis) {
		this.sadrzis = sadrzis;
	}

	public Sadrzi addSadrzi(Sadrzi sadrzi) {
		getSadrzis().add(sadrzi);
		sadrzi.setMera(this);

		return sadrzi;
	}

	public Sadrzi removeSadrzi(Sadrzi sadrzi) {
		getSadrzis().remove(sadrzi);
		sadrzi.setMera(null);

		return sadrzi;
	}

}