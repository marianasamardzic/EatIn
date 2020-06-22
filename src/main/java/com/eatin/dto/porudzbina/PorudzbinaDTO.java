package com.eatin.dto.porudzbina;

import java.math.BigDecimal;
import java.util.List;

import com.eatin.dto.LokacijaDTO;
import com.eatin.dto.korisnik.DostavljacDTO;
import com.eatin.dto.korisnik.KlijentDTO;

import lombok.Data;

@Data
public class PorudzbinaDTO {

	// osnovne informacije
	private int idPorudzbine;
	private String statusPorudzbine;
	private BigDecimal ukupnaCena;
	private String vremeIsporukePorudzbine;
	private String vremePrijemaPorudzbine;
//	//objekti
	private DostavljacDTO dostavljac;
	private KlijentDTO klijent;
	private LokacijaDTO lokacija;
//	//lista stavki
	private List<StavkaPorudzbineDTO> stavkePorudzbine;
}
