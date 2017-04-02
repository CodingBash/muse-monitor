package com.codingbash.musemonitor.socketserver.processor;

public class AngularAccelerationPacket {
	private long timeMillis;

	private double aaX;
	private double aaY;
	private double aaZ;

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

	public double getAaX() {
		return aaX;
	}

	public void setAaX(double aaX) {
		this.aaX = aaX;
	}

	public double getAaY() {
		return aaY;
	}

	public void setAaY(double aaY) {
		this.aaY = aaY;
	}

	public double getAaZ() {
		return aaZ;
	}

	public void setAaZ(double aaZ) {
		this.aaZ = aaZ;
	}

}
