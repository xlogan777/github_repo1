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

import com.util.nbc_data_layer.CommonUtils;
import com.util.nbc_data_layer.NBCDataParsingAsJson;
import com.util.nbc_data_layer.NBCDataParsingBase;
import com.util.nbc_data_layer.ParsingInputParams;
import com.util.nbc_data_layer.SqliteDBAbstractIface;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class NetworkContentService extends IntentService
{
	//leaving this as the iface..not specific type..
	private NBCDataParsingBase parsingBase = new NBCDataParsingAsJson();
	private String LOGTAG = "NetworkContentService";
	
	//use this to call the base class constructor, to allow for the 
	//seutp of this obj.
	public NetworkContentService()
	{
		super("NetworkContentService");
	}
	
	/*
http://www.nbcnewyork.com/apps/news-app/content/?contentId=244736981

http://www.nbcnewyork.com/apps/news-app/content/related/?contentId=252039911

http://www.nbcnewyork.com/apps/news-app/content/gallery/?contentId=244827851
	 */

	@Override
	protected void onHandleIntent(Intent intent)
	{
		String cms_id = intent.getStringExtra("contentId");
		long cmsid = Long.parseLong(cms_id);
		
		//create the url to for the content item.
		//need to have the domain part abstracted.
		String url = 
		"http://www.nbcnewyork.com/apps/news-app/content/?contentId="+cms_id.trim();
		
		
		InputStream is = NetworkProcessing.HttpGetProcessing(url);
		
		String json_data_content = parsingBase.readDataFromInputStream(is).toString();
		Log.d(LOGTAG, "JM...json data = "+json_data_content);
		
		
//		ParsingInputParams pip = new ParsingInputParams(0, NBCDataParsingBase.T_BasicContentTypes.E_CONTENT_ITEM_TYPE);
//		
//		SqliteDBAbstractIface db_iface = CommonUtils.getDBIface();
//		
//		//TODO: how do i get the ref to this obj for db mng perpective.
//		parsingBase.parseAndStoreDataType(json_data_content, pip, db_iface);
		
		//create intent stating the status of the insert.
		Intent localIntent = new Intent(IntentConstants.CONTENT_INTENT_SVC_BROADCAST_ACTION);
		localIntent.putExtra(IntentConstants.DB_STATUS_CONTENT_INTENT_SVC, IntentConstants.DB_SUCCESS);
		
		Log.d(LOGTAG, "JM...all OK, send broadcast msg to receivers.");
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
