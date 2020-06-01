package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Tip_datuma database table.
 * 
 */
@Entity
@NamedQuery(name="Tip_datuma.findAll", query="SELECT t FROM Tip_datuma t")
public class Tip_datuma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIP_DATUMA_IDTIPADATUMA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIP_DATUMA_IDTIPADATUMA_GENERATOR")
	@Column(name="id_tipa_datuma")
	private int idTipaDatuma;

	@Column(name="opis_tipa_datuma")
	private String opisTipaDatuma;

	//bi-directional many-to-one association to Radi
	@OneToMany(mappedBy="tipDatuma")
	private List<Radi> radis;

	public Tip_datuma() {
	}

	public int getIdTipaDatuma() {
		return this.idTipaDatuma;
	}

	public void setIdTipaDatuma(int idTipaDatuma) {
		this.idTipaDatuma = idTipaDatuma;
	}

	public String getOpisTipaDatuma() {
		return this.opisTipaDatuma;
	}

	public void setOpisTipaDatuma(String opisTipaDatuma) {
		this.opisTipaDatuma = opisTipaDatuma;
	}

	public List<Radi> getRadis() {
		return this.radis;
	}

	public void setRadis(List<Radi> radis) {
		this.radis = radis;
	}

	public Radi addRadi(Radi radi) {
		getRadis().add(radi);
		radi.setTipDatuma(this);

		return radi;
	}

	public Radi removeRadi(Radi radi) {
		getRadis().remove(radi);
		radi.setTipDatuma(null);

		return radi;
	}

}