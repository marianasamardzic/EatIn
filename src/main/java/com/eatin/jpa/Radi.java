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
 * The persistent class for the Radi database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Radi.findAll", query="SELECT r FROM Radi r")
public class Radi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RADI_IDRADI_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RADI_IDRADI_GENERATOR")
	@Column(name="id_radi")
	private int idRadi;

	@Column(name="vreme_do")
	private String vremeDo;

	@Column(name="vreme_od")
	private String vremeOd;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

	//bi-directional many-to-one association to Tip_datuma
	@ManyToOne
	@JoinColumn(name="id_tipa_datuma")
	private Tip_datuma tipDatuma;

}