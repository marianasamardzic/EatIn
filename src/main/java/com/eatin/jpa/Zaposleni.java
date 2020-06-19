package com.eatin.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;


/**
 * The persistent class for the Zaposleni database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Zaposleni.findAll", query="SELECT z FROM Zaposleni z")
public class Zaposleni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ZAPOSLENI_IDZAPOSLENOG_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ZAPOSLENI_IDZAPOSLENOG_GENERATOR")
	@Column(name="id_zaposlenog")
	private int idZaposlenog;

	@Column(name="funkcija_zaposlenog")
	private String funkcijaZaposlenog;

	//bi-directional one-to-one association to Korisnik
	@OneToOne
	@JoinColumn(name="id_zaposlenog")
	private Korisnik korisnik;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;


}