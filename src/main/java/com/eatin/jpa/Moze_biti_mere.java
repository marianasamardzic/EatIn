package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Moze_biti_mere database table.
 * 
 */
@Entity
@NamedQuery(name="Moze_biti_mere.findAll", query="SELECT m FROM Moze_biti_mere m")
public class Moze_biti_mere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MOZE_BITI_MERE_IDMOZEBITIMERE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MOZE_BITI_MERE_IDMOZEBITIMERE_GENERATOR")
	@Column(name="id_moze_biti_mere")
	private int idMozeBitiMere;

	//bi-directional many-to-one association to Artikl
	@ManyToOne
	@JoinColumn(name="id_artikla")
	private Artikl artikl;

	//bi-directional many-to-one association to Mera
	@ManyToOne
	@JoinColumn(name="id_mere")
	private Mera mera;

	public Moze_biti_mere() {
	}

	public int getIdMozeBitiMere() {
		return this.idMozeBitiMere;
	}

	public void setIdMozeBitiMere(int idMozeBitiMere) {
		this.idMozeBitiMere = idMozeBitiMere;
	}

	public Artikl getArtikl() {
		return this.artikl;
	}

	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}

	public Mera getMera() {
		return this.mera;
	}

	public void setMera(Mera mera) {
		this.mera = mera;
	}

}