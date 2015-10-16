package com.walmart.move.storm.item.tests;

import backtype.storm.Constants;
import backtype.storm.tuple.Tuple;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class will be utilized in order to help in the creation of the Tuples that
 * are to be utilized for a given Bolt Test. 
 * 
 * @author amguist
 *
 */
public final class MockTupleHelpers {

	/**
	 * Function is defined as being the default class constructor which is to be
	 * utilized in order to initialize a new instance of MockTupleHelpers to
	 * it's initial state of being.  However, this class is just a utility class
	 * therefore, there is no need to worry about the initialization.
	 */
	private MockTupleHelpers() {
		
	}
	
	/**
	 * Function will return back to the calling method a new Tuple based on 
	 * those System Component Identifier and System Stream Identifier that
	 * are defined.
	 * 
	 * @return
	 */
	public static Tuple mockTickTuple() {
		return(mockTuple(Constants.SYSTEM_COMPONENT_ID, Constants.SYSTEM_TICK_STREAM_ID));
	}
	
	/**
	 * Function will be utilized in order to return an initialized Tuple that
	 * can be utilized within a given Storm Bolt.
	 * 
	 * @param componentId
	 * @param streamId
	 * 
	 * @return
	 */
	public static Tuple mockTuple(String componentId, String streamId) {
		Tuple tuple = mock(Tuple.class);
		when(tuple.getSourceComponent()).thenReturn(componentId);
		when(tuple.getSourceStreamId()).thenReturn(streamId);
		return(tuple);
	}
}
