package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Je_tipa database table.
 * 
 */
@Entity
@NamedQuery(name="Je_tipa.findAll", query="SELECT j FROM Je_tipa j")
public class Je_tipa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="JE_TIPA_IDJETIPA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JE_TIPA_IDJETIPA_GENERATOR")
	@Column(name="id_je_tipa")
	private int idJeTipa;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

	//bi-directional many-to-one association to Tip_restorana
	@ManyToOne
	@JoinColumn(name="id_tipa_restorana")
	private Tip_restorana tipRestorana;

	public Je_tipa() {
	}

	public int getIdJeTipa() {
		return this.idJeTipa;
	}

	public void setIdJeTipa(int idJeTipa) {
		this.idJeTipa = idJeTipa;
	}

	public Restoran getRestoran() {
		return this.restoran;
	}

	public void setRestoran(Restoran restoran) {
		this.restoran = restoran;
	}

	public Tip_restorana getTipRestorana() {
		return this.tipRestorana;
	}

	public void setTipRestorana(Tip_restorana tipRestorana) {
		this.tipRestorana = tipRestorana;
	}

}