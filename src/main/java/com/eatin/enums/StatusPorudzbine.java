package com.eatin.enums;

public enum StatusPorudzbine {
	PRIMLJENA("Primljena"), GOTOVA("Gotova"), PRIHVACENA("Preuzeta"), ISPORUCENA("Dostavljena");

	public final String label;

	private StatusPorudzbine(String label) {
		this.label = label;
	}
}
