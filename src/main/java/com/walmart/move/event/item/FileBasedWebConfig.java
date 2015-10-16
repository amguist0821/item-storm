package com.walmart.move.event.item;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Class is needed in order to add application level properties at runtime, thus
 * allowing for easier configuration of application preferences.
 * 
 * @author amguist
 *
 */
@Singleton
@Named
public class FileBasedWebConfig implements IProcessConfig {

	/*
	 * Class Member Variables
	 */
	private	Configuration	config		=		null;
	
	/**
	 * Function is defined as being the default class constructor that is to be 
	 * utilized in order to initialize a given product to it's initial state of
	 * being.
	 * 
	 * @throws ConfigurationException
	 */
	@Inject
	public FileBasedWebConfig() throws ConfigurationException {
		String	envStatus	=	System.getProperty("envStatus", "dev");
		String	propFilePath =	envStatus + "/app.properties";
		
		CompositeConfiguration	cfg	= new CompositeConfiguration();
		cfg.addConfiguration(new PropertiesConfiguration(propFilePath));
		
		this.config = cfg;
	}
	
	/**
	 * Function will be utilized in order to provide the ability to retrieve
	 * back to the calling method the end point ( Zookeeper End Point ) which
	 * a Kafka Spout is to be linked to for consuming messages.
	 * 
	 * @return Zookeeper Host
	 */
	public String	getZookeeperHost() {
		return(config.getString("zookeeper.host", "localhost:2181"));
	}
	
	/**
	 * Function will be utilized in order to retrieve back to the calling method
	 * the Topic which the Processor is to consume messages from thus allowing for
	 * the ability to receive those messages.
	 * 
	 * @return Listening Topic For Kafka Spout
	 */
	public String	getListeningTopic() {
		return(config.getString("listening.topic", "US.06067.ITEM"));
	}
	
	/**
	 * Function will be utilized in order to retrieve back to the calling method
	 * the Number Of Threads that are to be utilized within a given Topic thus
	 * allowing for the ability to do parallel processing.
	 * 
	 * @return
	 */
	public Integer	getNumberOfWorkers() {
		return(config.getInteger("num.workers", 2));
	}
	
	/**
	 * Function will return back to the calling method the topic which the Item
	 * is to be published for other products to receive the transformation from the
	 * Master Item.
	 * 
	 * @return Move Item Topic
	 */
	public String	getMoveItemTopic() {
		return(config.getString("move.item.topic", "US.06067.MOVE.ITEM"));
	}
	
	/**
	 * Function will return back to the calling method the list of possible 
	 * Kafka Message Brokers which are required for publishing messages from
	 * within the Next Generation Supply Chain space.
	 * 
	 * @return Kafka Broker List
	 */
	public String	getKafkaBrokerList() {
		return(config.getString("kafka.broker.list", "localhost:9092"));
	}
	
	/**
	 * Function will return back to the calling method the value of the
	 * configured Request Required Acknowledgement which will be utilized 
	 * in order to determine if acknowledgments are required or not.
	 * 
	 * @return Request Required Acknowledgment
	 */
	public Short	getRequestRequiredAcks() {
		return(config.getShort("kafka.acknowledgement", new Short((short)1)));
	}
	
	/**
	 * Function will be utilized in order to return back to the calling method
	 * the Serializer class which is to be utilized for performing transformation
	 * for the required Kafka format.
	 * 
	 * @return Kafka Serializer Class
	 */
	public String getSerializerClass() {
		return(config.getString("kafka.serializer.class", "kafka.serializer.StringEncoder"));
	}
}
