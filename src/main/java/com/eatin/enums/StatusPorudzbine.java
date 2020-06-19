package com.eatin.enums;

public enum StatusPorudzbine {
	PRIMLJENA("primljena"), GOTOVA("gotova"), PRIHVACENA("prihvacena"), ISPORUCENA("isporucena");

	public final String label;

	private StatusPorudzbine(String label) {
		this.label = label;
	}
}
