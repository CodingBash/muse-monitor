package com.codingbash.musemonitor.socketserver.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;

public class SeizureDeterminationProcessor {

	private final static Logger LOG = LoggerFactory.getLogger(SeizureDeterminationProcessor.class);
	
	public boolean determineSeizure(InboundPayload inboundPayload){
		boolean seizureFlag = inboundPayload.getSeizureFlag();
		
		inboundPayload.getEegData().get(0);
		
		/*
		 * Seizure Analysis
		 */
		// TODO: Step 1: Utilize JWave for wavelet transformation
		
		// TODO: Step 2: Utilize pca_transform for dimensionality reduction
		
		// TODO: Step 3" Utilize JAMA for custom implementation of classifier assignment
		
		return seizureFlag;
		
	}
}
