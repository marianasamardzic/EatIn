package com.eatin.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;


/**
 * The persistent class for the Klijent database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Klijent.findAll", query="SELECT k FROM Klijent k")
public class Klijent implements Serializable {
	private static final long serialVersionUID = 1L;

//	@SequenceGenerator(name = "KORISNIK_IDKORISNIKA_GENERATOR", sequenceName = "Korisnik_sequence", allocationSize = 1)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KORISNIK_IDKORISNIKA_GENERATOR")
	@Id
	@Column(name="id_klijenta")
	private int idKlijenta;

	//bi-directional one-to-one association to Korisnik
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="id_klijenta")
	private Korisnik korisnik;

	//bi-directional many-to-one association to Klijent_se_nalazi
	@OneToMany(mappedBy="klijent")
	private List<Klijent_se_nalazi> klijentSeNalazis;

	//bi-directional many-to-one association to Porudzbina
	@OneToMany(mappedBy="klijent")
	private List<Porudzbina> porudzbinas;

}