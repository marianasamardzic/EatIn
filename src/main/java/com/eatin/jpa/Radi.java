package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Radi database table.
 * 
 */
@Entity
@NamedQuery(name="Radi.findAll", query="SELECT r FROM Radi r")
public class Radi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RADI_IDRADI_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RADI_IDRADI_GENERATOR")
	@Column(name="id_radi")
	private int idRadi;

	@Column(name="vreme_do")
	private String vremeDo;

	@Column(name="vreme_od")
	private String vremeOd;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

	//bi-directional many-to-one association to Tip_datuma
	@ManyToOne
	@JoinColumn(name="id_tipa_datuma")
	private Tip_datuma tipDatuma;

	public Radi() {
	}

	public int getIdRadi() {
		return this.idRadi;
	}

	public void setIdRadi(int idRadi) {
		this.idRadi = idRadi;
	}

	public String getVremeDo() {
		return this.vremeDo;
	}

	public void setVremeDo(String vremeDo) {
		this.vremeDo = vremeDo;
	}

	public String getVremeOd() {
		return this.vremeOd;
	}

	public void setVremeOd(String vremeOd) {
		this.vremeOd = vremeOd;
	}

	public Restoran getRestoran() {
		return this.restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

	public Tip_datuma getTipDatuma() {
		return this.tipDatuma;
	}

	public void setTipDatuma(Tip_datuma tipDatuma) {
		this.tipDatuma = tipDatuma;
	}

}