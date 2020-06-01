package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Moze_sadrzati_priloge database table.
 * 
 */
@Entity
@NamedQuery(name="Moze_sadrzati_priloge.findAll", query="SELECT m FROM Moze_sadrzati_priloge m")
public class Moze_sadrzati_priloge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MOZE_SADRZATI_PRILOGE_IDMOZESADRZATIPRILOGE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MOZE_SADRZATI_PRILOGE_IDMOZESADRZATIPRILOGE_GENERATOR")
	@Column(name="id_moze_sadrzati_priloge")
	private int idMozeSadrzatiPriloge;

	//bi-directional many-to-one association to Artikl
	@ManyToOne
	@JoinColumn(name="id_artikla")
	private Artikl artikl;

	//bi-directional many-to-one association to Prilog
	@ManyToOne
	@JoinColumn(name="id_priloga")
	private Prilog prilog;

	public Moze_sadrzati_priloge() {
	}

	public int getIdMozeSadrzatiPriloge() {
		return this.idMozeSadrzatiPriloge;
	}

	public void setIdMozeSadrzatiPriloge(int idMozeSadrzatiPriloge) {
		this.idMozeSadrzatiPriloge = idMozeSadrzatiPriloge;
	}

	public Artikl getArtikl() {
		return this.artikl;
	}

	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}

	public Prilog getPrilog() {
		return this.prilog;
	}

	public void setPrilog(Prilog prilog) {
		this.prilog = prilog;
	}

}