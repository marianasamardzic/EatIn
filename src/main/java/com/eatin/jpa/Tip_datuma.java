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
 * The persistent class for the Tip_datuma database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Tip_datuma.findAll", query="SELECT t FROM Tip_datuma t")
public class Tip_datuma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIP_DATUMA_IDTIPADATUMA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIP_DATUMA_IDTIPADATUMA_GENERATOR")
	@Column(name="id_tipa_datuma")
	private int idTipaDatuma;

	@Column(name="opis_tipa_datuma")
	private String opisTipaDatuma;

	//bi-directional many-to-one association to Radi
	@OneToMany(mappedBy="tipDatuma")
	private List<Radi> radis;


}