package com.walmart.move.event.item;

/**
 * Interface will be utilized in order to provide the ability to reference those
 * configurations which are needed at runtime for a given event processor,
 * 
 * @author amguist
 *
 */
public interface IProcessConfig {

	/**
	 * Function will be utilized in order to provide the ability to retrieve
	 * back to the calling method the end point ( Zookeeper End Point ) which
	 * a Kafka Spout is to be linked to for consuming messages.
	 * 
	 * @return Zookeeper Host
	 */
	String	getZookeeperHost();
	
	/**
	 * Function will be utilized in order to retrieve back to the calling method
	 * the Topic which the Processor is to consume messages from thus allowing for
	 * the ability to receive those messages.
	 * 
	 * @return Listening Topic For Kafka Spout
	 */
	String	getListeningTopic();
	
	/**
	 * Function will be utilized in order to retrieve back to the calling method
	 * the Number Of Threads that are to be utilized within a given Topic thus
	 * allowing for the ability to do parallel processing.
	 * 
	 * @return
	 */
	Integer	getNumberOfWorkers();
	
	/**
	 * Function will return back to the calling method the topic which the Item
	 * is to be published for other products to receive the transformation from the
	 * Master Item.
	 * 
	 * @return Move Item Topic
	 */
	String	getMoveItemTopic();
	
	/**
	 * Function will return back to the calling method the list of possible 
	 * Kafka Message Brokers which are required for publishing messages from
	 * within the Next Generation Supply Chain space.
	 * 
	 * @return Kafka Broker List
	 */
	String	getKafkaBrokerList();
	
	/**
	 * Function will return back to the calling method the value of the
	 * configured Request Required Acknowledgement which will be utilized 
	 * in order to determine if acknowledgments are required or not.
	 * 
	 * @return Request Required Acknowledgment
	 */
	Short	getRequestRequiredAcks();
	
	/**
	 * Function will be utilized in order to return back to the calling method
	 * the Serializer class which is to be utilized for performing transformation
	 * for the required Kafka format.
	 * 
	 * @return Kafka Serializer Class
	 */
	String getSerializerClass();
}
