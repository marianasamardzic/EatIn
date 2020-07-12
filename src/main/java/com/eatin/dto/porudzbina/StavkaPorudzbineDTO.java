package com.eatin.dto.porudzbina;

import java.util.List;

import com.eatin.dto.artikl.ArtiklDTO;
import com.eatin.dto.mera.MeraDTO;

import lombok.Data;

@Data
public class StavkaPorudzbineDTO {

	private int idStavkePorudzbine;
	private ArtiklDTO artikl;

	private MeraDTO mera;
	private List<ImaPrilogeDTO> imaPriloge;

}
