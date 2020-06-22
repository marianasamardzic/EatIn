package com.eatin.dto.porudzbina;

import java.math.BigDecimal;
import java.util.List;

import com.eatin.dto.LokacijaDTO;

import lombok.Data;

@Data
public class CreatePorudzbinaDTO {

	// osnovne informacije
	private BigDecimal ukupnaCena;
	private LokacijaDTO lokacija;
//	//lista stavki
	private List<StavkaPorudzbineDTO> stavkePorudzbine;
}
