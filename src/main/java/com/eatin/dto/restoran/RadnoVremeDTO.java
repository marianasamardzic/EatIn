package com.eatin.dto.restoran;

import com.eatin.dto.restoran.TipDatumaDTO;

import lombok.Data;

@Data
public class RadnoVremeDTO {

	private TipDatumaDTO tipDatuma;
	private String vremeOd;
	private String vremeDo;
}
