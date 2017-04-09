package com.codingbash.musemonitor.socketserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;
import com.codingbash.musemonitor.socketserver.model.IndicatorWrapper;
import com.codingbash.musemonitor.socketserver.model.MentalStatus;
import com.codingbash.musemonitor.socketserver.model.OutboundIndicatorPayload;
import com.codingbash.musemonitor.socketserver.model.OutboundVerbosePayload;
import com.codingbash.musemonitor.socketserver.model.PhysicalStatus;
import com.codingbash.musemonitor.socketserver.model.PreviousStatusHolder;
import com.codingbash.musemonitor.socketserver.processor.AccellerationProcessor;
import com.codingbash.musemonitor.socketserver.processor.GyroscopeProcessor;
import com.google.gson.Gson;

@Controller
public class WebsocketController {

	private final static Logger LOG = LoggerFactory.getLogger(WebsocketController.class);

	@Autowired
	private IndicatorWrapper indicators;

	@Autowired
	private Gson gson;

	@Autowired
	private AccellerationProcessor accellerationProcessor;

	@Autowired
	private GyroscopeProcessor gyroscopeProcessor;

	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private PreviousStatusHolder previousStatus;
	
	private static final double accelUFT = 1.60;
	private static final double accelLFT = 1.00;
	private static final double gyroUFT = 220.0;

	private static final String TOPIC_VERBOSE ="/topic/muse-verbose";
	private static final String TOPIC_INDICATOR = "/topic/muse-indicator";
	
	// TODO: Only send output out if a new status occurred! (find a way to conditionally send output in spring WS)
	// TODO: Modularize into methods
	@MessageMapping("/muse-payload")
	public void payload(InboundPayload inboundPayload) throws Exception {
		LOG.info(gson.toJson(inboundPayload));

		boolean fallFlag = inboundPayload.getFallFlag();
		boolean seizureFlag = inboundPayload.getSeizureFlag();
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

		/*
		 * Set indicator outbound properties
		 */
		OutboundIndicatorPayload outboundIndicatorPayload = new OutboundIndicatorPayload();
		outboundIndicatorPayload.setPatientId(inboundPayload.getPatientId());
		boolean newStatus = false;
		if (fallFlag) {
			outboundIndicatorPayload.setPhysicalStatus(PhysicalStatus.EMERGENCY);
			if(previousStatus.getPhysicalStatus() != PhysicalStatus.EMERGENCY){
				// New status
				newStatus = true;
				previousStatus.setPhysicalStatus(PhysicalStatus.EMERGENCY);
			}
		} else {
			outboundIndicatorPayload.setPhysicalStatus(PhysicalStatus.GOOD);
			if(previousStatus.getPhysicalStatus() != PhysicalStatus.GOOD){
				// New status
				newStatus = true;
				previousStatus.setPhysicalStatus(PhysicalStatus.GOOD);
			}
		}
		
		if (seizureFlag) {
			outboundIndicatorPayload.setMentalStatus(MentalStatus.EMERGENCY);
			if(previousStatus.getMentalStatus() != MentalStatus.EMERGENCY){
				// New status
				newStatus = true;
				previousStatus.setMentalStatus(MentalStatus.EMERGENCY);
			}
		} else {
			outboundIndicatorPayload.setMentalStatus(MentalStatus.GOOD);
			if(previousStatus.getMentalStatus() != MentalStatus.GOOD){
				// New status
				newStatus = true;
				previousStatus.setMentalStatus(MentalStatus.GOOD);
			}
		}
		
		if(newStatus){
			outboundIndicatorPayload.setTimeMillis(inboundPayload.getTimeMills());
			LOG.info("SENDING INDICATOR PAYLOAD: " + gson.toJson(outboundIndicatorPayload));
			template.convertAndSend(TOPIC_INDICATOR, outboundIndicatorPayload);
		}

		/*
		 * Set verbose outbound properties
		 */
		OutboundVerbosePayload outboundVerbosePayload = new OutboundVerbosePayload();
		outboundVerbosePayload.setPatientId(inboundPayload.getPatientId());
		outboundVerbosePayload.setTimeMillis(inboundPayload.getTimeMills());
		outboundVerbosePayload.setEegData(inboundPayload.getEegData());
		outboundVerbosePayload.setAccelerometerData(inboundPayload.getAccelerometerData());
		outboundVerbosePayload.setGyroscopeData(inboundPayload.getGyroscopeData());
		outboundVerbosePayload.setMentalStatus(outboundIndicatorPayload.getMentalStatus());
		outboundVerbosePayload.setPhysicalStatus(outboundIndicatorPayload.getPhysicalStatus());
		
		template.convertAndSend(TOPIC_VERBOSE, outboundVerbosePayload);
	}
}
