package com.eatin.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Data;


/**
 * The persistent class for the Lokacija database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Lokacija.findAll", query="SELECT l FROM Lokacija l")
public class Lokacija implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOKACIJA_IDLOKACIJE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOKACIJA_IDLOKACIJE_GENERATOR")
	@Column(name="id_lokacije")
	private int idLokacije;

	private String broj;

	private String grad;

	private BigDecimal latitude;

	private BigDecimal longitude;

	@Column(name="postanski_broj")
	private String postanskiBroj;

	private String ulica;

	//bi-directional many-to-one association to Klijent_se_nalazi
	@OneToMany(mappedBy="lokacija")
	private List<Klijent_se_nalazi> klijentSeNalazis;

	//bi-directional many-to-one association to Porudzbina
	@OneToMany(mappedBy="lokacija")
	private List<Porudzbina> porudzbinas;

	//bi-directional many-to-one association to Restoran_se_nalazi
	@OneToMany(mappedBy="lokacija")
	private List<Restoran_se_nalazi> restoranSeNalazis;

}