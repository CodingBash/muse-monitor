package com.codingbash.musemonitor.socketserver;

import java.util.Queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.codingbash.musemonitor.socketserver.model.FallIndicatorWrapper;
import com.codingbash.musemonitor.socketserver.model.InboundPayload;
import com.codingbash.musemonitor.socketserver.model.OutboundIndicatorPayload;
import com.codingbash.musemonitor.socketserver.model.PreviousStatusHolder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		registry.addEndpoint("/muse-ws").setAllowedOrigins("*");
	}
	
	@Bean
	public Gson gson(){
		return new GsonBuilder().create();
	}
	
	@Bean
	public Queue<InboundPayload> dataQueue(){
		/*
		 * CALCULATING SIZE OF QUEUE:
		 * Size of queue = 20 seconds
		 * Frequency of input data = 1000 / 30 ms = 30Hz (1/30 s)
		 * Size = 20s / 30Hz = 600
		 */
		int sizeOfCircularQueue = 600;
		
		/*
		 * Circular Queue Documentation: http://commons.apache.org/proper/commons-collections/javadocs/api-release/org/apache/commons/collections4/queue/CircularFifoQueue.html
		 */
		return new CircularFifoQueue<InboundPayload>(sizeOfCircularQueue);
	}
	
	@Bean
	public Queue<OutboundIndicatorPayload> indicatorQueue(){
		int sizeOfCircularQueue = 10;
		
		return new CircularFifoQueue<OutboundIndicatorPayload>(sizeOfCircularQueue);
	}
	@Bean
	public FallIndicatorWrapper indicatorWrapper(){
		FallIndicatorWrapper indicators = new FallIndicatorWrapper();
		indicators.setIndicatorOne(false);
		indicators.setIndicatorTwo(false);
		indicators.setTimeInterval(1000);
		return indicators;
	}
	
	@Bean
	public PreviousStatusHolder previousStatus(){
		PreviousStatusHolder previousStatus = new PreviousStatusHolder();
		return previousStatus;
	}
	
	
}
