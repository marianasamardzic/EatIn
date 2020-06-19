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
 * The persistent class for the Moze_sadrzati_priloge database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Moze_sadrzati_priloge.findAll", query="SELECT m FROM Moze_sadrzati_priloge m")
public class Moze_sadrzati_priloge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MOZE_SADRZATI_PRILOGE_IDMOZESADRZATIPRILOGE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MOZE_SADRZATI_PRILOGE_IDMOZESADRZATIPRILOGE_GENERATOR")
	@Column(name="id_moze_sadrzati_priloge")
	private int idMozeSadrzatiPriloge;

	//bi-directional many-to-one association to Artikl
	@ManyToOne
	@JoinColumn(name="id_artikla")
	private Artikl artikl;

	//bi-directional many-to-one association to Prilog
	@ManyToOne
	@JoinColumn(name="id_priloga")
	private Prilog prilog;

}