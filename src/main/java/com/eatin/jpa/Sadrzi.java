package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Sadrzi database table.
 * 
 */
@Entity
@NamedQuery(name="Sadrzi.findAll", query="SELECT s FROM Sadrzi s")
public class Sadrzi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SADRZI_IDSADRZI_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SADRZI_IDSADRZI_GENERATOR")
	@Column(name="id_sadrzi")
	private int idSadrzi;

	//bi-directional many-to-one association to Ima_priloge
	@OneToMany(mappedBy="sadrzi")
	private List<Ima_priloge> imaPriloges;

	//bi-directional many-to-one association to Artikl
	@ManyToOne
	@JoinColumn(name="id_artikla")
	private Artikl artikl;

	//bi-directional many-to-one association to Mera
	@ManyToOne
	@JoinColumn(name="id_mere")
	private Mera mera;

	//bi-directional many-to-one association to Porudzbina
	@ManyToOne
	@JoinColumn(name="id_porudzbine")
	private Porudzbina porudzbina;

	public Sadrzi() {
	}

	public int getIdSadrzi() {
		return this.idSadrzi;
	}

	public void setIdSadrzi(int idSadrzi) {
		this.idSadrzi = idSadrzi;
	}

	public List<Ima_priloge> getImaPriloges() {
		return this.imaPriloges;
	}

	public void setImaPriloges(List<Ima_priloge> imaPriloges) {
		this.imaPriloges = imaPriloges;
	}

	public Ima_priloge addImaPriloge(Ima_priloge imaPriloge) {
		getImaPriloges().add(imaPriloge);
		imaPriloge.setSadrzi(this);

		return imaPriloge;
	}

	public Ima_priloge removeImaPriloge(Ima_priloge imaPriloge) {
		getImaPriloges().remove(imaPriloge);
		imaPriloge.setSadrzi(null);

		return imaPriloge;
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

	public Porudzbina getPorudzbina() {
		return this.porudzbina;
	}

	public void setPorudzbina(Porudzbina porudzbina) {
		this.porudzbina = porudzbina;
	}

}