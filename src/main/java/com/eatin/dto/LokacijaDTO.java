package com.eatin.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LokacijaDTO {

	private int idLokacije;
	private String broj;
	private String grad;
	private BigDecimal latitude;
	private BigDecimal longitude;
	@Size(max = 5)
	private String postanskiBroj;
	private String ulica;
}
