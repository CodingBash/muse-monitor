package com.codingbash.musemonitor.socketserver.model;

/**
 * Wrapper to hold indicator for progression of analysis flow
 * 
 * @author bbece
 *
 */
public class FallIndicatorWrapper {

	/**
	 * TRUE: Acc < LFT in last 0.5s FALSE: contrary
	 */
	private boolean indicatorOne;

	/**
	 * TRUE: indicatorOne true && Acc > UFT in last 0.5s FALSE: contrary
	 */
	private boolean indicatorTwo;

	/**
	 * TRUE: etc
	 */
	private boolean indicatorThree;
	/**
	 * Holds the first timestamp when indicatorOne was fired
	 */
	private long initialTime;

	private long timeInterval;

	public boolean getIndicatorOne() {
		return indicatorOne;
	}

	public void setIndicatorOne(boolean indicatorOne) {
		this.indicatorOne = indicatorOne;
	}

	public boolean getIndicatorTwo() {
		return indicatorTwo && indicatorOne;
	}

	public void setIndicatorTwo(boolean indicatorTwo) {
		this.indicatorTwo = indicatorTwo;
	}

	
	public boolean getIndicatorThree() {
		return indicatorThree;
	}

	public void setIndicatorThree(boolean indicatorThree) {
		this.indicatorThree = indicatorThree;
	}

	public long getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(long initialTime) {
		this.initialTime = initialTime;
	}

	public long getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public void refresh(long timeMillis) {
		if (timeMillis - initialTime > timeInterval) {
			this.initialTime = timeMillis; // Confirm default value
			indicatorOne = false;
			indicatorTwo = false;
		}
	}
}
