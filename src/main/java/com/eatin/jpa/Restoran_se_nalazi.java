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
 * The persistent class for the Restoran_se_nalazi database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Restoran_se_nalazi.findAll", query="SELECT r FROM Restoran_se_nalazi r")
public class Restoran_se_nalazi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RESTORAN_SE_NALAZI_IDRESTORANSENALAZI_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESTORAN_SE_NALAZI_IDRESTORANSENALAZI_GENERATOR")
	@Column(name="id_restoran_se_nalazi")
	private int idRestoranSeNalazi;

	//bi-directional many-to-one association to Lokacija
	@ManyToOne
	@JoinColumn(name="id_lokacije")
	private Lokacija lokacija;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

}