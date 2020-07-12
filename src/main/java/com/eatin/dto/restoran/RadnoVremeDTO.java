package com.eatin.dto.restoran;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class RadnoVremeDTO {

	private TipDatumaDTO tipDatuma;
	@Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Polje vremeOd mora biti u formatu HH:mm")
	private String vremeOd;
	@Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Polje vremeDo mora biti u formatu HH:mm")
	private String vremeDo;
}
