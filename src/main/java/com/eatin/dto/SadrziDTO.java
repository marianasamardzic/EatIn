package com.eatin.dto;

import java.util.List;

import lombok.Data;

@Data
public class SadrziDTO {

	private int idSadrzi;
	private ArtiklDTO artikl;

	private MeraDTO mera;
	private List<PrilogDTO> prilozi;

}
