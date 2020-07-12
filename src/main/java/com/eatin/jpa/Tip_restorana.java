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
 * The persistent class for the Tip_restorana database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Tip_restorana.findAll", query="SELECT t FROM Tip_restorana t")
public class Tip_restorana implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TIP_RESTORANA_IDTIPARESTORANA_GENERATOR", sequenceName = "Tip_restorana_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIP_RESTORANA_IDTIPARESTORANA_GENERATOR")
	@Column(name="id_tipa_restorana")
	private int idTipaRestorana;

	@Column(name="opis_tipa_restorana")
	private String opisTipaRestorana;

	//bi-directional many-to-one association to Je_tipa
	@OneToMany(mappedBy="tipRestorana")
	private List<Je_tipa> jeTipa;

}