package com.codingbash.musemonitor.socketserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;
import com.codingbash.musemonitor.socketserver.model.MentalStatus;
import com.codingbash.musemonitor.socketserver.model.OutboundPayload;
import com.codingbash.musemonitor.socketserver.model.PhysicalStatus;
import com.google.gson.Gson;

@Controller
public class WebsocketController {
	
	private static Logger logger = LoggerFactory.getLogger(WebsocketController.class);
	
	@MessageMapping("/muse-payload")
	@SendTo("/topic/muse-indicator")
	public OutboundPayload payload(InboundPayload inboundPayload) throws Exception {
		Thread.sleep(1000);
		Gson gson = new Gson();
		logger.info(gson.toJson(inboundPayload));
		OutboundPayload outboundPayload = new OutboundPayload();
		outboundPayload.setPatientId("00000");
		outboundPayload.setMentalStatus(MentalStatus.GOOD);
		outboundPayload.setPhysicalStatus(PhysicalStatus.GOOD);
		return outboundPayload;
	}
}
