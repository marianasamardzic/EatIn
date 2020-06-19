package com.eatin.jpa;

import java.io.Serializable;
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
 * The persistent class for the Restoran database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Restoran.findAll", query="SELECT r FROM Restoran r")
public class Restoran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RESTORAN_IDRESTORANA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTORAN_IDRESTORANA_GENERATOR")
	@Column(name="id_restorana")
	private int idRestorana;

	@Column(name="naziv_restorana")
	private String nazivRestorana;

	@Column(name="opis_restorana")
	private String opisRestorana;

	@Column(name="pib_restorana")
	private int pibRestorana;

	@Column(name="slika_restorana")
	private String slikaRestorana;

	@Column(name="telefon_restorana")
	private String telefonRestorana;

	//bi-directional many-to-one association to Artikl
	@OneToMany(mappedBy="restoran")
	private List<Artikl> artikls;

	//bi-directional many-to-one association to Je_tipa
	@OneToMany(mappedBy="restoran")
	private List<Je_tipa> jeTipas;

	//bi-directional many-to-one association to Radi
	@OneToMany(mappedBy="restoran")
	private List<Radi> radis;

	//bi-directional many-to-one association to Restoran_se_nalazi
	@OneToMany(mappedBy="restoran")
	private List<Restoran_se_nalazi> restoranSeNalazis;

	//bi-directional many-to-one association to Zaposleni
	@OneToMany(mappedBy="restoran")
	private List<Zaposleni> zaposlenis;

}