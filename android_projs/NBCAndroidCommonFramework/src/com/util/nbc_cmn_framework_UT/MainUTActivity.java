package com.util.nbc_cmn_framework_UT;

import java.io.*;

import com.util.nbc_common_framework.R;
import com.util.nbc_data_layer.NBCDataBaseHelper;
import com.util.nbc_data_layer.NBCDataParsingAsJson;
import com.util.nbc_data_layer.NBCDataParsingBase;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTable;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoMaster;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoMaster.DevOpenHelper;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
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
	
	//these are the class members that are from green dao. 
    private DaoMaster daoMaster;//this holds the DB connection
    private DaoSession daoSession;//this holds caching for all the entities for a particular session.    
    private DevOpenHelper helper;//helper obj that creates the db connection and provides the db connection.
    
    private SQLiteDatabase db;//db connection obj
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ut);
		
		TextView tv = (TextView)findViewById(R.id.my_text_view);
		tv.setText("");
				
		//create a new helper obj that does most of the DB initialization.
        helper = new DaoMaster.DevOpenHelper(this, "NBC_Content_DB_Test.db", null);
        
        //get reference to writable DB.
        db = helper.getWritableDatabase();
        
        //get master obj as entry point for green dao.
        daoMaster = new DaoMaster(db);
        
        //get session obj for schema created previously.
        daoSession = daoMaster.newSession();
                
        //create tables now if they don't exists, if it does
        //then it wont create it again..
        DaoMaster.createAllTables(db, true);
        
        //ALSO this helper version will drop all tables and create then when
        //using DevOpenHelper class, supposed to be used for dev only.
                
        //get the asset manager and load the json file to it.
        AssetManager asset_mgr = getAssets();
        
        try
        {
        	//open file from asset folder.
	        InputStream input_stream = asset_mgr.open("my_content_obj.json");
	        	        
	        //initiate parsing of json obj in asset folder for content obj.        
	        NBCDataParsingAsJson parse_json = new NBCDataParsingAsJson();
	        
	        //read data from input stream
	        String json_string = parse_json.readDataFromInputStream(input_stream);
	        	        
	        //invoke specific parsing type based on enum and save it to DB.
	        parse_json.parseAndStoreDataType(json_string, NBCDataParsingBase.BasicContentTypes.CONTENT_ITEM_TYPE, daoSession);
	        
	        //get the content data using the content id. this loads a bean obj.
	        long id = 253794761;
	        ContentItemsTable content_items_table_bean = daoSession.getContentItemsTableDao().load(id);
	        	        
	        //log content item data.
	        //display data saved from the parser and gotten from DB.
	        Log.d(MainUTActivityTAG, "JM...cms id "+content_items_table_bean.getCmsID()+
	        		" type = "+content_items_table_bean.getContentType() + " title of article = "+
	        		content_items_table_bean.getContentItemDetailTable().getTitle());
	        
	        //make a change to the title of the article..
	        ContentItemDetailTable tmp2 = content_items_table_bean.getContentItemDetailTable();
	        tmp2.setTitle("JIMBO TITLE NOW IN THE BEAN FOR CNT ITEM DETAILS POJO");
	        daoSession.getContentItemDetailTableDao().insertOrReplace(tmp2);
	        
	        //save the entity bean back to DB now...with either insert or replace. via bean obj.
	        //daoSession.getContentItemsTableDao().insertOrReplace(content_items_table_bean);
	        	        
	        //display that change from the DAO. by getting the updated bean.
	        ContentItemsTable tmp_bean = daoSession.getContentItemsTableDao().load(id);
	        
	        //display results.
	        Log.d(MainUTActivityTAG, "JM...cms id "+tmp_bean.getCmsID()+
	        		" type = "+tmp_bean.getContentType() + " title of article = "+
	        		tmp_bean.getContentItemDetailTable().getTitle());
	        
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
