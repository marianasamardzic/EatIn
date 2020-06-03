package com.eatin.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
	@Min(value = 1, message = "UlogaId moze biti 1, 2 ili 3")
	@Max(value = 3, message = "UlogaId moze biti 1, 2 ili 3")
	private int ulogaId;

}
