package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Prilog database table.
 * 
 */
@Entity
@NamedQuery(name="Prilog.findAll", query="SELECT p FROM Prilog p")
public class Prilog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRILOG_IDPRILOGA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRILOG_IDPRILOGA_GENERATOR")
	@Column(name="id_priloga")
	private int idPriloga;

	@Column(name="naziv_priloga")
	private String nazivPriloga;

	//bi-directional many-to-one association to Ima_priloge
	@OneToMany(mappedBy="prilog")
	private List<Ima_priloge> imaPriloges;

	//bi-directional many-to-one association to Moze_sadrzati_priloge
	@OneToMany(mappedBy="prilog")
	private List<Moze_sadrzati_priloge> mozeSadrzatiPriloges;

	public Prilog() {
	}

	public int getIdPriloga() {
		return this.idPriloga;
	}

	public void setIdPriloga(int idPriloga) {
		this.idPriloga = idPriloga;
	}

	public String getNazivPriloga() {
		return this.nazivPriloga;
	}

	public void setNazivPriloga(String nazivPriloga) {
		this.nazivPriloga = nazivPriloga;
	}

	public List<Ima_priloge> getImaPriloges() {
		return this.imaPriloges;
	}

	public void setImaPriloges(List<Ima_priloge> imaPriloges) {
		this.imaPriloges = imaPriloges;
	}

	public Ima_priloge addImaPriloge(Ima_priloge imaPriloge) {
		getImaPriloges().add(imaPriloge);
		imaPriloge.setPrilog(this);

		return imaPriloge;
	}

	public Ima_priloge removeImaPriloge(Ima_priloge imaPriloge) {
		getImaPriloges().remove(imaPriloge);
		imaPriloge.setPrilog(null);

		return imaPriloge;
	}

	public List<Moze_sadrzati_priloge> getMozeSadrzatiPriloges() {
		return this.mozeSadrzatiPriloges;
	}

	public void setMozeSadrzatiPriloges(List<Moze_sadrzati_priloge> mozeSadrzatiPriloges) {
		this.mozeSadrzatiPriloges = mozeSadrzatiPriloges;
	}

	public Moze_sadrzati_priloge addMozeSadrzatiPriloge(Moze_sadrzati_priloge mozeSadrzatiPriloge) {
		getMozeSadrzatiPriloges().add(mozeSadrzatiPriloge);
		mozeSadrzatiPriloge.setPrilog(this);

		return mozeSadrzatiPriloge;
	}

	public Moze_sadrzati_priloge removeMozeSadrzatiPriloge(Moze_sadrzati_priloge mozeSadrzatiPriloge) {
		getMozeSadrzatiPriloges().remove(mozeSadrzatiPriloge);
		mozeSadrzatiPriloge.setPrilog(null);

		return mozeSadrzatiPriloge;
	}

}