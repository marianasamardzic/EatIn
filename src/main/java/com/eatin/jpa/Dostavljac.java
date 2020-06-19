package com.eatin.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;


/**
 * The persistent class for the Dostavljac database table.
 * 
 */
@Data
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

}