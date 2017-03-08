package com.codingbash.musemonitor.socketserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.codingbash.musemonitor.socketserver.model.Payload;

@Controller
public class WebsocketController {
	@MessageMapping("/payload")
	@SendTo("/topic/payload")
	public Payload payload(Payload payload) throws Exception {
		Thread.sleep(1000);
		return payload;
	}
}
