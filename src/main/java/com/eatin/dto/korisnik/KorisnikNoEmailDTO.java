package com.eatin.dto.korisnik;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class KorisnikNoEmailDTO {

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
