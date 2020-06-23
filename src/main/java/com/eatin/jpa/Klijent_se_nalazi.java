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
 * The persistent class for the Klijent_se_nalazi database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Klijent_se_nalazi.findAll", query="SELECT k FROM Klijent_se_nalazi k")
public class Klijent_se_nalazi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "KLIJENT_SE_NALAZI_IDKLIJENTSENALAZI_GENERATOR", sequenceName = "Klijent_se_nalazi_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="KLIJENT_SE_NALAZI_IDKLIJENTSENALAZI_GENERATOR")
	@Column(name="id_klijent_se_nalazi")
	private int idKlijentSeNalazi;

	@Column(name="aktuelna_adresa")
	private boolean aktuelnaAdresa;

	//bi-directional many-to-one association to Klijent
	@ManyToOne
	@JoinColumn(name="id_klijenta")
	private Klijent klijent;

	//bi-directional many-to-one association to Lokacija
	@ManyToOne
	@JoinColumn(name="id_lokacije")
	private Lokacija lokacija;

}