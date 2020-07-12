package com.eatin.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LokacijaNoIdDTO {

	@NotNull(message = "Polje broj je obavezno")
	@NotEmpty(message = "Polje broj ne sme biti prazno")
	private String broj;
	@NotNull(message = "Polje grad je obavezno")
	@NotEmpty(message = "Polje grad ne sme biti prazno")
	private String grad;
	private BigDecimal latitude;
	private BigDecimal longitude;
	@Size(max = 5, min = 5, message = "Polje postanskiBroj mora sadrzati 5 karaktera")
	@NotNull(message = "Polje postanskiBroj je obavezno")
	@NotEmpty(message = "Polje postanskiBroj ne sme biti prazno")
	private String postanskiBroj;
	@NotNull(message = "Polje ulica je obavezno")
	@NotEmpty(message = "Polje ulica ne sme biti prazno")
	private String ulica;
}
