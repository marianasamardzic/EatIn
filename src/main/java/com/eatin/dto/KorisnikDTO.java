package com.eatin.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class KorisnikDTO {

	@NotEmpty(message = "Polje email je obavezno")
	@Email(message = "Polje email mora biti u pravilnom formatu")
	private String emailKorisnika;
	@NotEmpty(message = "Polje imeKorisnika je obavezno")
	private String imeKorisnika;
	@NotEmpty(message = "Polje lozinkaKorisnika je obavezno")
	@Size(min = 7, message = "Lozinka mora sadrzati minimum 7 karaktera")
	private String lozinkaKorisnika;
	@NotEmpty(message = "Polje prezimeKorisnika je obavezno")
	private String prezimeKorisnika;
	@NotEmpty(message = "Polje telefonKorisnika je obavezno")
	private String telefonKorisnika;

}
