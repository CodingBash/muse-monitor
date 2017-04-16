package com.codingbash.musemonitor.socketserver.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;
import com.codingbash.musemonitor.socketserver.model.MentalStatus;
import com.codingbash.musemonitor.socketserver.model.OutboundIndicatorPayload;
import com.codingbash.musemonitor.socketserver.model.PhysicalStatus;

// TODO: Modularize status checkers
@Component
public class OutboundIndicatorPayloadMapper {

	private final static Logger LOG = LoggerFactory.getLogger(OutboundIndicatorPayloadMapper.class);

	@Autowired
	@Qualifier("physicalIndicatorQueue")
	private Queue<OutboundIndicatorPayload> physicalIndicatorQueue;

	@Autowired
	@Qualifier("mentalIndicatorQueue")
	private Queue<OutboundIndicatorPayload> mentalIndicatorQueue;

	public OutboundIndicatorPayload mapOutboundIndicatorPayload(InboundPayload inboundPayload, boolean fallFlag,
			boolean seizureFlag) {
		OutboundIndicatorPayload outboundIndicatorPayload = new OutboundIndicatorPayload();
		outboundIndicatorPayload.setPatientId(inboundPayload.getPatientId());

		PhysicalStatus newPhysicalStatus = null;
		MentalStatus newMentalStatus = null;
		boolean physicalNewStatus = false;
		boolean mentalNewStatus = false;
		if (fallFlag) {
			newPhysicalStatus = PhysicalStatus.EMERGENCY;
			if (checkPhysicalStatus(physicalIndicatorQueue, inboundPayload, PhysicalStatus.EMERGENCY)) {
				// New status
				physicalNewStatus = true;
			}
		} else {
			newPhysicalStatus = PhysicalStatus.GOOD;
			if (checkPhysicalStatus(physicalIndicatorQueue, inboundPayload, PhysicalStatus.GOOD)) {
				// New status
				physicalNewStatus = true;
			}
		}

		if (seizureFlag) {
			newMentalStatus = MentalStatus.EMERGENCY;
			if (checkMentalStatus(mentalIndicatorQueue, inboundPayload, MentalStatus.EMERGENCY)) {
				// New status
				mentalNewStatus = true;
			}
		} else {
			newMentalStatus = MentalStatus.GOOD;
			if (checkMentalStatus(mentalIndicatorQueue, inboundPayload, MentalStatus.GOOD)) {
				// New status
				mentalNewStatus = true;
			}
		}
		if (physicalNewStatus || mentalNewStatus) {
			outboundIndicatorPayload.setTimeMillis(inboundPayload.getTimeMills());
			if (physicalNewStatus) {
				outboundIndicatorPayload.setPhysicalStatus(newPhysicalStatus);
				physicalIndicatorQueue.add(outboundIndicatorPayload);
			}
			if (mentalNewStatus) {
				outboundIndicatorPayload.setMentalStatus(newMentalStatus);
				mentalIndicatorQueue.add(outboundIndicatorPayload);
			}
		}
		return outboundIndicatorPayload;
	}

	private boolean checkPhysicalStatus(Queue<OutboundIndicatorPayload> indicatorQueue, InboundPayload inboundPayload,
			PhysicalStatus status) {
		List<OutboundIndicatorPayload> list = new ArrayList<OutboundIndicatorPayload>(indicatorQueue);

		if (!list.isEmpty()) {
			if (list.get(list.size() - 1).getPhysicalStatus() != status
					&& inboundPayload.getTimeMills() - list.get(list.size() - 1).getTimeMillis() > 3 * 1000) {
				return true;
			} else {
				return false;
			}
		} else {
			/*
			 * If list is empty, return new indicator
			 */
			return true;
		}
	}

	private boolean checkMentalStatus(Queue<OutboundIndicatorPayload> indicatorQueue, InboundPayload inboundPayload,
			MentalStatus status) {
		List<OutboundIndicatorPayload> list = new ArrayList<OutboundIndicatorPayload>(indicatorQueue);

		if (!list.isEmpty()) {
			if (list.get(list.size() - 1).getMentalStatus() != status
					&& inboundPayload.getTimeMills() - list.get(list.size() - 1).getTimeMillis() > 3 * 1000) {
				return true;
			} else {
				return false;
			}
		} else {
			/*
			 * If list is empty, return new indicator
			 */
			return true;
		}
	}
}
