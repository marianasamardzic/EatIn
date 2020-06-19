package com.eatin.dto;

import java.util.List;

import lombok.Data;

@Data
public class RestoranDTO {

	private int idRestorana;
	private String nazivRestorana;
	private String opisRestorana;
	private int pibRestorana;
	private String slikaRestorana;
	private String telefonRestorana;
	private List<LokacijaDTO> restoranSeNalazis;
}
