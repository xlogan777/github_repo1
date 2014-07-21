package com.util.nbc_network_layer;

/**
 * this class handles the retrieval of the json data for all content data.
 * it will also perform the insertion into the db layer.
 * it will also do the broadcast intent to allow for 
 * other components to use this db status updates as they wish.
 * 
 * author J.Mena 
 */

import java.io.InputStream;

import com.util.nbc_data_layer.NBCDataParsingAsJson;
import com.util.nbc_data_layer.NBCDataParsingBase;
import com.util.nbc_data_layer.ParsingInputParams;
import com.util.nbc_data_layer.SqliteDBAbstractIface;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class NetworkContentService extends IntentService
{
	//leaving this as the iface..not specific type..
	private NBCDataParsingBase parsingBase = new NBCDataParsingAsJson();
	
	//use db iface as needed.
	private SqliteDBAbstractIface dbIface;
	
	//constructor
	public NetworkContentService(String name, SqliteDBAbstractIface dbIface)
	{
		super(name);
		this.dbIface = dbIface;//save the db iface here.
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		String url = intent.getDataString();
		
		//TODO; use the intent to get the different 
		//url streams that this obj will use to make calls for..
		
		InputStream is = NetworkProcessing.HttpGetProcessing(url);		
		String json_data_content = parsingBase.readDataFromInputStream(is).toString();
		ParsingInputParams pip = new ParsingInputParams(0, NBCDataParsingBase.T_BasicContentTypes.E_CONTENT_ITEM_TYPE);
		parsingBase.parseAndStoreDataType(json_data_content, pip, dbIface);
		
		//create intent stating the status of the insert.
		Intent localIntent = new Intent("com.util.nbc_common_framework.network_layer.NetworkContentService")
	            .putExtra("StatusInsert", "Done With Insert to DB layer");
		
	    // Broadcasts the Intent to receivers in this app.
		//this will only broadcast to internal components of the app.
	    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

		
		//need to check to see if this content item is article type..if so then, pull down related items..
		
		//need to check if this item is gallery content type..if so then pull down the gallery json data.
		
//		is = NetworkProcessing.HttpGetProcessing("URL 2");
//		String json_data_related_items = parsingBase.readDataFromInputStream(is).toString();
//		pip = new ParsingInputParams(0, NBCDataParsingBase.T_BasicContentTypes.E_RELATED_ITEM_TYPE);
//		parsingBase.parseAndStoreDataType(json_data_related_items, pip, dbIface);
//		
//		long cms_id = 111;
//		is = NetworkProcessing.HttpGetProcessing("URL 3");
//		String json_data_gallery_items = parsingBase.readDataFromInputStream(is).toString();
//		pip = new ParsingInputParams(cms_id, NBCDataParsingBase.T_BasicContentTypes.E_GALLERY_ITEM_TYPE);
//		parsingBase.parseAndStoreDataType(json_data_gallery_items, pip, dbIface);
	}
}
