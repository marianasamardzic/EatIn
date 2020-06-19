package com.eatin.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class KorisnikDTO {

	@NotEmpty(message = "Polje email ne sme biti prazno")
	@Email(message = "Polje email mora biti u pravilnom formatu")
	@NotNull(message = "Polje email je obavezno")
	private String emailKorisnika;

	@NotNull(message = "Polje lozinkaKorisnika je obavezno")
	@NotEmpty(message = "Polje lozinkaKorisnika ne sme biti prazno")
	@Size(min = 7, message = "Lozinka mora sadrzati minimum 7 karaktera")
	private String lozinkaKorisnika;

	@NotNull(message = "Polje imeKorisnika je obavezno")
	@NotEmpty(message = "Polje imeKorisnika ne sme biti prazno")
	private String imeKorisnika;

	@NotNull(message = "Polje prezimeKorisnika je obavezno")
	@NotEmpty(message = "Polje prezimeKorisnika ne sme biti prazno")
	private String prezimeKorisnika;

	@NotNull(message = "Polje telefonKorisnika je obavezno")
	@NotEmpty(message = "Polje telefonKorisnika je obavezno")
	private String telefonKorisnika;

}
