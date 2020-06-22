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
 * The persistent class for the Ima_priloge database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Ima_priloge.findAll", query="SELECT i FROM Ima_priloge i")
public class Ima_priloge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "IMA_PRILOGE_IDIMAPRILOGE_GENERATOR", sequenceName = "Ima_priloge_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IMA_PRILOGE_IDIMAPRILOGE_GENERATOR")
	@Column(name="id_ima_priloge")
	private int idImaPriloge;

	//bi-directional many-to-one association to Prilog
	@ManyToOne
	@JoinColumn(name="id_priloga")
	private Prilog prilog;

	//bi-directional many-to-one association to Sadrzi
	@ManyToOne
	@JoinColumn(name="id_sadrzi")
	private StavkaPorudzbine stavkaPorudzbine;

}