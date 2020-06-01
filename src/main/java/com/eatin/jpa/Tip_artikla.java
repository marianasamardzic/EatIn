package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Tip_artikla database table.
 * 
 */
@Entity
@NamedQuery(name="Tip_artikla.findAll", query="SELECT t FROM Tip_artikla t")
public class Tip_artikla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIP_ARTIKLA_IDTIPAARTIKLA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIP_ARTIKLA_IDTIPAARTIKLA_GENERATOR")
	@Column(name="id_tipa_artikla")
	private int idTipaArtikla;

	@Column(name="opis_tipa_artikla")
	private String opisTipaArtikla;

	//bi-directional many-to-one association to Artikl
	@OneToMany(mappedBy="tipArtikla")
	private List<Artikl> artikls;

	public Tip_artikla() {
	}

	public int getIdTipaArtikla() {
		return this.idTipaArtikla;
	}

	public void setIdTipaArtikla(int idTipaArtikla) {
		this.idTipaArtikla = idTipaArtikla;
	}

	public String getOpisTipaArtikla() {
		return this.opisTipaArtikla;
	}

	public void setOpisTipaArtikla(String opisTipaArtikla) {
		this.opisTipaArtikla = opisTipaArtikla;
	}

	public List<Artikl> getArtikls() {
		return this.artikls;
	}

	public void setArtikls(List<Artikl> artikls) {
		this.artikls = artikls;
	}

	public Artikl addArtikl(Artikl artikl) {
		getArtikls().add(artikl);
		artikl.setTipArtikla(this);

		return artikl;
	}

	public Artikl removeArtikl(Artikl artikl) {
		getArtikls().remove(artikl);
		artikl.setTipArtikla(null);

		return artikl;
	}

}