package com.util.mydao_cmn_framework_UT;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.util.mydao_data_layer.CommonUtils;
import com.util.mydao_data_layer.DataParsingAsJson;
import com.util.mydao_data_layer.DataParsingBase;
import com.util.mydao_data_layer.ParsingInputParams;
import com.util.mydao_data_layer.SqliteDBAbstractIface;
import com.util.mydao_data_layer.GreenDaoSrcGen.ContentItemDetailTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.ContentItemsTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.DaoSession;
import com.util.mydao_data_layer.GreenDaoSrcGen.GalleryContentTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.GalleryContentTableDao;
import com.util.mydao_data_layer.GreenDaoSrcGen.RelatedItemsTable;
import com.util.mydao_data_layer.GreenDaoSrcGen.RelatedItemsTableDao;
import com.util.mydao_data_layer.dataTypes.*;
import com.util.mydao_network_layer.IntentConstants;
import com.util.mydao_network_layer.NetworkContentService;
import com.util.mydao_network_layer.NetworkProcessing;
import com.util.mydao_common_framework.*;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.os.Build;

public class MainUTActivity extends ActionBarActivity 
{
	private String MainUTActivityTAG = "MainUTActivity";
	
	//class member for db iface with green dao.
	private SqliteDBAbstractIface dbIface;
	
	//test class vars.
	private TextView tv;
	//private DaoSession daoSession;
	private AssetManager asset_mgr;
	
	private ResponseReceiver responseReceiver = new ResponseReceiver();
	
	//initiate parsing of json obj in asset folder for content obj.        
    DataParsingBase parse_data = new DataParsingAsJson();
   	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ut);
		responseReceiver.setMainActivity(this);
		Bundle bundle = getIntent().getExtras();
		
		if(bundle != null )
		{
			String mm = bundle.getString("ACTION");
			if(mm.length() > 0)
			{
		    	//get the video view...from the layout file.
				VideoView vidView = (VideoView)findViewById(R.id.myVideo);
				
				Uri uri = Uri.parse(mm);
				vidView.setVideoURI(uri);
				Log.d(MainUTActivityTAG, "JM..got ready to play the video file...");
				//vidView.setVideoPath(bufferFile.getAbsolutePath());
				
				//add a media controller with this activity in the constructor.
				MediaController vidControl = new MediaController(this);
				
				//set the anchor for the video view component to the media controller.
				vidControl.setAnchorView(vidView);
				
				//set the media control to the video view component.
				vidView.setMediaController(vidControl);
				
				//start the video stream..with media controller functionality.
				vidView.start();
				
				return;
			}
		}
			
		//test1();
		
		
		
//		File bufferFile = null;
//		try
//		{
//			URL url = new URL(vidAddress);
//			//URL url = new URL("http://www.android.com/");
//		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//		   
//			
//            
////            //Open a connection to that URL.
////            URLConnection ucon = url.openConnection();
////
////            //this timeout affects how long it takes for the app to realize there's a connection problem
////            ucon.setReadTimeout(5000);
////            ucon.setConnectTimeout(30000);
//
//
//            //Define InputStreams to read from the URLConnection.
//          ]  // uses 3KB download buffer
//            InputStream is =urlConnection.getInputStream();
//			bufferFile = File.createTempFile("test", "mp4");
//	
//	        BufferedOutputStream bufferOS = new BufferedOutputStream(
//	                new FileOutputStream(bufferFile));
//	        
//	        int numRead = 0;
//	        byte[] buffer = new byte[1024];
//	        while ((numRead = is.read(buffer, 0,buffer.length )) > 0) {
//	
//	            bufferOS.write(buffer, 0, numRead);
//	            bufferOS.flush();
//	        }
//		}
//		catch(Exception e)
//		{
//			Log.d(MainUTActivityTAG, "JM..got error = "+e.getMessage());
//		}
		

		//create a uri from the url...
		//String vidAddress = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
//		Uri vidUri = Uri.parse(vidAddress);
//		
//		
//		//set the uri in the video view component.
//		//vidView.setVideoURI(vidUri);
		
		
		
		
				
		
		//tv = (TextView)findViewById(R.id.my_text_view);
		//tv.setText("");
