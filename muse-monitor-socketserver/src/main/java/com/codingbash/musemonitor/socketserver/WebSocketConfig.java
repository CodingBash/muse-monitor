package com.codingbash.musemonitor.socketserver;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		/*
		 * Endpoint to broadcast to subscribers of the WS
		 */
		config.enableSimpleBroker("/topic", "/queue");
		
		/*
		 * Endpoint to send to WS controller for application work
		 */
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		/*
		 * This is the endpoint where clients register their WS client
		 * Allowing any origin
		 */
		registry.addEndpoint("/muse-ws").setAllowedOrigins("*").withSockJS();
	}
}
