package com.walmart.move.eventing.core;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class is the transfer object which is to be utilized for communicating with the
 * Message Backbone within the Next Generation Supply Chain.
 * 
 * @author amguist
 *
 * @param <T>
 */
@XmlRootElement
public class EventNotification<T> {

	/*
	 * Class Member Variables
	 */
	private	EventHeader	header;
	private T			payload;
	
	/**
	 * Function is defined as being the overriding class constructor which is to be 
	 * utilized in order to initialize a new Event Notification Object with the necessary
	 * member variables which are to be required.
	 * 
	 * @param target
	 * @param user
	 * @param event
	 * @param eventTime
	 */
	public EventNotification(T target, String user, String event, Date eventTime, EventType eventType, String eventSource) {
		this.header = new EventHeader(user,event,eventType, eventSource);
		this.payload = target;
	}
	
	/**
	 * Function will be utilized in order to return back to the calling method
	 * the Header that is to utilized when sending messages over the Walmart 
	 * Network to any of the Next Generation Supply and Enterprise products.
	 * 
	 * @return Event Message Header
	 */
	public EventHeader	getHeader() {
		return(this.header);
	}
	
	/**
	 * Function will be utilized in order to return back to the calling method the 
	 * object payload which is being sent to the Message Back end.
	 * 
	 * @return Message Pay Load
	 */
	public T getPayload() {
		return(this.payload);
	}
}
