package com.eatin.enums;

public enum StatusPorudzbine {
	PRIMLJENA("primljena"), GOTOVA("gotova"), PRIHVACENA("preuzeta"), ISPORUCENA("dostavljena");

	public final String label;

	private StatusPorudzbine(String label) {
		this.label = label;
	}
}
