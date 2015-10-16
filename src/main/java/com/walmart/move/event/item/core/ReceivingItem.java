package com.walmart.move.event.item.core;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class is going to be represent the contents which are required in order to
 * format a given item to that of Receiving Item so that the Receiving product
 * would be able to listen for those Receiving Item updates in order to update
 * it's own copy of Item.
 * 
 * @author amguist
 *
 */
@XmlRootElement
public class ReceivingItem implements Serializable {

	/*
	 * Class Member Variables 
	 */
	private	Long	receivingItemNumber;
	private String	itemName;
	private String	shortDescription;
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Function is defined as being the default class constructor which 
	 * is being utilized in order to format the Item Domain for the Receiving Product
	 * from within the Item Topology.
	 */
	public ReceivingItem() {
		
	}
	
	/**
	 * Function will be utilized in order to provide the ability to remain
	 * the field of Walmart Item Number, which is associated with the Master
	 * Item Domain, to Receiving Item Number.  Thus return back to the calling
	 * method the Receiving Item Number to the calling method.
	 * 
	 * @return Receiving Item Number
	 */
	public Long	getReceivingItemNumber() {
		return(this.receivingItemNumber);
	}
	
	/**
	 * Function will be utilized in order to return back to the calling method
	 * the Receiving Item, Item Name, which is present within the Item Canonical 
	 * Model which is transmitted from the Enterprise Master Item.
	 * 
	 * @return Item Name
	 */
	public String getItemName() {
		return(this.itemName);
	}
	
	/**
	 * Function will be utilized in order to return back to the calling method 
	 * the Receiving Item, Short Description, which is present within the Item
	 * Canonical Model which is transmitted from the Enterprise Master Item.
	 * 
	 * @return Short Description
	 */
	public String getShortDescription() {
		return(this.shortDescription);
	}
	
	/**
	 * Function will be utilized in order to provide the ability to override the
	 * current Receiving Item Number based on being supplied a Walmart Item Number,
	 * 
	 * @param walmartItemNumber
	 */
	public void setReceivingItemNumber(Long walmartItemNumber) {
		this.receivingItemNumber = walmartItemNumber;
	}
	
	/**
	 * Function will be utilized in order to provide the ability to override the current
	 * value of the Item Name based on the supplied Item Name value which is present
	 * from the calling method.
	 * 
	 * @param ItemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	/**
	 * Function will be utilized in order to provide the ability to override the current
	 * value of the Item Short Description based on the supplied Short Description value
	 * that is present from the calling method.
	 * 
	 * @param shortDescription
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
}
