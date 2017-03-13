package com.codingbash.musemonitor.socketserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;
import com.codingbash.musemonitor.socketserver.model.MentalStatus;
import com.codingbash.musemonitor.socketserver.model.OutboundPayload;
import com.codingbash.musemonitor.socketserver.model.PhysicalStatus;

@Controller
public class WebsocketController {
	
	@MessageMapping("/payload")
	@SendTo("/topic/payload")
	public OutboundPayload payload(InboundPayload inboundPayload) throws Exception {
		Thread.sleep(1000);
		OutboundPayload outboundPayload = new OutboundPayload();
		outboundPayload.setPatientId("00000");
		outboundPayload.setMentalStatus(MentalStatus.GOOD);
		outboundPayload.setPhysicalStatus(PhysicalStatus.GOOD);
		return outboundPayload;
	}
}
