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
 * The persistent class for the Tip_artikla database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Tip_artikla.findAll", query="SELECT t FROM Tip_artikla t")
public class Tip_artikla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIP_ARTIKLA_IDTIPAARTIKLA_GENERATOR", sequenceName = "Tip_artikla_sequence", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIP_ARTIKLA_IDTIPAARTIKLA_GENERATOR")
	@Column(name="id_tipa_artikla")
	private int idTipaArtikla;

	@Column(name="opis_tipa_artikla")
	private String opisTipaArtikla;

	//bi-directional many-to-one association to Artikl
	@OneToMany(mappedBy="tipArtikla")
	private List<Artikl> artikli;


}