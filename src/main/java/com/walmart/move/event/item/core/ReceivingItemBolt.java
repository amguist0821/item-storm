package com.walmart.move.event.item.core;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.walmart.move.eventing.core.StringToGson;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * Class will be utilized in order to provide the ability to transform an
 * update that would have come from the Master Item service which would require
 * to have changes done within the Receiving Product in order to update their
 * item information.
 * 
 * @author amguist
 *
 */
public class ReceivingItemBolt extends BaseBasicBolt {

	/*
	 * Class Member Variables
	 */
	private static final long			serialVersionUID	=		1L;
	private static final Logger			logger				=		Logger.getLogger(ReceivingItemBolt.class);
	
	private static final String			MASTER_ORIGIN		= "MasterItem";	
	private static final String			dateFormat 			= "dd MMM yyyy HH:mm:ss";			// Format required for JSON Conversion
	
	/**
	 * Function is utilized in order to define those fields which are come out
	 * from the processing of the initial event notification that was received 
	 * over Apache Kafka and which have not been generated from a Next Generation
	 * product.
	 */
	public void declareOutputFields(OutputFieldsDeclarer outputDeclarer) { 
		outputDeclarer.declare(new Fields("correlation_identifier", "receiving_item_object"));
	}
	
	/**
	 * Function will examine the origin of the message in order to determine whether or not
	 * the message needs to be formatted and published to the Item Master Bolt, which would 
	 * send item updates to the Item Master first, else push information to the next Bolt which 
	 * will then process the information and push to multiple bolts which require the item
	 * update for their local copy of item.
	 */
	public void execute(Tuple input, BasicOutputCollector collector) {
		String  correlationId = input.getString(0);
		String	eventOrigin = input.getString(1);
		String  eventObject = input.getString(2);
		
		logger.info("Item Event Notification Orgin is: " + eventOrigin);
		if( eventOrigin.equals(MASTER_ORIGIN)) {
			logger.info("[" + correlationId + "] - " + "Process is transforming information from Item Master to that of Receiving Item for the Receiving Product ....");
			logger.info("[" + correlationId + "] - " + "Event Object: " + eventObject);
			
			StringToGson<Item> stringToGson = new StringToGson<Item>(new TypeToken<Item>(){}.getType());
			Item item = stringToGson.convert(eventObject);
			
			ReceivingItem receivingItem = new ReceivingItem();
			receivingItem.setReceivingItemNumber(item.getWalmartItemNumber());
			receivingItem.setItemName("Test_Item");
			receivingItem.setShortDescription("This Item Is A Test Item");
			
			Gson gson	=	new GsonBuilder().setDateFormat(dateFormat).create();
			String receivingPayload = gson.toJson(receivingItem);
			/*
			 *	Another Bolt will end up perform the transformation and then
			 *  another Bolt will perform any additional calls to the Master Item
			 *  Micro Service. 
			 */
			collector.emit(new Values(correlationId, receivingPayload));
		}
	}
}