//		
//		
//		//create specific db iface implementation via factory method.
//		//this will also do the initialization of the iface.
//		CommonUtils.createSqliteIface
//		(this.getApplicationContext(), "Content_DB_Test.db", null, SqliteDBAbstractIface.T_Session_Type.E_GREEN_DAO);
//		
//		dbIface = CommonUtils.getDBIface();
//				
//		//get the session obj and cast to specific one.
//		//should be getting session type and then cast to specific one...
//		//daoSession = (DaoSession)dbIface.getDBSession();
//                        
//        //get the asset manager and load the json file to it.
//        //asset_mgr = getAssets();
//        
//        //setup intent filter to a specific action, and tie the receiver to it.
        IntentFilter mStatusIntentFilter = 
        		new IntentFilter(IntentConstants.CONTENT_INTENT_SVC_BROADCAST_ACTION);
//        
//        //register local receiver with intent filter
        LocalBroadcastManager.getInstance(this).registerReceiver
        (
         responseReceiver,
         mStatusIntentFilter
        );
//        
        try
        {
////        	long sleep_time = 2000;//2 secs
////        	
////        	//perform the unit test for all 3 types of data streams.
////        	//this will do the insertion and updates of data needed.
////        	this.unitTestContentData();
////        	this.unitTestRelatedItemsContentData();
////        	this.unitTestGalleryContentData();
////        	
////        	Thread.sleep(sleep_time);
////        	
////        	//this will show the data using only the dao..load all the data 
////        	//using all the relationships from the dao...and display it.
////        	this.displayAllContentData();
////        	Thread.sleep(sleep_time);
////        	
////        	this.displayAllRelatedItemData();
////        	Thread.sleep(sleep_time);
////        	
////        	this.displayAllGalleryItemData();
////        	Thread.sleep(sleep_time);
//        	
//        	//call the intent svc.
//        	
        	long cnt_id = 253794761;
        	sendMsgToIntentSvc(cnt_id);
        }
        catch(Exception e)
        {
        	Log.d(MainUTActivityTAG, "JM..got error = "+e.getMessage());
        }

		/*if (savedInstanceState == null) 
		{
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}
	
	/*
	 * read in the m3u file, that contains many mp3 url files..and return an 
	 * array list of the files. this will be used by the media player class.
	 */
	public ArrayList<String> readURLs(String url) 
	{   
		ArrayList<String> list_urls = null;
		
        try
        {
        	//create url connection obj to the url for the m3u file.
            URL urls = new URL(url);
            
           //open the stream to the m3u file with 
            //buffered reader to read it as a file list.
            BufferedReader in = 
            	new BufferedReader(
            		new InputStreamReader(urls.openStream()));
            
            String str;
            
            //create array list to contain all the list of files video files
            list_urls = new ArrayList<String>();
            
            //save all the list of files to array list.
            while ((str = in.readLine()) != null) 
            {
            	//check to see if this line contains the http protocol
            	//if so then add it to the array list.
            	if(str.contains("http://"))
            		list_urls.add(str);
            }
            
            //close buffer reader
            in.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        //return the list of urls as array list.
        return list_urls;
    }
	
	/*
	 * function that sends the command of the url, that we need to download.
	 */
	private void sendMsgToIntentSvc(long cntId)
	{
		//url.
		//String url = "cntId";
		
		//create intent with this activity as the sending activity, and the calling service.
		Intent mServiceIntent = new Intent(this, NetworkContentService.class);		
		
		//provide the content id.
		mServiceIntent.putExtra("contentId", ""+cntId);
		
		//start the service
		startService(mServiceIntent);
		
		Log.d(MainUTActivityTAG, "JM...started intent svc to load data via http.");
	}
	
	private void displayAllContentData()
	{
		Log.d(MainUTActivityTAG, "JM...display content items data");
		
		long cms_id = 253794761;
		
		//create query and get the data from the cnt table
		CntTypeIface cnt_items =  dbIface.getContentData(cms_id);
		tv.setText("");
		
		String final_string = "";

		ContentItemsTable cnt = (ContentItemsTable)cnt_items;
		final_string += cnt.getDebugString();
		
		tv.setText(final_string);
	}
	
	private void displayAllRelatedItemData()
	{
		Log.d(MainUTActivityTAG, "JM...display related items data");
		
		long cms_id = 253794761;
		
		//create query and get the data from the related table.
		List<RelCntItemTypeIface> rel_items = null; 
		rel_items = dbIface.getRelatedContentDataAsList(cms_id);
		tv.setText("");
		
		String final_string = "";
		int size = rel_items.size();
		Log.d(MainUTActivityTAG, "JM...size = "+size);
		
		//loop over all the items here..and display them..
		for(RelCntItemTypeIface obj1 : rel_items)
		{
			RelatedItemsTable rct = (RelatedItemsTable)obj1;
			final_string += rct.getDebugString();
		}
		
		tv.setText(final_string);
	}
	
	/*
	 * display all the gallery data here with their unique img data associated with the 
	 * gallery items.
	 */
	private void displayAllGalleryItemData()
	{
		Log.d(MainUTActivityTAG, "JM...display gallery content data");
		long cms_id = 237503121;
		
		//create query and get the data from the gallery table.
		List<GalleryCntTypeIface> gallery_items = null; 
		gallery_items = dbIface.getGalleryContentDataAsList(cms_id);
		tv.setText("");
		
		String final_string = "";
		int size = gallery_items.size();
		Log.d(MainUTActivityTAG, "JM...size = "+size);
		
		//loop over all the items here..and display them..
		for(GalleryCntTypeIface obj1 : gallery_items)
		{
			GalleryContentTable gct = (GalleryContentTable)obj1;
			final_string += gct.getDebugString();			
		}
		
		tv.setText(final_string);
	}
	
	/*
	 * this will do the unit testing for content_data, related_items, gallery content.
	 */
	private void unitTestContentData() throws Exception
	{
		String json_string = this.parseDataViaAssetFile("my_content_obj.json");
		
        //invoke specific parsing type by providing the parsing input params.
        ParsingInputParams pip = new ParsingInputParams(0, DataParsingBase.T_BasicContentTypes.E_CONTENT_ITEM_TYPE);
        parse_data.parseAndStoreDataType(json_string, pip, dbIface);
        
        //get the content data using the content id. this loads a bean obj.
        long id = 253794761;
        ContentItemsTable content_items_table_bean = null; //= daoSession.getContentItemsTableDao().load(id);

        String log = "JM...cms id "+content_items_table_bean.getCmsID()+
        		" type = "+content_items_table_bean.getContentType() + " title of article = "+
        		content_items_table_bean.getContentItemDetailTable().getTitle();
        //log content item data.
        //display data saved from the parser and gotten from DB.
        Log.d(MainUTActivityTAG, log);
        
        //make a change to the title of the article..
        ContentItemDetailTable tmp2 = content_items_table_bean.getContentItemDetailTable();
        tmp2.setTitle("JIMBO TITLE NOW IN THE BEAN FOR CNT ITEM DETAILS POJO");
        //daoSession.getContentItemDetailTableDao().insertOrReplace(tmp2);

        //display that change from the DAO. by getting the updated bean.
        ContentItemsTable tmp_bean  =null;// = daoSession.getContentItemsTableDao().load(id);
        
        log = "JM...cms id "+tmp_bean.getCmsID()+
        		" type = "+tmp_bean.getContentType() + " title of article = "+
        		tmp_bean.getContentItemDetailTable().getTitle();
        
        //display results.
        Log.d(MainUTActivityTAG, log);
        
        tv.setText(tv.getText()+"\n\n"+log);
	}
	
	private void unitTestRelatedItemsContentData() throws Exception
	{
		String json_string = this.parseDataViaAssetFile("related_item.json");
		
        //invoke specific parsing type by providing the parsing input params.
        ParsingInputParams pip = new ParsingInputParams(0, DataParsingBase.T_BasicContentTypes.E_RELATED_ITEM_TYPE);
        parse_data.parseAndStoreDataType(json_string, pip, dbIface);
        
        //get the related item list using the parent cms id.. 
        long cms_id = 253794761;
        String log = "";
        
        //get this table dao
        RelatedItemsTableDao dao = null;// = this.daoSession.getRelatedItemsTableDao();
        
        //do a query against the parent id to get a list of related items tied to this parent id.
        List<RelatedItemsTable> related_items = 
        		dao.queryBuilder().where(RelatedItemsTableDao.Properties.ParentCmsID.eq(cms_id)).list();
        
        String final_str = "";
        //iterate over the list of hits here.
        for(RelatedItemsTable my_related_item : related_items)        	
        {
        	log = "JM...parent id = "+my_related_item.getParentCmsID()+ " , relcmsId = "+my_related_item.getRelCmsID()+
        			" title = "+my_related_item.getTitle();
        	
        	Log.d(MainUTActivityTAG, log);
        	final_str+=log;
        }
        
        tv.setText(tv.getText()+"\n\n"+final_str);
	}
	
	private void unitTestGalleryContentData() throws Exception
	{
		String json_string = this.parseDataViaAssetFile("gallery_contentId=237503121.json");
		
		long cms_id = 237503121;
		
        //invoke specific parsing type by providing the parsing input params.
        ParsingInputParams pip = new ParsingInputParams(cms_id, DataParsingBase.T_BasicContentTypes.E_GALLERY_ITEM_TYPE);
        parse_data.parseAndStoreDataType(json_string, pip, dbIface);
        
        //get this table dao
        GalleryContentTableDao dao=null;// = this.daoSession.getGalleryContentTableDao();
        
        //do a query against the parent id to get a list of related items tied to this parent id.
        List<GalleryContentTable> gallery_items = 
        		dao.queryBuilder().where(GalleryContentTableDao.Properties.GalCmsID.eq(cms_id)).list();
        
        String final_str = "";
        String log = "";
        
        //iterate over the list of hits here.
        for(GalleryContentTable my_gallery_item : gallery_items)        	
        {
        	log = "JM...gallery id = "+my_gallery_item.getGalCmsID()+" index = "+my_gallery_item.getImgIndex();
        	//get the image data for this item...
        	
        	Log.d(MainUTActivityTAG, log);
        	final_str+=log;
        }
        
        tv.setText(tv.getText()+"\n\n"+final_str);
	}
	
	/*
	 * this will read in the asset file and return a json stringby simulating the stream process via
	 * the asset mgr.
	 */
	private String parseDataViaAssetFile(String inputFileName) throws Exception
	{
		//open file from asset folder.
        InputStream input_stream = asset_mgr.open(inputFileName);
        
        //read data which returns a byte array ostream obj. and get the raw array to create a string from it.
        String json_string = new String(parse_data.readDataFromInputStream(input_stream).toByteArray());
        
        //return the string to the caller.
        return json_string;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_ut, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * this class handles the intent sent from the intent svc...
	 */
	private class ResponseReceiver extends BroadcastReceiver
	{   
		private Context mainact;
		
	    public ResponseReceiver() 
	    {
	    	
	    }
	    
	    public void setMainActivity(Context context)
	    {
	    	this.mainact = context;
	    }
	    // Called when the BroadcastReceiver gets an Intent it's registered to receive
	    @Override
	    public void onReceive(Context context, Intent intent) 
	    {
	    	//tv.setText("");
	    	String fname = intent.getStringExtra(IntentConstants.DB_STATUS_CONTENT_INTENT_SVC);
	    	String int_str = intent.getAction() ;
	    	Log.d(MainUTActivityTAG, int_str);
	    	//tv.setText(int_str+"in the receiver part.");
	    	
	    	
	    	Bundle bundle = new Bundle();
	    	bundle.putString("ACTION", fname);
	        Intent intent2 = new Intent(mainact,MainUTActivity.class);
	        intent2.putExtras(bundle);
	        mainact.startActivity(intent2);
	    }
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment 
	{

		public PlaceholderFragment() 
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_main_ut, container, false);
			return rootView;
		}
	}
}
