package com.codingbash.musemonitor.socketserver.model;

public class OutboundIndicatorPayload {
	private String patientId;
	private long timeMillis;
	private MentalStatus mentalStatus;
	private PhysicalStatus physicalStatus;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

	public MentalStatus getMentalStatus() {
		return mentalStatus;
	}

	public void setMentalStatus(MentalStatus mentalStatus) {
		this.mentalStatus = mentalStatus;
	}

	public PhysicalStatus getPhysicalStatus() {
		return physicalStatus;
	}

	public void setPhysicalStatus(PhysicalStatus physicalStatus) {
		this.physicalStatus = physicalStatus;
	}

}
