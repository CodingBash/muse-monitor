package com.codingbash.musemonitor.socketserver.model;

public enum MentalStatus {
	GOOD("GOOD"), CAUTION("CAUTION"), EMERGENCY("EMERGENCY");

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
