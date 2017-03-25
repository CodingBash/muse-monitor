package com.codingbash.muse_monitor_android.model;

public class EegPacket {
	private long timeMillis;
	
	private double eeg1;
	private double eeg2;
	private double eeg3;
	private double eeg4;
	private double auxLeft;
	private double auxRight;

	public double getTimeMillis(){
		return timeMillis;
	}
	
	public void setTimeMills(long timeMillis){
		this.timeMillis = timeMillis;
	}
	
	public double getEeg1() {
		return eeg1;
	}

	public void setEeg1(double eeg1) {
		this.eeg1 = eeg1;
	}

	public double getEeg2() {
		return eeg2;
	}

	public void setEeg2(double eeg2) {
		this.eeg2 = eeg2;
	}

	public double getEeg3() {
		return eeg3;
	}

	public void setEeg3(double eeg3) {
		this.eeg3 = eeg3;
	}

	public double getEeg4() {
		return eeg4;
	}

	public void setEeg4(double eeg4) {
		this.eeg4 = eeg4;
	}

	public double getAuxLeft() {
		return auxLeft;
	}

	public void setAuxLeft(double auxLeft) {
		this.auxLeft = auxLeft;
	}

	public double getAuxRight() {
		return auxRight;
	}

	public void setAuxRight(double auxRight) {
		this.auxRight = auxRight;
	}

}
