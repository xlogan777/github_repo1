package com.util.mydao_network_layer;

/**
 * this class handles the retrieval of the json data for all content data.
 * it will also perform the insertion into the db layer.
 * it will also do the broadcast intent to allow for 
 * other components to use this db status updates as they wish.
 * 
 * author J.Mena 
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.util.mydao_data_layer.CommonUtils;
import com.util.mydao_data_layer.DataParsingAsJson;
import com.util.mydao_data_layer.DataParsingBase;
import com.util.mydao_data_layer.ParsingInputParams;
import com.util.mydao_data_layer.SqliteDBAbstractIface;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class NetworkContentService extends IntentService
{
	//leaving this as the iface..not specific type..
	private DataParsingBase parsingBase = new DataParsingAsJson();
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
		String fname = "";
		try {
            URL url = new URL("http://www.ebookfrenzy.com/android_book/movie.mp4");
			//URL url = new URL("http://nbclim-f.akamaihd.net/i/Prod/,NBCU_LM_VMS_-_WNBC/758/630/WNBC_000000003446911_med.mp4,.csmil/segment1_0_av.ts");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();           
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[1024];
            
            File bufferFile = File.createTempFile("test", "mp4");
	        BufferedOutputStream bufferOS = new BufferedOutputStream(
	                new FileOutputStream(bufferFile));
	        
	        int numRead = 0;
	        
	        while ((numRead = is.read(buffer, 0,buffer.length )) > 0) 
	        {
	
	            bufferOS.write(buffer, 0, numRead);
	            bufferOS.flush();
	        }
	        
	        is.close();
	        bufferOS.close();
	        fname = bufferFile.getAbsolutePath();
	        Log.e(LOGTAG, "JM..path = "+bufferFile.getAbsolutePath());
        } catch (IOException e) {
            Log.e("Abhan", "Error: " + e);
        }
		
		String cms_id = intent.getStringExtra("contentId");
		long cmsid = Long.parseLong(cms_id);
		
		//create the url to for the content item.
		//need to have the domain part abstracted.
		String url = 
		"http://www.nbcnewyork.com/apps/news-app/content/?contentId="+cms_id.trim();
		
		
		InputStream is = NetworkProcessing.HttpGetProcessing(url);
		
		String json_data_content = parsingBase.readDataFromInputStream(is).toString();
		//Log.d(LOGTAG, "JM...json data = "+json_data_content);
		
		
//		ParsingInputParams pip = new ParsingInputParams(0, NBCDataParsingBase.T_BasicContentTypes.E_CONTENT_ITEM_TYPE);
//		
//		SqliteDBAbstractIface db_iface = CommonUtils.getDBIface();
//		
//		//TODO: how do i get the ref to this obj for db mng perpective.
//		parsingBase.parseAndStoreDataType(json_data_content, pip, db_iface);
		
		//create intent stating the status of the insert.
		Intent localIntent = new Intent(IntentConstants.CONTENT_INTENT_SVC_BROADCAST_ACTION);
		localIntent.putExtra(IntentConstants.DB_STATUS_CONTENT_INTENT_SVC, fname);
		
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
