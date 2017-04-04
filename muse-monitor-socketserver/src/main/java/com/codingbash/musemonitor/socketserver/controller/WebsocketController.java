package com.codingbash.musemonitor.socketserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;
import com.codingbash.musemonitor.socketserver.model.IndicatorWrapper;
import com.codingbash.musemonitor.socketserver.model.MentalStatus;
import com.codingbash.musemonitor.socketserver.model.OutboundPayload;
import com.codingbash.musemonitor.socketserver.model.PhysicalStatus;
import com.codingbash.musemonitor.socketserver.processor.AccellerationProcessor;
import com.codingbash.musemonitor.socketserver.processor.GyroscopeProcessor;
import com.codingbash.musemonitor.socketserver.processor.QueueProcessor;
import com.google.gson.Gson;

@Controller
public class WebsocketController {
	
	private static Logger logger = LoggerFactory.getLogger(WebsocketController.class);
	
	@Autowired
	private IndicatorWrapper indicators;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private QueueProcessor queueProcessor;
	
	@Autowired
	private AccellerationProcessor accellerationProcessor;
	
	@Autowired
	private GyroscopeProcessor gyroscopeProcessor;
	
	private static final double accelUFT = 0.0;
	private static final double accelLFT = 0.0;
	private static final double gyroUFT = 0.0;
	
	// TODO: Modularize into methods
	@MessageMapping("/muse-payload")
	@SendTo("/topic/muse-indicator")
	public OutboundPayload payload(InboundPayload inboundPayload) throws Exception {
		logger.info(gson.toJson(inboundPayload));
	
		indicators.refresh(inboundPayload.getTimeMills());
		if(indicators.getIndicatorOne()){
			if(indicators.getIndicatorTwo()){
				if(indicators.getIndicatorThree()){
					// Output true
				} else {
					/*
					 * Check indicatorThree
					 */
					double gyro = gyroscopeProcessor.retrieveOrientation(inboundPayload.getGyroscopeData().get(0));
					if(gyro > gyroUFT){
						indicators.setIndicatorThree(true);
						// Output true
					}
					
				}
			} else {
				/*
				 * Check indicatorTwo AND three
				 */
				double acc = accellerationProcessor.retrieveAcceleration(inboundPayload.getAccelerometerData().get(0));
				if( acc > accelUFT){
					indicators.setIndicatorTwo(true);
					double gyro = gyroscopeProcessor.retrieveOrientation(inboundPayload.getGyroscopeData().get(0));
					if(gyro > gyroUFT){
						indicators.setIndicatorThree(true);
						// Output true
					}
				}
				
				
			}
		} else {
			/*
			 * Check indicatorOne
			 */
			double acc = accellerationProcessor.retrieveAcceleration(inboundPayload.getAccelerometerData().get(0));
			if(acc < accelLFT){
				indicators.setIndicatorOne(true);
				indicators.setInitialTime(inboundPayload.getTimeMills());
			}
			
		}
		queueProcessor.addItem(inboundPayload);
		
		/*
		 * TODO: Change from list to singleton
		 */
		accellerationProcessor.retrieveAcceleration(inboundPayload.getAccelerometerData().get(0));
		gyroscopeProcessor.retrieveOrientation(inboundPayload.getGyroscopeData().get(0));
		
		OutboundPayload outboundPayload = new OutboundPayload();
		outboundPayload.setPatientId(inboundPayload.getPatientId());
		outboundPayload.setMentalStatus(MentalStatus.GOOD);
		outboundPayload.setPhysicalStatus(PhysicalStatus.GOOD);
		return outboundPayload;
	}
}
