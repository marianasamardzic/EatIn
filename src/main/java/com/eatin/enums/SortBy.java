package com.eatin.enums;

public enum SortBy {
	ID("idArtikla"), NAZIV("nazivArtikla"), CENA("cenaArtikla");

	public final String label;

	private SortBy(String label) {
		this.label = label;
	}
}