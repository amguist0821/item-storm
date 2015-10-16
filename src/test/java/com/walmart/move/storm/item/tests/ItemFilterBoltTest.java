package com.walmart.move.storm.item.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walmart.move.event.item.core.Item;
import com.walmart.move.event.item.core.ItemFilterBolt;
import com.walmart.move.eventing.core.EventNotification;
import com.walmart.move.eventing.core.EventType;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


/**
 * Class will be utilized in order to provide the ability to perform tests against
 * a Storm Bolt.  There right now were some restrictions on the definition of
 * the Timeout Session within the Testing framework of Apache Storm, which is supposed
 * be resolved within later versions of the product, however, this also depends still
 * on the machine which is executing the test case.  Therefore, in order to not have
 * this issue, going to test each Bolt individually.
 * 
 * @author amguist
 *
 */
public class ItemFilterBoltTest {

	/*
	 * Class Member Variables
	 */
	private final	Gson	gson	=	new GsonBuilder().setDateFormat(dateFormat).create();
	
	/*
	 * Class Constant Variables
	 */
	private static final String		ANY_NON_SYSTEM_COMPONENT_ID	=	"irrelevant_id";
	private static final String		ANY_NON_SYSTEM_STREAM_ID =		"irrelevant_stream";
	
	private static final String		mdcCorrelationId	=	"correlationId";
	private static final 	String	dateFormat = "dd MMM yyyy HH:mm:ss";			// Format required for JSON Conversion
	
	/**
	 * Function will return back to the calling method an initialized
	 * Tuple that can be utilized within a given Storm Bolt test.
	 * 
	 * @param object
	 * @return
	 */
	private Tuple mockNormalTuple(Object object) {
		Tuple tuple = MockTupleHelpers.mockTuple(ANY_NON_SYSTEM_COMPONENT_ID, ANY_NON_SYSTEM_STREAM_ID);
		when(tuple.getString(0)).thenReturn((String) object);
		return(tuple);
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
	public void itemFilterEmitTest() throws Exception {
		org.slf4j.MDC.put(mdcCorrelationId, "1");
		Tuple normalTuple = mockNormalTuple(gson.toJson(createEventNotification()));
		ItemFilterBolt filterBolt	=	new ItemFilterBolt();
		Map conf = mock(Map.class);
		
		TopologyContext	context	= mock(TopologyContext.class);
		BasicOutputCollector collector = mock(BasicOutputCollector.class);
		OutputFieldsDeclarer outputDeclarer = mock(OutputFieldsDeclarer.class);
		filterBolt.declareOutputFields(outputDeclarer);
		filterBolt.prepare(conf, context);
		filterBolt.getComponentConfiguration();
		
		filterBolt.execute(normalTuple, collector);
		
		verify(collector).emit(any(Values.class));
		org.slf4j.MDC.remove(mdcCorrelationId);
	}
}
