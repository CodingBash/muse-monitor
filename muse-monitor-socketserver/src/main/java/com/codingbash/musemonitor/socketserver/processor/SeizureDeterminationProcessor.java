package com.codingbash.musemonitor.socketserver.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;

import jwave.Transform;
import jwave.transforms.WaveletPacketTransform;
import jwave.transforms.wavelets.daubechies.Daubechies4;

@Component
public class SeizureDeterminationProcessor {

	private final static Logger LOG = LoggerFactory.getLogger(SeizureDeterminationProcessor.class);

	@Autowired
	@Qualifier("dataQueue")
	private Queue<InboundPayload> dataQueue;

	public boolean determineSeizure(InboundPayload inboundPayload) {
		boolean seizureFlag = inboundPayload.getSeizureFlag();

		if (!seizureFlag) {
			double[] eegData = retrieveEeg1ValuesInArray(dataQueue);

			// TODO: Catch exception case
			for (int i = 0; i < 20; i++) {
				if (eegData.length < Math.pow(2, i)) {
					eegData = Arrays.copyOfRange(eegData, 0, (int) Math.pow(2, i - 1));
					break;
				}
			}

			Transform t = new Transform(new WaveletPacketTransform(new Daubechies4()));

			double[][] decomposition = t.decompose(eegData);

			int coefficients = decomposition[0].length;
			double sSum = 0.;
			for (int k = 0; k < coefficients; k++) {
				sSum += Math.log(Math.pow(decomposition[3][k], 2));

			}
			if (sSum > 5000) {
				seizureFlag = true;
			}
			LOG.info("sSum= " + sSum);
		}

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
