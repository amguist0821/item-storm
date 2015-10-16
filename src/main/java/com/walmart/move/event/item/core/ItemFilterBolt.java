package com.walmart.move.event.item.core;

import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.walmart.move.eventing.core.EventNotification;
import com.walmart.move.eventing.core.StringToGson;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * Class will be responsible for receiving information from Apache Kafka
 * and filtering the information which has been received.  This means that 
 * there would be two variables which are to be filtered out, which includes
 * the Correlation Identifier along with the published object.
 * 
 * @author amguist
 *
 */
public class ItemFilterBolt extends BaseBasicBolt {

	/*
	 * Class Member Variables
	 */
	private static final Logger			logger	=		Logger.getLogger(ItemFilterBolt.class);
	
	private static final long 			serialVersionUID = 1L;
	private static final String			dateFormat 						= "dd MMM yyyy HH:mm:ss";			// Format required for JSON Conversion
	
	
	/**
	 * Function is utilized in order to define those fields which are come out
	 * from the processing of the initial event notification that was received 
	 * over Apache Kafka.
	 * 
	 * @param outputDeclarer
	 */
	public void declareOutputFields(OutputFieldsDeclarer outputDeclarer) {
		outputDeclarer.declare(new Fields("correlation_identifier","event_origin","event_object","event_identifier"));
	}
	
	/**
	 * Function will be executed upon receiving an event notification on
	 * item which has been published by some product within the Next Generation
	 * Supply Chain, thus the action is needed in order to be able to
	 * determine what exactly needs to be performed.
	 */
	public void execute(Tuple input, BasicOutputCollector collector) {
		logger.info("Filtering incoming Item Event Notifications ....");
		
		String itemJsonString = input.getString(0);		
		logger.info("Message Payload: " + itemJsonString);
		
		StringToGson<EventNotification<Item>> stringToGson = new StringToGson<EventNotification<Item>>(new TypeToken<EventNotification<Item>>(){}.getType());
		EventNotification<Item> eventNotification = stringToGson.convert(itemJsonString);
		
		Gson				gson	=	new GsonBuilder().setDateFormat(dateFormat).create();
		String itemPayload = gson.toJson(eventNotification.getPayload());
		
		collector.emit(new Values(eventNotification.getHeader().getChecksum(), eventNotification.getHeader().getOrigin().getEventSource(), itemPayload, eventNotification.getHeader().getId()));
	}
	
	/**
	 * Function will supply the ability to return back a specific configuration 
	 * which is to be utilized in order to initialize the Item Filter Bolt.
	 */
	public Map<String, Object> getComponentConfiguration() {
		return(null);
	}
}
