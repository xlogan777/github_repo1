package com.util.nbc_common_framework;

import java.io.*;

import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
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
	private static String MainUTActivityTAG = "MainUTActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ut);
		
		String data = this.readJsonData();
		
		TextView tv = (TextView)findViewById(R.id.my_text_view);
		tv.setText(data);

		/*if (savedInstanceState == null) 
		{
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}
	
	/*
	 * this function will read json data from the assets folder in the project.
	 */
	private String readJsonData()
	{
		try
		{
			//1 get the asset mgr that allows to read files from asset folder.
			AssetManager am = this.getAssets();
			
			//2.open the input stream, for the file.
			InputStream is = am.open("my_contet_obj.json");
			
			//3 create buff to save read data.
			byte [] buff = new byte[1024];
			
			//4 create output buffer to save all read data.
			ByteArrayOutputStream out_buff = new ByteArrayOutputStream();
			
			//5 read in the data from the file, and save to byte stream.
			int data_read = 0;
			while( (data_read = is.read(buff, 0, 1024)) > 0)
			{
				out_buff.write(buff, 0, data_read);
			}
			
			//6 create string from byte array.
			String s = new String(out_buff.toByteArray());
			
			//7 create json obj reader with string
			JSONObject reader = new JSONObject(s);
			
			//create json obj from sys key name
			//get country field from this obj.				
			String fullTitle = reader.getString("fullTitle");
			
			//get json object from main key
			JSONObject metadata = reader.getJSONObject("metadata");
			String leadMediaThumbnail = metadata.getString("leadMediaThumbnail");
			String ans = "JM...full_title = "+fullTitle+" metadata.leadMediaThumbnail = "+leadMediaThumbnail;
			
			Log.d(MainUTActivityTAG, ans);
			return ans;
		}
		catch(Exception e)
		{
			Log.d(MainUTActivityTAG, e.getMessage());
		}
		
		return null;
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
