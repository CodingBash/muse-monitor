package com.codingbash.musemonitor.socketserver.model;

public class GyroscopePacket {
	private long timeMills;

	private double gyroX;
	private double gyroY;
	private double gyroZ;

	public double getGyroX() {
		return gyroX;
	}

	public void setGyroX(double gyroX) {
		this.gyroX = gyroX;
	}

	public double getGyroY() {
		return gyroY;
	}

	public void setGyroY(double gyroY) {
		this.gyroY = gyroY;
	}

	public double getGyroZ() {
		return gyroZ;
	}

	public void setGyroZ(double gyroZ) {
		this.gyroZ = gyroZ;
	}

	public long getTimeMills() {
		return timeMills;
	}

	public void setTimeMills(long timeMills) {
		this.timeMills = timeMills;
	}

}
