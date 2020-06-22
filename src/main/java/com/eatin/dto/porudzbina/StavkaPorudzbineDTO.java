package com.eatin.dto.porudzbina;

import java.util.List;

import com.eatin.dto.MeraDTO;
import com.eatin.dto.PrilogDTO;
import com.eatin.dto.artikl.ArtiklDTO;

import lombok.Data;

@Data
public class StavkaPorudzbineDTO {

	private int idStavkePorudzbine;
	private ArtiklDTO artikl;

	private MeraDTO mera;
	private List<PrilogDTO> prilozi;

}
