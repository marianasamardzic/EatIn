package com.eatin.dto.restoran;

import java.util.List;

import com.eatin.dto.LokacijaDTO;

import lombok.Data;

@Data
public class RestoranDTO {

	private int idRestorana;
	private String nazivRestorana;
	private String opisRestorana;
	private int pibRestorana;
	private String slikaRestorana;
	private String telefonRestorana;
	private List<LokacijaDTO> restoranSeNalazi;
	private List<RadnoVremeDTO> radnoVreme;
}