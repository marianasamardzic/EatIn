package com.eatin.dto.korisnik;

import com.eatin.dto.restoran.RestoranDTO;
import lombok.Data;

@Data
public class ZaposleniNoIdDTO {
	
	
	private String funkcijaZaposlenog;
	private KorisnikDTO korisnik;
	private int restoranId;

}
