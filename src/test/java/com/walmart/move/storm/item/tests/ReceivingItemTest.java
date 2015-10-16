package com.walmart.move.storm.item.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.walmart.move.event.item.core.ReceivingItem;

/**
 * Class will be utilized in order to provide the ability to populate information
 * that is needed for the Receiving Product based on those Items which are coming from the
 * Master Item Service upon receiving events from either within the Next Generation Supply
 * Chain Products or Enterprise Master Item.
 * 
 * @author amguist
 *
 */
public class ReceivingItemTest {

	/**
	 * Function will be utilized in order to perform those tests against the
	 * Receiving Item object, which is utilized by the Receiving Product for
	 * being able to communicate the Master Item Service.
	 * 
	 */
	@Test
	public void receivingItemTest() {
		
		ReceivingItem receivingItem = new ReceivingItem();
		receivingItem.setReceivingItemNumber(1L);
		receivingItem.setItemName("Test_Item");
		receivingItem.setShortDescription("This Item Is A Test Item");
		
		assertNotNull(receivingItem.getReceivingItemNumber());
		assertNotNull(receivingItem.getItemName());
		assertNotNull(receivingItem.getShortDescription());
	}
}
