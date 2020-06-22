package com.eatin.jpa;

import java.io.Serializable;
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
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the Sadrzi database table.
 * 
 */
@Data
@Entity
@Table(name = "Sadrzi")
@NamedQuery(name = "Sadrzi.findAll", query = "SELECT s FROM StavkaPorudzbine s")
public class StavkaPorudzbine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SADRZI_IDSADRZI_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SADRZI_IDSADRZI_GENERATOR")
	@Column(name="id_sadrzi")
	private int idStavkePorudzbine;

	//bi-directional many-to-one association to Ima_priloge
	@OneToMany(mappedBy = "stavkaPorudzbine")
	private List<Ima_priloge> imaPriloge;

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


}