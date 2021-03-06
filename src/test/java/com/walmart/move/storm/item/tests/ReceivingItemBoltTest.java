package com.walmart.move.storm.item.tests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walmart.move.event.item.core.Item;
import com.walmart.move.event.item.core.ReceivingItemBolt;
import com.walmart.move.eventing.core.EventNotification;
import com.walmart.move.eventing.core.EventType;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * Class will be utilized in order to perform the necessary tests which are required in order
 * to perform tests against the Receiving Item Bolt, which would take a filter Item Notification
 * message and transform the information that was published by the Master Item so that 
 * the Receiving copy of Item would remain in sync with the Master.
 * 
 * @author amguist
 *
 */
public class ReceivingItemBoltTest {

	/*
	 * Class Member Variables
	 */
	private final	Gson	gson	=	new GsonBuilder().setDateFormat(dateFormat).create();
	
	/*
	 * Class Constant Variables
	 */
	private static final String		ANY_NON_SYSTEM_COMPONENT_ID	=	"irrelevant_id";
	private static final String		ANY_NON_SYSTEM_STREAM_ID =		"irrelevant_stream";
	
	private static final String		dateFormat 						= "dd MMM yyyy HH:mm:ss";			// Format required for JSON Conversion
	private static final String		mdcCorrelationId				=	"correlationId";
	
	/**
	 * Function will return back to the calling method an initialized
	 * Tuple that can be utilized within a given Storm Bolt test.
	 * 
	 * @param object
	 * @return
	 */
	private Tuple mockNormalTuple(EventNotification<Item> object) {
		Tuple tuple = MockTupleHelpers.mockTuple(ANY_NON_SYSTEM_COMPONENT_ID, ANY_NON_SYSTEM_STREAM_ID);
		when(tuple.getString(0)).thenReturn(object.getHeader().getChecksum());
		when(tuple.getString(1)).thenReturn(object.getHeader().getOrigin().getEventSource());
		when(tuple.getString(2)).thenReturn(gson.toJson(object.getPayload()));
		return(tuple);
	}
	
	/**
	 * Function will be utilized in order to provide the ability to create an event
	 * notification object based on an Item event being pushed to some type of
	 * back end server.
	 * 
	 * @return Initialized Event Notification
	 */
	private EventNotification<Item> createEventNotification(String eventSource) {
		Item testItem = new Item();
		testItem.setWalmartItemNumber(2300202011L);
		
		EventNotification<Item> eventNotification = new EventNotification<Item>(testItem,"testUser","item.event", new Date(), EventType.Business, eventSource);
		return(eventNotification);
	}
	
	/**
	 * Function will be utilized in order to perform the basic test against
	 * the Item Filter which will extract out those features of the Event
	 * Notification which are needed to be carried forward within the
	 * rest of the Event Processing of an Item Event.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void receivingItemEmitTest() throws Exception {
		org.slf4j.MDC.put(mdcCorrelationId, "1");
		Tuple normalTuple = mockNormalTuple(createEventNotification("MasterItem"));
		ReceivingItemBolt receivingItemBolt	=	new ReceivingItemBolt();
		Map conf = mock(Map.class);
		
		TopologyContext	context	= mock(TopologyContext.class);
		BasicOutputCollector collector = mock(BasicOutputCollector.class);
		OutputFieldsDeclarer outputDeclarer = mock(OutputFieldsDeclarer.class);
		receivingItemBolt.declareOutputFields(outputDeclarer);
		receivingItemBolt.prepare(conf, context);
		receivingItemBolt.getComponentConfiguration();
		
		receivingItemBolt.execute(normalTuple, collector);
		
		verify(collector).emit(any(Values.class));
		org.slf4j.MDC.remove(mdcCorrelationId);
	}
	
	/**
	 * Function will be utilized in order to perform the basic test against
	 * the Item Filter which will extract out those features of the Event
	 * Notification which are needed to be carried forward within the
	 * rest of the Event Processing of an Item Event.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void receivingItemNonEmitTest() throws Exception {
		org.slf4j.MDC.put(mdcCorrelationId, "1");
		Tuple normalTuple = mockNormalTuple(createEventNotification("InventoryControl"));
		ReceivingItemBolt receivingItemBolt	=	new ReceivingItemBolt();
		Map conf = mock(Map.class);
		
		TopologyContext	context	= mock(TopologyContext.class);
		BasicOutputCollector collector = mock(BasicOutputCollector.class);
		OutputFieldsDeclarer outputDeclarer = mock(OutputFieldsDeclarer.class);
		receivingItemBolt.declareOutputFields(outputDeclarer);
		receivingItemBolt.prepare(conf, context);
		receivingItemBolt.getComponentConfiguration();
		
		receivingItemBolt.execute(normalTuple, collector);
		
		org.slf4j.MDC.remove(mdcCorrelationId);
	}	
}
