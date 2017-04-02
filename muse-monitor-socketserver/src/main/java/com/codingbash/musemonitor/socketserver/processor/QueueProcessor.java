package com.codingbash.musemonitor.socketserver.processor;

import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codingbash.musemonitor.socketserver.model.InboundPayload;

@Component
public class QueueProcessor {

	/*
	 * May turn to be a Map<K,V> with V  as the Queue and K as the patient ID
	 */
	@Autowired
	private Queue<InboundPayload> dataQueue;
	
	private static final int MAX_QUEUE_DURATION = 3 * 1000;
	
	public void addItem(InboundPayload incomingPayload){
		/*
		 * Add Item
		 */
		dataQueue.add(incomingPayload);
		
		/*
		 * Remove expired items
		 */
		boolean keepChecking;
		do {
			keepChecking = false;
			InboundPayload queuePayload = dataQueue.peek();
			if(queuePayload != null){
				if(incomingPayload.getTimeMills() - queuePayload.getTimeMills() > MAX_QUEUE_DURATION){
					dataQueue.remove();
					keepChecking = true;
				}
			}
		} while (keepChecking);
	}
	
	@SuppressWarnings("unchecked")
	public List<InboundPayload> getDataAsList(){
		return (List<InboundPayload>) dataQueue;
	}
}
