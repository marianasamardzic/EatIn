package com.eatin.dto.porudzbina;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.eatin.dto.LokacijaDTO;

import lombok.Data;

@Data
public class CreatePorudzbinaDTO {

	// osnovne informacije
	@NotEmpty(message = "Polje ukupnaCena ne sme biti prazno")
	@NotNull(message = "Polje ukupnaCena je obavezeno")
	private BigDecimal ukupnaCena;
	private LokacijaDTO lokacija;
	private int restoranId;
//	//lista stavki
	private List<StavkaPorudzbineDTO> stavkePorudzbine;
}
