package com.eatin.dto.korisnik;

import com.eatin.dto.restoran.RestoranDTO;
import com.eatin.jpa.Restoran;

import lombok.Data;

@Data
public class ZaposleniDTO {
	
	private int idZaposlenog;
	private String funkcijaZaposlenog;
	private KorisnikDTO korisnik;
	private int restoranId;
}
