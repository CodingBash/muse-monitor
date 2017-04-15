package com.codingbash.musemonitor.socketserver.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private Queue<OutboundIndicatorPayload> indicatorQueue;

	public OutboundIndicatorPayload mapOutboundIndicatorPayload(InboundPayload inboundPayload, boolean fallFlag,
			boolean seizureFlag) {
		OutboundIndicatorPayload outboundIndicatorPayload = new OutboundIndicatorPayload();
		outboundIndicatorPayload.setPatientId(inboundPayload.getPatientId());
		boolean newStatus = false;
		if (fallFlag) {
			outboundIndicatorPayload.setPhysicalStatus(PhysicalStatus.EMERGENCY);
			if (checkPhysicalStatus(indicatorQueue, inboundPayload, PhysicalStatus.EMERGENCY)) {
				// New status
				newStatus = true;
			}
		} else {
			outboundIndicatorPayload.setPhysicalStatus(PhysicalStatus.GOOD);
			if (checkPhysicalStatus(indicatorQueue, inboundPayload, PhysicalStatus.GOOD)) {
				// New status
				newStatus = true;
			}
		}

		if (seizureFlag) {
			outboundIndicatorPayload.setMentalStatus(MentalStatus.EMERGENCY);
			if (checkMentalStatus(indicatorQueue, inboundPayload, MentalStatus.EMERGENCY)) {
				// New status
				newStatus = true;
			}
		} else {
			outboundIndicatorPayload.setMentalStatus(MentalStatus.GOOD);
			if (checkMentalStatus(indicatorQueue, inboundPayload, MentalStatus.GOOD)) {
				// New status
				newStatus = true;
			}
		}
		System.out.println("NEWSTATUS" + newStatus);
		if (newStatus) {
			outboundIndicatorPayload.setTimeMillis(inboundPayload.getTimeMills());
			indicatorQueue.add(outboundIndicatorPayload);
		}
		return outboundIndicatorPayload;
	}

	private boolean checkPhysicalStatus(Queue<OutboundIndicatorPayload> indicatorQueue, InboundPayload inboundPayload,
			PhysicalStatus status) {
		List<OutboundIndicatorPayload> list = new ArrayList<OutboundIndicatorPayload>(indicatorQueue);

		if (!list.isEmpty()) {
			/*
			 * Check if target status is different from last status change
			 */
			if (list.get(list.size() - 1).getPhysicalStatus() != status) {
				if (list.size() > 1) {
					/*
					 * Find the last status change that is the same as the
					 * target status
					 */
					for (int i = list.size() - 2; i >= 0; i--) {
						if (list.get(i).getPhysicalStatus() == status) {
							/*
							 * If the last synonymous status change was more
							 * than 3 seconds ago, send indicator change
							 */
							if (inboundPayload.getTimeMills() - list.get(i).getTimeMillis() > 3 * 1000) {
								return true;
							} else {
								break;
							}
						}
						return true;
					}
				} else {
					/*
					 * There is only one saved indicator and it is different
					 */
					return true;
				}

			}
		} else {
			return true;
		}
		return false;
	}

	private boolean checkMentalStatus(Queue<OutboundIndicatorPayload> indicatorQueue, InboundPayload inboundPayload,
			MentalStatus status) {
		List<OutboundIndicatorPayload> list = new ArrayList<OutboundIndicatorPayload>(indicatorQueue);

		if (!list.isEmpty()) {
			/*
			 * Check if target status is different from last status change
			 */
			if (list.get(list.size() - 1).getMentalStatus() != status) {
				if (list.size() > 1) {
					/*
					 * Find the last status change that is the same as the
					 * target status
					 */
					for (int i = list.size() - 2; i >= 0; i--) {
						if (list.get(i).getMentalStatus() == status) {
							/*
							 * If the last synonymous status change was more
							 * than 3 seconds ago, send indicator change
							 */
							if (inboundPayload.getTimeMills() - list.get(i).getTimeMillis() > 3 * 1000) {
								return true;
							} else {
								break;
							}
						}
						return true;
					}
				} else {
					/*
					 * There is only one saved indicator and it is different
					 */
					return true;
				}

			}
		} else {
			return true;
		}
		return false;
	}
}
