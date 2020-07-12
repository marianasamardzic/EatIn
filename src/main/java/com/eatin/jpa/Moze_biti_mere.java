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
import javax.persistence.SequenceGenerator;

import lombok.Data;

/**
 * The persistent class for the Moze_biti_mere database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Moze_biti_mere.findAll", query="SELECT m FROM Moze_biti_mere m")
public class Moze_biti_mere implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "MOZE_BITI_MERE_IDMOZEBITIMERE_GENERATOR", sequenceName = "Moze_biti_mere_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MOZE_BITI_MERE_IDMOZEBITIMERE_GENERATOR")
	@Column(name="id_moze_biti_mere")
	private int idMozeBitiMere;

	//bi-directional many-to-one association to Artikl
	@ManyToOne
	@JoinColumn(name="id_artikla")
	private Artikl artikl;

	//bi-directional many-to-one association to Mera
	@ManyToOne
	@JoinColumn(name="id_mere")
	private Mera mera;

}