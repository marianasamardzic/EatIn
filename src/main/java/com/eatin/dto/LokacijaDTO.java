package com.eatin.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LokacijaDTO {

	private int idLokacije;
	private String broj;
	private String grad;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String postanskiBroj;
	private String ulica;
}
