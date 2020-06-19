package com.eatin.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Data;


/**
 * The persistent class for the Porudzbina database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Porudzbina.findAll", query="SELECT p FROM Porudzbina p")
public class Porudzbina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PORUDZBINA_IDPORUDZBINE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PORUDZBINA_IDPORUDZBINE_GENERATOR")
	@Column(name="id_porudzbine")
	private int idPorudzbine;

	@Column(name="status_porudzbine")
	private String statusPorudzbine;

	@Column(name="ukupna_cena")
	private BigDecimal ukupnaCena;

	@Column(name="vreme_isporuke_porudzbine")
	private String vremeIsporukePorudzbine;

	@Column(name="vreme_prijema_porudzbine")
	private String vremePrijemaPorudzbine;

	//bi-directional many-to-one association to Dostavljac
	@ManyToOne
	@JoinColumn(name="id_dostavljaca")
	private Dostavljac dostavljac;

	//bi-directional many-to-one association to Klijent
	@ManyToOne
	@JoinColumn(name="id_klijenta")
	private Klijent klijent;

	//bi-directional many-to-one association to Lokacija
	@ManyToOne
	@JoinColumn(name="id_lokacije")
	private Lokacija lokacija;

	//bi-directional many-to-one association to Sadrzi
	@OneToMany(mappedBy="porudzbina")
	private List<StavkaPorudzbine> stavkePorudzbine;


}