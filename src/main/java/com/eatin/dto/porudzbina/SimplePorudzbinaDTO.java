package com.eatin.dto.porudzbina;

import java.math.BigDecimal;
import java.util.List;

import com.eatin.dto.LokacijaDTO;

import lombok.Data;

@Data
public class SimplePorudzbinaDTO {

	private int idPorudzbine;
	private String vremePrijemaPorudzbine;
	private String vremeIsporukePorudzbine;
	private String statusPorudzbine;
	private BigDecimal ukupnaCena;
	private int restoranId;
	private LokacijaDTO lokacija;
	private List<StavkaPorudzbineDTO> stavkePorudzbine;
}
