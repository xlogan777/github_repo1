package com.util.nbc_network_layer;

public class IntentConstants 
{
	//TODO: need to create the network status breakdown..for now tied both together.
	
	//this is the action to tie different status to a broadcast action.
	public static final String CONTENT_INTENT_SVC_BROADCAST_ACTION = 
			"com.util.nbc_network_layer.CONTENT_INTENT_SVC_BROADCAST_ACTION";
	
	//this will do the status tied to this action.
	public static final String DB_STATUS_CONTENT_INTENT_SVC = 
			"com.util.nbc_network_layer.DB_STATUS_CONTENT_INTENT_SVC";
	
	//success or failed for network, db processing.
	public static final String DB_SUCCESS = "DB_SUCCESS";
	public static final String DB_FAILED = "DB_FAILED";
}
