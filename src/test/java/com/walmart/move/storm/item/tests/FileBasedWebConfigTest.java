package com.walmart.move.storm.item.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.walmart.move.event.item.FileBasedWebConfig;

/**
 * Class will be utilized in order to perform the necessary testing that is required 
 * for within the File Based Web Configuration class, which is utilized within the 
 * Event Processor for initializing those key features.
 * 
 * @author amguist
 *
 */
public class FileBasedWebConfigTest {

	/*
	 * Class Member Variables
	 */
	
	/**
	 * Test case will be utilized in order to test the normal configuration
	 * process which retrieves from application.properties based on the 
	 * type of environment which is being referenced.
	 */
	@Test
	public void filedBasedConfigTest() throws Exception {
		FileBasedWebConfig config = new FileBasedWebConfig();
		
		assertNotNull(config.getZookeeperHost());
		assertNotNull(config.getListeningTopic());
		assertNotNull(config.getNumberOfWorkers());
		assertNotNull(config.getMoveItemTopic());
		assertNotNull(config.getKafkaBrokerList());
		assertNotNull(config.getRequestRequiredAcks());
		assertNotNull(config.getSerializerClass());
		
		
		assertEquals(config.getZookeeperHost(), "localhost:2181");
		assertEquals(config.getListeningTopic(), "US.06067.TEST");
		assertEquals(config.getNumberOfWorkers(), new Integer(2));
		assertEquals(config.getMoveItemTopic(), "US.06067.MOVE.ITEM");
		assertEquals(config.getKafkaBrokerList(), "localhost:9092");
		assertEquals(config.getRequestRequiredAcks(), new Short((short)1));
		assertEquals(config.getSerializerClass(), "kafka.serializer.StringEncoder");
	}
}
