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
 * The persistent class for the Mera database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Mera.findAll", query="SELECT m FROM Mera m")
public class Mera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MERA_IDMERE_GENERATOR", sequenceName = "Mera_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MERA_IDMERE_GENERATOR")
	@Column(name="id_mere")
	private int idMere;

	@Column(name="opis_mere")
	private String opisMere;

	//bi-directional many-to-one association to Moze_biti_mere
	@OneToMany(mappedBy="mera")
	private List<Moze_biti_mere> mozeBitiMere;

	//bi-directional many-to-one association to Sadrzi
	@OneToMany(mappedBy="mera")
	private List<StavkaPorudzbine> stavkaPorudzbine;


}