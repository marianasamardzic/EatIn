package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Tip_restorana database table.
 * 
 */
@Entity
@NamedQuery(name="Tip_restorana.findAll", query="SELECT t FROM Tip_restorana t")
public class Tip_restorana implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIP_RESTORANA_IDTIPARESTORANA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIP_RESTORANA_IDTIPARESTORANA_GENERATOR")
	@Column(name="id_tipa_restorana")
	private int idTipaRestorana;

	@Column(name="opis_tipa_restorana")
	private String opisTipaRestorana;

	//bi-directional many-to-one association to Je_tipa
	@OneToMany(mappedBy="tipRestorana")
	private List<Je_tipa> jeTipas;

	public Tip_restorana() {
	}

	public int getIdTipaRestorana() {
		return this.idTipaRestorana;
	}

	public void setIdTipaRestorana(int idTipaRestorana) {
		this.idTipaRestorana = idTipaRestorana;
	}

	public String getOpisTipaRestorana() {
		return this.opisTipaRestorana;
	}

	public void setOpisTipaRestorana(String opisTipaRestorana) {
		this.opisTipaRestorana = opisTipaRestorana;
	}

	public List<Je_tipa> getJeTipas() {
		return this.jeTipas;
	}

	public void setJeTipas(List<Je_tipa> jeTipas) {
		this.jeTipas = jeTipas;
	}

	public Je_tipa addJeTipa(Je_tipa jeTipa) {
		getJeTipas().add(jeTipa);
		jeTipa.setTipRestorana(this);

		return jeTipa;
	}

	public Je_tipa removeJeTipa(Je_tipa jeTipa) {
		getJeTipas().remove(jeTipa);
		jeTipa.setTipRestorana(null);

		return jeTipa;
	}

}