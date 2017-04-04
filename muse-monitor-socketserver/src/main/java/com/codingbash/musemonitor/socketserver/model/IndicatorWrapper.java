package com.codingbash.musemonitor.socketserver.model;

/**
 * Wrapper to hold indicator for progression of analysis flow
 * 
 * @author bbece
 *
 */
public class IndicatorWrapper {

	/**
	 * TRUE: Acc < LFT in last 0.5s
	 * FALSE: contrary
	 */
	private Boolean indicatorOne;
	
	/**
	 * TRUE: indicatorOne true && Acc > UFT in last 0.5s
	 * FALSE: contrary
	 */
	private Boolean indicatorTwo;

	public Boolean getIndicatorOne() {
		return indicatorOne;
	}

	public void setIndicatorOne(Boolean indicatorOne) {
		this.indicatorOne = indicatorOne;
	}

	public Boolean getIndicatorTwo() {
		return indicatorTwo && indicatorOne;
	}

	public void setIndicatorTwo(Boolean indicatorTwo) {
		this.indicatorTwo = indicatorTwo;
	}
	
	
}
