package com.eatin.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;


/**
 * The persistent class for the Token database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Token.findAll", query="SELECT t FROM Token t")
public class Token implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TOKEN_IDTOKENA_GENERATOR", sequenceName="TOKEN_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TOKEN_IDTOKENA_GENERATOR")
	@Column(name="id_tokena")
	private int idTokena;

	@Column(name="datum_kreiranja")
	private String datumKreiranja;

//	@Column(name="id_korisnika")
//	private int idKorisnika;

	@OneToOne
	@JoinColumn(name = "id_korisnika")
	private Korisnik korisnik;

	private String token;

}