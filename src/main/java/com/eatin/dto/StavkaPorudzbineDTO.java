package com.eatin.dto;

import java.util.List;

import lombok.Data;

@Data
public class StavkaPorudzbineDTO {

	private int idStavkePorudzbine;
	private ArtiklDTO artikl;

	private MeraDTO mera;
	private List<PrilogDTO> prilozi;

}
