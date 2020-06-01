package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Restoran_se_nalazi database table.
 * 
 */
@Entity
@NamedQuery(name="Restoran_se_nalazi.findAll", query="SELECT r FROM Restoran_se_nalazi r")
public class Restoran_se_nalazi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RESTORAN_SE_NALAZI_IDRESTORANSENALAZI_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTORAN_SE_NALAZI_IDRESTORANSENALAZI_GENERATOR")
	@Column(name="id_restoran_se_nalazi")
	private int idRestoranSeNalazi;

	//bi-directional many-to-one association to Lokacija
	@ManyToOne
	@JoinColumn(name="id_lokacije")
	private Lokacija lokacija;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

	public Restoran_se_nalazi() {
	}

	public int getIdRestoranSeNalazi() {
		return this.idRestoranSeNalazi;
	}

	public void setIdRestoranSeNalazi(int idRestoranSeNalazi) {
		this.idRestoranSeNalazi = idRestoranSeNalazi;
	}

	public Lokacija getLokacija() {
		return this.lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}

	public Restoran getRestoran() {
		return this.restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

}