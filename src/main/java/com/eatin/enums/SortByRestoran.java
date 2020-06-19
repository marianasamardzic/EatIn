package com.eatin.enums;

public enum SortByRestoran {
	ID("idRestorana"), NAZIV("nazivRestorana");

	public final String label;

	private SortByRestoran(String label) {
		this.label = label;
	}

}
