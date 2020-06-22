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
 * The persistent class for the Prilog database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Prilog.findAll", query="SELECT p FROM Prilog p")
public class Prilog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PRILOG_IDPRILOGA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRILOG_IDPRILOGA_GENERATOR")
	@Column(name="id_priloga")
	private int idPriloga;

	@Column(name="naziv_priloga")
	private String nazivPriloga;

	//bi-directional many-to-one association to Ima_priloge
	@OneToMany(mappedBy="prilog")
	private List<Ima_priloge> imaPriloge;

	//bi-directional many-to-one association to Moze_sadrzati_priloge
	@OneToMany(mappedBy="prilog")
	private List<Moze_sadrzati_priloge> mozeSadrzatiPriloge;


}