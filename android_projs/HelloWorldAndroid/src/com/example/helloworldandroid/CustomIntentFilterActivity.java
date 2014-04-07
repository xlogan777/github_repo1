package com.example.helloworldandroid;

import android.support.v7.app.ActionBarActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CustomIntentFilterActivity extends ActionBarActivity 
{
	private static final String MyTag = "CustomIntentFilterActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_intent_filter);
		Log.d(MyTag,"This activity got called since it passed all the filters from intent filter.");

		TextView label = (TextView) findViewById(R.id.show_data);
		Uri url = getIntent().getData();
		label.setText(url.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.custom_intent_filter, menu);
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
}
