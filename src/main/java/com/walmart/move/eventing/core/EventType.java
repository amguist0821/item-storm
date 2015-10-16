package com.walmart.move.eventing.core;

/**
 * Enumeration contains those applicable features which are needed in order to
 * define the type of event which is being broadcast.  These events could be as 
 * follows:
 * 
 * Business	-	0x000000001
 * System   -   0x000000002
 * Alert    -   0x000000003
 * 
 * @author amguist
 *
 */
public enum EventType {

	/*
	 * Defined Enumeration Types
	 */
	Business(1), System(2), Alert(3), None(99);
	
	private	Integer	eventTypeCode;
	
	/**
	 * Function is defined as being the default class constructor that is to be utilized
	 * in order to initialize the Enumeration to it's initial state of being
	 * thus allowing it to be consumed.
	 * 
	 * @param eventType
	 */
	EventType(Integer eventTypeCode) {
		this.eventTypeCode = eventTypeCode;
	}
	
	/**
	 * Function will return back to the calling method event type code that
	 * has been assigned for a given Event Type.
	 * 
	 * @return Event Type Code
	 */
	public String	getEventTypeCode() {
		return(Integer.toHexString(this.eventTypeCode));
	}
	
	/**
	 * Function will be utilized in order to retrieve back to the calling method 
	 * the entire Event Type Object that is associated the supplied event type 
	 * code.
	 * 
	 * @param eventTypeCode
	 * @return Event Type
	 */
	public static EventType	getEventType(int eventTypeCode) {
		EventType	eventType = null;
		for( EventType currentEventType : EventType.values()) {
			if(currentEventType.eventTypeCode == eventTypeCode) {
				eventType = currentEventType;
			}
		}
		return(eventType);
	}
}
