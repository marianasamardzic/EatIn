package com.eatin.dto.artikl;

import java.math.BigDecimal;
import java.util.List;

import com.eatin.dto.PrilogDTO;
import com.eatin.dto.mera.MeraDTO;

import lombok.Data;

@Data
public class ArtiklNoIdDTO {

	private BigDecimal cenaArtikla;
	private String nazivArtikla;
	private String slikaArtikla;
	private TipArtiklaDTO tipArtikla;
	private List<MeraDTO> mere;
	private List<PrilogDTO> prilozi;
}
