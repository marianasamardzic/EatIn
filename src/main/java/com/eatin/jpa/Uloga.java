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
 * The persistent class for the Uloga database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Uloga.findAll", query="SELECT u FROM Uloga u")
public class Uloga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ULOGA_IDULOGE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ULOGA_IDULOGE_GENERATOR")
	@Column(name="id_uloge")
	private int idUloge;

	@Column(name="naziv_uloge")
	private String nazivUloge;

	//bi-directional many-to-one association to Korisnik
	@OneToMany(mappedBy="uloga")
	private List<Korisnik> korisniks;

}