package com.codingbash.musemonitor.socketserver.model;

public class PreviousStatusHolder {

	private PhysicalStatus physicalStatus;

	private MentalStatus mentalStatus;
	
	public PreviousStatusHolder(){
		this.physicalStatus = null;
		this.mentalStatus = null;
	}
	
	public PhysicalStatus getPhysicalStatus() {
		return physicalStatus;
	}

	public void setPhysicalStatus(PhysicalStatus physicalStatus) {
		this.physicalStatus = physicalStatus;
	}

	public MentalStatus getMentalStatus() {
		return mentalStatus;
	}

	public void setMentalStatus(MentalStatus mentalStatus) {
		this.mentalStatus = mentalStatus;
	}

	
}
