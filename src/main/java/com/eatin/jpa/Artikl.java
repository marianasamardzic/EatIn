package com.eatin.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Data;


/**
 * The persistent class for the Artikl database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Artikl.findAll", query="SELECT a FROM Artikl a")
public class Artikl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ARTIKL_IDARTIKLA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARTIKL_IDARTIKLA_GENERATOR")
	@Column(name="id_artikla")
	private int idArtikla;

	@Column(name="cena_artikla")
	private BigDecimal cenaArtikla;

	@Column(name="naziv_artikla")
	private String nazivArtikla;

	@Column(name="slika_artikla")
	private String slikaArtikla;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

	//bi-directional many-to-one association to Tip_artikla
	@ManyToOne
	@JoinColumn(name="id_tipa_artikla")
	private Tip_artikla tipArtikla;

	//bi-directional many-to-one association to Moze_biti_mere
	@OneToMany(mappedBy="artikl")
	private List<Moze_biti_mere> mozeBitiMeres;

	//bi-directional many-to-one association to Moze_sadrzati_priloge
	@OneToMany(mappedBy="artikl")
	private List<Moze_sadrzati_priloge> mozeSadrzatiPriloges;

	//bi-directional many-to-one association to Sadrzi
	@OneToMany(mappedBy="artikl")
	private List<StavkaPorudzbine> sadrzis;


}