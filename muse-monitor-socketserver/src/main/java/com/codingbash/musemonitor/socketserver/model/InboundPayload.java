package com.codingbash.musemonitor.socketserver.model;

import java.util.List;

public class InboundPayload {
	private String patientId;
	private long timeMills;
	private List<EegPacket> eegData;
	private List<AccelerationPacket> accelerometerData;
	private List<GyroscopePacket> gyroscopeData;
	private Boolean seizureFlag;
	private Boolean fallFlag;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public long getTimeMills() {
		return timeMills;
	}

	public void setTimeMills(long timeMills) {
		this.timeMills = timeMills;
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

	public Boolean getSeizureFlag() {
		return seizureFlag;
	}

	public void setSeizureFlag(Boolean seizureFlag) {
		this.seizureFlag = seizureFlag;
	}

	public Boolean getFallFlag() {
		return fallFlag;
	}

	public void setFallFlag(Boolean fallFlag) {
		this.fallFlag = fallFlag;
	}

}
