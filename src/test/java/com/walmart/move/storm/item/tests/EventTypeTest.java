package com.walmart.move.storm.item.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.walmart.move.eventing.core.EventType;

/**
 * Class will be utilized in order to provide the ability to test the Event Type
 * Enumeration completely in order to make sure that all scenarios are in fact covered
 * while testing objects.
 * 
 * @author amguist
 *
 */
public class EventTypeTest {

	/**
	 * Function will be utilized in order to provide a test
	 * where a given User asks to receive the entire event
	 * type enumeration object based on supplying a valid 
	 * input parameter.
	 */
	@Test
	public void getEventTypeTest() {
		assertNotNull(EventType.getEventType(1));
		assertNotNull(EventType.valueOf("Business"));
	}
}
