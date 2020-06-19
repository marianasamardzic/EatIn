package com.eatin.enums;

public enum SortByArtikl {
	ID("idArtikla"), NAZIV("nazivArtikla"), CENA("cenaArtikla");

	public final String label;

	private SortByArtikl(String label) {
		this.label = label;
	}
}