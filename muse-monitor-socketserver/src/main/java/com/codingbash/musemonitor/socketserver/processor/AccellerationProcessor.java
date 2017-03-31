package com.codingbash.musemonitor.socketserver.processor;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codingbash.musemonitor.socketserver.model.AccelerationPacket;
import com.codingbash.musemonitor.socketserver.model.JerkPacket;

/*
 * Acceleration is in G-Force: https://en.wikipedia.org/wiki/G-force
 */
@Component
public class AccellerationProcessor {

	/**
	 * When stationary, result = 1 When falling, accelleleration is changing,
	 * thus the jerk must be calculated via calculus
	 * 
	 */
	public double retrieveAcceleration(AccelerationPacket packet) {
		return Math.sqrt(
				Math.pow(packet.getAccelX(), 2) + Math.pow(packet.getAccelY(), 2) + Math.pow(packet.getAccelZ(), 2));
	}

	/*
	 * TODO: Determine what derivative the algorithm uses
	 * Calculates G/ms
	 */
	public List<AccelerationPacket> calculateJerk(List<AccelerationPacket> inboundPacket) {
		List<JerkPacket> outboundPacket = new LinkedList<JerkPacket>();
		for (int i = 0; i < inboundPacket.size() - 1; i++) {
			JerkPacket jPacket = new JerkPacket();
			/*
			 * Time = the time between current time and next time
			 */
			jPacket.setTimeMillis(inboundPacket.get(i).getTimeMillis()
					+ (inboundPacket.get(i + 1).getTimeMillis() - inboundPacket.get(i).getTimeMillis()) / 2);

			/*
			 * Derivatives calculated by the difference quotient
			 */
			jPacket.setJerkX((inboundPacket.get(i + 1).getAccelX() - inboundPacket.get(i).getAccelX())
					/ (inboundPacket.get(i + 1).getTimeMillis() - inboundPacket.get(i).getTimeMillis()));
			jPacket.setJerkY((inboundPacket.get(i + 1).getAccelY() - inboundPacket.get(i).getAccelY())
					/ (inboundPacket.get(i + 1).getTimeMillis() - inboundPacket.get(i).getTimeMillis()));
			jPacket.setJerkZ((inboundPacket.get(i + 1).getAccelZ() - inboundPacket.get(i).getAccelZ())
					/ (inboundPacket.get(i + 1).getTimeMillis() - inboundPacket.get(i).getTimeMillis()));
			
			/*
			 * Add to list
			 */
			outboundPacket.add(jPacket);
		}
		return null;
	}

}
