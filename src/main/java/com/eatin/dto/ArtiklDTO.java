package com.eatin.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ArtiklDTO {

	private int idArtikla;
	private BigDecimal cenaArtikla;
	private String nazivArtikla;
	private int tipArtiklaId;
	private String slikaArtikla;
	private int restoranId;

}
