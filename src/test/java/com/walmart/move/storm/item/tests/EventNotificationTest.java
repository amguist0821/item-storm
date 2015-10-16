package com.walmart.move.storm.item.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

import com.walmart.move.event.item.core.Item;
import com.walmart.move.eventing.core.EventNotification;
import com.walmart.move.eventing.core.EventType;

/**
 * Class will contain those features that are required in order to 
 * perform unit testing against a common event domain object thus allowing
 * for the ability to increase the coverage that is required by all products.
 * 
 * @author amguist
 *
 */
public class EventNotificationTest {

	/*
	 * Class Defined Constants
	 */
	private static final String		mdcCorrelationId	=	"correlationId";
	
	/**
	 * Function will be utilized in order to perform the basic tests that are
	 * required in order to make sure that all aspects of the Event Notification
	 * object are covered.
	 */
	@Test
	public void basicEventNotificationTest() {
		org.slf4j.MDC.put(mdcCorrelationId, "1");
		
		EventNotification<Item> eventNotification = createEventNotification();
		assertNotNull(eventNotification.getHeader().getChecksum());
		assertNotNull(eventNotification.getHeader().getId());
		assertNotNull(eventNotification.getHeader().getName());
		assertNotNull(eventNotification.getHeader().getTimestamp());
		assertNotNull(eventNotification.getHeader().getType());
		assertNotNull(eventNotification.getHeader().getUser());
		assertNotNull(eventNotification.getHeader().getOrigin().getEventSource());
		
		org.slf4j.MDC.remove(mdcCorrelationId);
	}
	
	/**
	 * Function will be utilized in order to perform the basic test where
	 * the correlation identifier supplied within the event notification
	 * header is empty.
	 */
	@Test
	public void eventNotificationEmptyCorrelationIdTest() {
		org.slf4j.MDC.put(mdcCorrelationId, "");
		
		EventNotification<Item> eventNotification = createEventNotification();
		assertNotNull(eventNotification.getHeader().getChecksum());
		assertNotNull(eventNotification.getHeader().getId());
		assertNotNull(eventNotification.getHeader().getName());
		assertNotNull(eventNotification.getHeader().getTimestamp());
		assertNotNull(eventNotification.getHeader().getType());
		assertNotNull(eventNotification.getHeader().getUser());
		assertNotNull(eventNotification.getHeader().getOrigin().getEventSource());
		
		org.slf4j.MDC.remove(mdcCorrelationId);
	}
	
	/**
	 * Function will be utilized in order to perform the basic test where
	 * the correlation identifier supplied within the event notification
	 * header is null.
	 */
	@Test
	public void eventNotificationWithoutCorrelationIdTest() {
		EventNotification<Item> eventNotification = createEventNotification();
		assertNull(eventNotification.getHeader().getChecksum());
		assertNotNull(eventNotification.getHeader().getId());
		assertNotNull(eventNotification.getHeader().getName());
		assertNotNull(eventNotification.getHeader().getTimestamp());
		assertNotNull(eventNotification.getHeader().getType());
		assertNotNull(eventNotification.getHeader().getUser());
		assertNotNull(eventNotification.getHeader().getOrigin().getEventSource());
	}
	
	/**
	 * Function will be utilized in order to provide the ability to create an event
	 * notification object based on an Item event being pushed to some type of
	 * back end server.
	 * 
	 * @return Initialized Event Notification
	 */
	private EventNotification<Item> createEventNotification() {
		Item testItem = new Item();
		testItem.setWalmartItemNumber(2300202011L);
		
		EventNotification<Item> eventNotification = new EventNotification<Item>(testItem,"testUser","item.event", new Date(), EventType.Business, "MasterItem");
		return(eventNotification);
	}
}
