package com.eatin.dto.artikl;

import java.math.BigDecimal;
import java.util.List;

import com.eatin.dto.PrilogDTO;
import com.eatin.dto.mera.MeraDTO;

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
