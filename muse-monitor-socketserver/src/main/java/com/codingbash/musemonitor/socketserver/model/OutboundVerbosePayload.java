package com.codingbash.musemonitor.socketserver.model;

import java.util.List;

public class OutboundVerbosePayload {
	private String patientId;
	private long timeMillis;
	private List<EegPacket> eegData;
	private List<AccelerationPacket> accelerometerData;
	private List<GyroscopePacket> gyroscopeData;
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

	public List<EegPacket> getEegData() {
		return eegData;
	}

	public void setEegData(List<EegPacket> eegData) {
		this.eegData = eegData;
	}

	public List<AccelerationPacket> getAccelerometerData() {
		return accelerometerData;
	}

	public void setAccelerometerData(List<AccelerationPacket> accelerometerData) {
		this.accelerometerData = accelerometerData;
	}

	public List<GyroscopePacket> getGyroscopeData() {
		return gyroscopeData;
	}

	public void setGyroscopeData(List<GyroscopePacket> gyroscopeData) {
		this.gyroscopeData = gyroscopeData;
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
