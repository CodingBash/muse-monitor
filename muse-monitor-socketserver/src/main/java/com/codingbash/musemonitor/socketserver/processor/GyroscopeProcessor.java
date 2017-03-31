package com.codingbash.musemonitor.socketserver.processor;

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

	// TODO: Create derivative calclation for angular velocity
}
