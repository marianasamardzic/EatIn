package com.eatin.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Dostavljac database table.
 * 
 */
@Entity
@NamedQuery(name="Dostavljac.findAll", query="SELECT d FROM Dostavljac d")
public class Dostavljac implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOSTAVLJAC_IDDOSTAVLJACA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOSTAVLJAC_IDDOSTAVLJACA_GENERATOR")
	@Column(name="id_dostavljaca")
	private int idDostavljaca;

	@Column(name="prevozno_sredstvo")
	private String prevoznoSredstvo;

	//bi-directional one-to-one association to Korisnik
	@OneToOne
	@JoinColumn(name="id_dostavljaca")
	private Korisnik korisnik;

	//bi-directional many-to-one association to Porudzbina
	@OneToMany(mappedBy="dostavljac")
	private List<Porudzbina> porudzbinas;

	public Dostavljac() {
	}

	public int getIdDostavljaca() {
		return this.idDostavljaca;
	}

	public void setIdDostavljaca(int idDostavljaca) {
		this.idDostavljaca = idDostavljaca;
	}

	public String getPrevoznoSredstvo() {
		return this.prevoznoSredstvo;
	}

	public void setPrevoznoSredstvo(String prevoznoSredstvo) {
		this.prevoznoSredstvo = prevoznoSredstvo;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public List<Porudzbina> getPorudzbinas() {
		return this.porudzbinas;
	}

	public void setPorudzbinas(List<Porudzbina> porudzbinas) {
		this.porudzbinas = porudzbinas;
	}

	public Porudzbina addPorudzbina(Porudzbina porudzbina) {
		getPorudzbinas().add(porudzbina);
		porudzbina.setDostavljac(this);

		return porudzbina;
	}

	public Porudzbina removePorudzbina(Porudzbina porudzbina) {
		getPorudzbinas().remove(porudzbina);
		porudzbina.setDostavljac(null);

		return porudzbina;
	}

}