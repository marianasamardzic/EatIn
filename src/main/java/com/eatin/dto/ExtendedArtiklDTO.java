package com.eatin.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ExtendedArtiklDTO {

	private int idArtikla;
	private BigDecimal cenaArtikla;
	private String nazivArtikla;
	private int tipArtiklaId;
	private String slikaArtikla;
	private int restoranId;

	private List<MeraDTO> mere;
	private List<PrilogDTO> prilozi;

}
