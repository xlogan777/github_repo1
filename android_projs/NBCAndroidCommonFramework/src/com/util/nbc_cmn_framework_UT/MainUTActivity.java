package com.util.nbc_cmn_framework_UT;

import java.io.*;
import java.util.List;

import com.util.nbc_common_framework.R;
import com.util.nbc_data_layer.NBCDataParsingBase;
import com.util.nbc_data_layer.NBCDataParsingAsJson;
import com.util.nbc_data_layer.ParsingInputParams;
import com.util.nbc_data_layer.SqliteDBAbstractIface;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.GalleryContentTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.GalleryContentTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.RelatedItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.RelatedItemsTableDao;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class MainUTActivity extends ActionBarActivity 
{
	private String MainUTActivityTAG = "MainUTActivity";
	
	//class member for db iface with green dao.
	private SqliteDBAbstractIface dbIface;
	
	//test class vars.
	private TextView tv;
	private DaoSession daoSession;
	private AssetManager asset_mgr;
	
	//initiate parsing of json obj in asset folder for content obj.        
    NBCDataParsingBase parse_data = new NBCDataParsingAsJson();
   	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ut);
		
		tv = (TextView)findViewById(R.id.my_text_view);
		tv.setText("");

		//create specific db iface implementation via factory method.
		//this will also do the initialization of the iface.
		dbIface = SqliteDBAbstractIface.createSqliteIface
					(this, "NBC_Content_DB_Test.db", null, SqliteDBAbstractIface.T_Session_Type.E_GREEN_DAO);

		//get the session obj and cast to specific one.
		//should be getting session type and then cast to specific one...
		daoSession = (DaoSession)dbIface.getDBSession();
                        
        //get the asset manager and load the json file to it.
        asset_mgr = getAssets();
        
        try
        {
        	long sleep_time = 2000;//2 secs
        	
        	//perform the unit test for all 3 types of data streams.
        	//this will do the insertion and updates of data needed.
        	this.unitTestContentData();
        	this.unitTestRelatedItemsContentData();
        	this.unitTestGalleryContentData();
        	Thread.sleep(sleep_time);
        	
        	//this will show the data using only the dao..load all the data 
        	//using all the relationships from the dao...and display it.
        	this.displayAllContentData();
        	Thread.sleep(sleep_time);
        	
        	this.displayAllRelatedItemData();
        	Thread.sleep(sleep_time);
        	
        	this.displayAllGalleryItemData();
        	Thread.sleep(sleep_time);
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
	
	private void displayAllContentData()
	{
		Log.d(MainUTActivityTAG, "JM...display content data");	
	}
	
	private void displayAllRelatedItemData()
	{
		Log.d(MainUTActivityTAG, "JM...display related items data");
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
		List<Object> gallery_items = dbIface.getContentDataAsList(cms_id);
		tv.setText("");
		
		for(Object obj : gallery_items)
		{
			GalleryContentTable gct = (GalleryContentTable)obj;
			
			//print all the items here...
			
			
		}
	}
	
	/*
	 * this will do the unit testing for content_data, related_items, gallery content.
	 */
	private void unitTestContentData() throws Exception
	{
		String json_string = this.parseDataViaAssetFile("my_content_obj.json");
		
        //invoke specific parsing type by providing the parsing input params.
        ParsingInputParams pip = new ParsingInputParams(0, NBCDataParsingBase.T_BasicContentTypes.E_CONTENT_ITEM_TYPE);
        parse_data.parseAndStoreDataType(json_string, pip, dbIface);
        
        //get the content data using the content id. this loads a bean obj.
        long id = 253794761;
        ContentItemsTable content_items_table_bean = daoSession.getContentItemsTableDao().load(id);

        String log = "JM...cms id "+content_items_table_bean.getCmsID()+
        		" type = "+content_items_table_bean.getContentType() + " title of article = "+
        		content_items_table_bean.getContentItemDetailTable().getTitle();
        //log content item data.
        //display data saved from the parser and gotten from DB.
        Log.d(MainUTActivityTAG, log);
        
        //make a change to the title of the article..
        ContentItemDetailTable tmp2 = content_items_table_bean.getContentItemDetailTable();
        tmp2.setTitle("JIMBO TITLE NOW IN THE BEAN FOR CNT ITEM DETAILS POJO");
        daoSession.getContentItemDetailTableDao().insertOrReplace(tmp2);

        //display that change from the DAO. by getting the updated bean.
        ContentItemsTable tmp_bean = daoSession.getContentItemsTableDao().load(id);
        
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
        ParsingInputParams pip = new ParsingInputParams(0, NBCDataParsingBase.T_BasicContentTypes.E_RELATED_ITEM_TYPE);
        parse_data.parseAndStoreDataType(json_string, pip, dbIface);
        
        //get the related item list using the parent cms id.. 
        long cms_id = 253794761;
        String log = "";
        
        //get this table dao
        RelatedItemsTableDao dao = this.daoSession.getRelatedItemsTableDao();
        
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
        ParsingInputParams pip = new ParsingInputParams(cms_id, NBCDataParsingBase.T_BasicContentTypes.E_GALLERY_ITEM_TYPE);
        parse_data.parseAndStoreDataType(json_string, pip, dbIface);
        
        //get this table dao
        GalleryContentTableDao dao = this.daoSession.getGalleryContentTableDao();
        
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
