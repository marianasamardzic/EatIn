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
 * The persistent class for the Je_tipa database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Je_tipa.findAll", query="SELECT j FROM Je_tipa j")
public class Je_tipa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "JE_TIPA_IDJETIPA_GENERATOR", sequenceName = "Je_tipa_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JE_TIPA_IDJETIPA_GENERATOR")
	@Column(name="id_je_tipa")
	private int idJeTipa;

	//bi-directional many-to-one association to Restoran
	@ManyToOne
	@JoinColumn(name="id_restorana")
	private Restoran restoran;

	//bi-directional many-to-one association to Tip_restorana
	@ManyToOne
	@JoinColumn(name="id_tipa_restorana")
	private Tip_restorana tipRestorana;

}