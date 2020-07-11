package com.eatin.dto.korisnik;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class KorisnikWithIdDTO {

	private int idKorisnika;
	private String emailKorisnika;
	private String lozinkaKorisnika;
	private String imeKorisnika;
	private String prezimeKorisnika;
	private String telefonKorisnika;
}
