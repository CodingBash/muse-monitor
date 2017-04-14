package com.codingbash.musemonitor.socketserver.controller;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.codingbash.musemonitor.socketserver.mapper.OutboundIndicatorPayloadMapper;
import com.codingbash.musemonitor.socketserver.mapper.OutboundVerbosePayloadMapper;
import com.codingbash.musemonitor.socketserver.model.InboundPayload;
import com.codingbash.musemonitor.socketserver.model.OutboundIndicatorPayload;
import com.codingbash.musemonitor.socketserver.model.OutboundVerbosePayload;
import com.codingbash.musemonitor.socketserver.processor.FallDeterminationProcessor;
import com.google.gson.Gson;

@Controller
public class WebsocketController {

	private final static Logger LOG = LoggerFactory.getLogger(WebsocketController.class);

	@Autowired
	private Gson gson;

	@Autowired
	private OutboundIndicatorPayloadMapper outboundIndicatorPayloadMapper;
	@Autowired
	private OutboundVerbosePayloadMapper outboundVerbosePayloadMapper;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private FallDeterminationProcessor fallDeterminationProcessor;

	@Autowired
	private Queue<InboundPayload> dataQueue;
	
	private static final String TOPIC_VERBOSE = "/topic/muse-verbose";
	private static final String TOPIC_INDICATOR = "/topic/muse-indicator";

	@MessageMapping("/muse-payload")
	public void payload(InboundPayload inboundPayload) throws Exception {
		LOG.info(gson.toJson(inboundPayload));
		dataQueue.add(inboundPayload);
		
		/*
		 * Determine status
		 * TODO: Change the values to enums (since seizure anaysis has three outputs)
		 */
		boolean fallFlag = fallDeterminationProcessor.determineFall(inboundPayload);
		boolean seizureFlag = inboundPayload.getSeizureFlag();

		/*
		 * Set indicator outbound properties
		 */
		OutboundIndicatorPayload outboundIndicatorPayload = outboundIndicatorPayloadMapper
				.mapOutboundIndicatorPayload(inboundPayload, fallFlag, seizureFlag);

		OutboundVerbosePayload outboundVerbosePayload = outboundVerbosePayloadMapper
				.mapOutboundVerbosePayload(inboundPayload, outboundIndicatorPayload);

		/*
		 * Send appropriate payloads
		 */
		if (outboundIndicatorPayload.getTimeMillis() == inboundPayload.getTimeMills()) {
			LOG.info("SENDING INDICATOR PAYLOAD: " + gson.toJson(outboundIndicatorPayload));
			template.convertAndSend(TOPIC_INDICATOR, outboundIndicatorPayload);
		}
		template.convertAndSend(TOPIC_VERBOSE, outboundVerbosePayload);
	}
}
