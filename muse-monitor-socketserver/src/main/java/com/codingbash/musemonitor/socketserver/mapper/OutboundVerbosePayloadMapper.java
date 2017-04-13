package com.codingbash.musemonitor.socketserver.mapper;

import org.springframework.stereotype.Component;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;
import com.codingbash.musemonitor.socketserver.model.OutboundIndicatorPayload;
import com.codingbash.musemonitor.socketserver.model.OutboundVerbosePayload;

@Component
public class OutboundVerbosePayloadMapper {

	public OutboundVerbosePayload mapOutboundVerbosePayload(InboundPayload inboundPayload,
			OutboundIndicatorPayload outboundIndicatorPayload) {
		OutboundVerbosePayload outboundVerbosePayload = new OutboundVerbosePayload();
		outboundVerbosePayload.setPatientId(inboundPayload.getPatientId());
		outboundVerbosePayload.setTimeMillis(inboundPayload.getTimeMills());
		outboundVerbosePayload.setEegData(inboundPayload.getEegData());
		outboundVerbosePayload.setAccelerometerData(inboundPayload.getAccelerometerData());
		outboundVerbosePayload.setGyroscopeData(inboundPayload.getGyroscopeData());
		outboundVerbosePayload.setMentalStatus(outboundIndicatorPayload.getMentalStatus());
		outboundVerbosePayload.setPhysicalStatus(outboundIndicatorPayload.getPhysicalStatus());
		return outboundVerbosePayload;
	}
}
