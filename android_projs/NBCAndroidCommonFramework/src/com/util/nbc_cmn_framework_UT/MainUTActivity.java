package com.util.nbc_common_framework;

import java.io.*;

import org.json.JSONObject;

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
	private NBCDataBaseHelper helper = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_ut);

		Log.d(MainUTActivityTAG, "inside the onCreate");
		//create db.
		this.createDB();
		
		TextView tv = (TextView)findViewById(R.id.my_text_view);
		tv.setText("");

		/*if (savedInstanceState == null) 
		{
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}
	
	public void createDB()
	{
		//start db here.
		if(helper == null)
		{
			//get the application context since it spans more than this activity
			Context context = this.getApplicationContext();
			
			//create the DB now. save this in the bundle obj...
			helper = new NBCDataBaseHelper(context);
			
			//perform the db operations here..CRUD
			//helper.insert()
			//helper.update()
			//helper.delete()
			//helper.select()
		}
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