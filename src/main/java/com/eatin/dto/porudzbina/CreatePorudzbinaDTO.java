package com.eatin.dto.porudzbina;

import java.math.BigDecimal;
import java.util.List;

import com.eatin.dto.LokacijaDTO;

import lombok.Data;

@Data
public class CreatePorudzbinaDTO {

	private String statusPorudzbine;
	private BigDecimal ukupnaCena;
	private String vremeIsporukePorudzbine;
	private String vremePrijemaPorudzbine;
	private LokacijaDTO lokacija;
//	//lista stavki
	private List<StavkaPorudzbineDTO> stavkePorudzbine;
}
