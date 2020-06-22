package com.eatin.dto.korisnik;

import lombok.Data;

@Data
public class DostavljacDTO {

	private int idDostavljaca;
	private String prevoznoSredstvo;
	private KorisnikDTO korisnik;
}
