package com.codingbash.musemonitor.socketserver.model;

public class JerkPacket {
	private long timeMillis;

	private double jerkX;
	private double jerkY;
	private double jerkZ;

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

	public double getJerkX() {
		return jerkX;
	}

	public void setJerkX(double jerkX) {
		this.jerkX = jerkX;
	}

	public double getJerkY() {
		return jerkY;
	}

	public void setJerkY(double jerkY) {
		this.jerkY = jerkY;
	}

	public double getJerkZ() {
		return jerkZ;
	}

	public void setJerkZ(double jerkZ) {
		this.jerkZ = jerkZ;
	}

}