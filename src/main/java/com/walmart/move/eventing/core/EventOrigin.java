package com.walmart.move.eventing.core;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class will be utilized in order to define the origin at which a given
 * Event Message is coming from in order to pinpoint the exact location 
 * so that decisions can be made possibly.
 * 
 * @author amguist
 *
 */
@XmlRootElement
public class EventOrigin {

	/*
	 * Class Member Variables
	 */
	private	String		eventSource;
	
	/**
	 * Function is defined as being the default class constructor which is to be utilized 
	 * in order to initialize the Event Origin domain to that which it needs.
	 */
	EventOrigin(String eventSource) {
		this.eventSource =	eventSource;
	}
	
	/**
	 * Function will be utilized in order to return back to the calling method the
	 * event source, which is the process which published the event to the
	 * message services within the Next Generation Supply Chain.
	 * 
	 * @return Message Source
	 */
	public String	getEventSource() {
		return(this.eventSource);
	}
	
	
}
