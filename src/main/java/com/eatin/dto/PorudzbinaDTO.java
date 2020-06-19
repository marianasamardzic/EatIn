package com.eatin.dto;

import java.math.BigDecimal;
import java.util.List;

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
