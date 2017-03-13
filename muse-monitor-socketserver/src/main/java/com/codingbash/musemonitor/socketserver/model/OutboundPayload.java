package com.codingbash.musemonitor.socketserver.model;

public class OutboundPayload {
	private String patientId;
	private MentalStatus mentalStatus;
	private PhysicalStatus physicalStatus;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
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
