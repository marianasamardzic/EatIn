package com.eatin.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CreatePorudzbinaDTO {

	private String statusPorudzbine;
	private BigDecimal ukupnaCena;
	private String vremeIsporukePorudzbine;
	private String vremePrijemaPorudzbine;
	private LokacijaDTO lokacija;
//	//lista stavki
	private List<SadrziDTO> sadrzis;
}
