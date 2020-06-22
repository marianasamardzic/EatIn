package com.eatin.dto.artikl;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ArtiklDTO {

	private int idArtikla;
	private BigDecimal cenaArtikla;
	private String nazivArtikla;
	private String slikaArtikla;
	private int restoranId;
	private TipArtiklaDTO tipArtikla;

}
