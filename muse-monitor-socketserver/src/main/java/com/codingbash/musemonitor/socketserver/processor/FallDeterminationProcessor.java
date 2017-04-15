package com.codingbash.musemonitor.socketserver.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codingbash.musemonitor.socketserver.model.FallIndicatorWrapper;
import com.codingbash.musemonitor.socketserver.model.InboundPayload;

@Component
public class FallDeterminationProcessor {

	private final static Logger LOG = LoggerFactory.getLogger(FallDeterminationProcessor.class);

	@Autowired
	private FallIndicatorWrapper indicators;

	@Autowired
	private AccellerationProcessor accellerationProcessor;

	@Autowired
	private GyroscopeProcessor gyroscopeProcessor;

	private static final double accelUFT = 1.60;
	private static final double accelLFT = 1.00;
	private static final double gyroUFT = 220.0;

	public boolean determineFall(InboundPayload inboundPayload) {
		boolean fallFlag = inboundPayload.getFallFlag();
		indicators.refresh(inboundPayload.getTimeMills());
		if (indicators.getIndicatorOne()) {
			if (indicators.getIndicatorTwo()) {
				if (indicators.getIndicatorThree()) {
					fallFlag = true;
					LOG.info("FALL DETECTED PREVIOUSLY");
				} else {
					/*
					 * Check indicatorThree
					 */
					double gyro = gyroscopeProcessor.retrieveOrientation(inboundPayload.getGyroscopeData().get(0));
					if (gyro > gyroUFT) {
						indicators.setIndicatorThree(true);
						fallFlag = true;
						LOG.info("INDICATOR THREE TRUE: " + gyro);
					} else {
						LOG.info("INDICATOR THREE FALSE: " + gyro);
					}
				}
			} else {
				/*
				 * Check indicatorTwo AND three
				 */
				double acc = accellerationProcessor.retrieveAcceleration(inboundPayload.getAccelerometerData().get(0));
				if (acc > accelUFT) {
					indicators.setIndicatorTwo(true);
					indicators.setInitialTime(inboundPayload.getTimeMills());
					LOG.info("INDICATOR TWO TRUE: " + acc);
					double gyro = gyroscopeProcessor.retrieveOrientation(inboundPayload.getGyroscopeData().get(0));
					if (gyro > gyroUFT) {
						indicators.setIndicatorThree(true);
						LOG.info("INDICATOR THREE TRUE: " + gyro);
						fallFlag = true;
					} else {
						LOG.info("INDICATOR THREE FALSE: " + gyro);
					}
				} else {
					LOG.info("INDICATOR TWO FALSE: " + acc);
				}

			}
		} else {
			/*
			 * Check indicatorOne
			 */
			double acc = accellerationProcessor.retrieveAcceleration(inboundPayload.getAccelerometerData().get(0));
			if (acc < accelLFT) {
				indicators.setIndicatorOne(true);
				indicators.setInitialTime(inboundPayload.getTimeMills());
				LOG.info("INDICATOR ONE TRUE: " + acc);
			} else {
				LOG.info("INDICATOR ONE FALSE: " + acc);
			}

		}

		return fallFlag;
	}
}
