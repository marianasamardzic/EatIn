package com.eatin.dto.artikl;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PutArtiklDTO {

	private BigDecimal cenaArtikla;
	private String nazivArtikla;
	private String slikaArtikla;
	private TipArtiklaDTO tipArtikla;

}
