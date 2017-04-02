package com.codingbash.musemonitor.socketserver.processor;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codingbash.musemonitor.socketserver.model.GyroscopePacket;

@Component
public class GyroscopeProcessor {

	/*
	 * When stationary, result = 0 When falling, the angular velocity produces
	 * patterns
	 */
	public double retrieveOrientation(GyroscopePacket packet) {
		return Math
				.sqrt(Math.pow(packet.getGyroX(), 2) + Math.pow(packet.getGyroY(), 2) + Math.pow(packet.getGyroZ(), 2));
	}
	
	
	public List<AngularAccelerationPacket> calculateAngularAcceleration(List<GyroscopePacket> inboundPacket){
		List<AngularAccelerationPacket> outboundPacket = new LinkedList<AngularAccelerationPacket>();
		for (int i = 0; i < inboundPacket.size() - 1; i++) {
			AngularAccelerationPacket aPacket = new AngularAccelerationPacket();
			/*
			 * Time = the time between current time and next time
			 */
			aPacket.setTimeMillis(inboundPacket.get(i).getTimeMillis()
					+ (inboundPacket.get(i + 1).getTimeMillis() - inboundPacket.get(i).getTimeMillis()) / 2);

			/*
			 * Derivatives calculated by the difference quotient
			 */
			aPacket.setAaX((inboundPacket.get(i + 1).getGyroX() - inboundPacket.get(i).getGyroX())
					/ (inboundPacket.get(i + 1).getTimeMillis() - inboundPacket.get(i).getTimeMillis()));
			aPacket.setAaY((inboundPacket.get(i + 1).getGyroY() - inboundPacket.get(i).getGyroY())
					/ (inboundPacket.get(i + 1).getTimeMillis() - inboundPacket.get(i).getTimeMillis()));
			aPacket.setAaZ((inboundPacket.get(i + 1).getGyroZ() - inboundPacket.get(i).getGyroZ())
					/ (inboundPacket.get(i + 1).getTimeMillis() - inboundPacket.get(i).getTimeMillis()));
			
			/*
			 * Add to list
			 */
			outboundPacket.add(aPacket);
		}
		
		return outboundPacket;
	}
	// TODO: Create derivative calclation for angular velocity
}
