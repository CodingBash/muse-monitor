package com.codingbash.musemonitor.socketserver.processor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;

import jwave.Transform;
import jwave.transforms.WaveletPacketTransform;
import jwave.transforms.wavelets.haar.Haar1;

public class SeizureDeterminationProcessor {

	private final static Logger LOG = LoggerFactory.getLogger(SeizureDeterminationProcessor.class);

	@Autowired
	private Queue<InboundPayload> dataQueue;

	public boolean determineSeizure(InboundPayload inboundPayload) {
		boolean seizureFlag = inboundPayload.getSeizureFlag();

		double[] eegData = retrieveEeg1ValuesInArray(dataQueue);

		/*
		 * Seizure Analysis
		 */
		/*
		 * TODO: Step 1: Utilize JWave for wavelet transformation
		 * 
		 * Documentation: https://github.com/cscheiblich/JWave/wiki/HowTo
		 * 
		 * TODO: Determine which wavelet is used in EEG wavelet transform
		 */
		Transform t = new Transform(new WaveletPacketTransform(new Haar1()));

		double[] arrHilb = t.forward(eegData); // 1-D WPT Haar forward

		double[] arrReco = t.reverse(arrHilb); // 1-D WPT Haar reverse

		// TODO: Step 2: Utilize pca_transform for dimensionality reduction

		// TODO: Step 3" Utilize JAMA for custom implementation of classifier
		// assignment

		return seizureFlag;

	}

	/*
	 * This method is really inefficient - look at ways to speed it up
	 */
	private double[] retrieveEeg1ValuesInArray(Queue<InboundPayload> dataQueueIn) {
		List<InboundPayload> list = new ArrayList<InboundPayload>(dataQueueIn);
		List<Double> eeg1List = new LinkedList<Double>();
		for (InboundPayload payload : list) {
			eeg1List.add(payload.getEegData().get(0).getEeg1());
		}
		double[] eegData = ArrayUtils.toPrimitive(eeg1List.toArray(new Double[eeg1List.size()]));
		return eegData;
	}
}
