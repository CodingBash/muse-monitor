package com.codingbash.musemonitor.socketserver.model;

public class GyroscopePacket {
	private long timeMillis;

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

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

}
