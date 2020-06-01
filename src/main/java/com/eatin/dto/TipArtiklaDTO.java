package com.eatin.dto;

import java.util.List;

import lombok.Data;

@Data
public class TipArtiklaDTO {

	private int idTipaArtikla;
	private String opisTipaArtikla;
	private List<ArtiklDTO> artikls;

}
