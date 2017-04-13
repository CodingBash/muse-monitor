package com.codingbash.musemonitor.socketserver.model;

public enum MentalStatus {
	NORMAL("NORMAL"), INTERICTAL("INTERICTAL"), ICTAL("ICTAL");

	private String value;

	private MentalStatus(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
