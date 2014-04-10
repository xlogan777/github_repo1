package com.example.helloworldandroid;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * class to support intent demos.
 *
 */
public class IntentDemoActivity extends ActionBarActivity 
{

	private static final String MyTag = "IntentDemoActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intent_demo);
		
		Log.d(MyTag, "Kick off the intent demo stuff...");

//this is the intent demo area...
		
		//find the component for the browser using the id.
		Button startBrowser = (Button) findViewById(R.id.start_browser);
		
		//setup a listening via anonymous class to invoke the built in browser..
		startBrowser.setOnClickListener
		( new View.OnClickListener() 
			{
				public void onClick(View view) 
				{
					//implicit intent obj, that will setup the action/uri approach to let the intent resolver
					//figure out what resouce to call..in out case its the built in brower with the following url.
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://www.cnn.com"));					
					startActivity(intent);//start the activity with this intent.
				}
			}
		);
		
		//this is the same as above except for it is for the phone.
		Button startPhone = (Button) findViewById(R.id.start_phone);
		
		startPhone.setOnClickListener
		( new View.OnClickListener() 
			{
				public void onClick(View view) 
				{
					//setup the phone actions to allow for the phone to be used.
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("tel:7181234567"));
					startActivity(intent);//start the activity with this intent.
				}
			}
		);

//this is the intent demo area...
		
//this is the intent with filters demo area..
		
		// First intent to use ACTION_VIEW action with correct data
		Button startBrowser_a = (Button) findViewById(R.id.start_browser_a);
		
		startBrowser_a.setOnClickListener
		( new View.OnClickListener() 
			{
				//calling this activity with this intent object asks the user to pick between internal 
				//browser and custom activity.
				public void onClick(View view) 
				{
					Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));
					startActivity(i);
				}
			}
		);
		
		// Second intent to use LAUNCH action with correct data
		Button startBrowser_b = (Button) findViewById(R.id.start_browser_b);
		
		startBrowser_b.setOnClickListener
		( new View.OnClickListener() 
			{
				//this will directly call the custom activity...since it is using a specific action not defined
				//system wide for android os.
				public void onClick(View view) 
				{
					Intent i = new Intent("com.example.intentdemo.LAUNCH", Uri.parse("http://www.example.com"));
					startActivity(i);
				}
			}
		);
		
		// Third intent to use LAUNCH action with incorrect data
		Button startBrowser_c = (Button) findViewById(R.id.start_browser_c);
		
		startBrowser_c.setOnClickListener
		( new View.OnClickListener() 
			{
				//this will try to invoke an activity but the data is https, and we setup data type to be
				//http only...so it raises an exception.
				public void onClick(View view) 
				{
					Intent i = new Intent("com.example.intentdemo.LAUNCH", Uri.parse("https://www.example.com"));
					startActivity(i);
				}
			}
		);
//this is the intent with filters demo area..		
		
	}
	
//UI area demos code to kick off the activity for these demos.	
	/**
	 * 
	 * 
	 */
	public void onClickStartUISamplelayoutDemos(View view)
	{
		Log.d(MyTag, "call the new activity to allow to render diff layouts.");
		String input = ((EditText)this.findViewById(R.id.textInputlayout1)).getText().toString();
		
		//create intent for specific component, with this class as the context.
		//base intent object that allows for more customization later.
		Intent intent = new Intent(this, UserIfaceLayoutActivity.class);
		
		//this is a name/value pair of data to be used by the new activity class being started.
		intent.putExtra("editField", input);
		
		//this call below does the same thing...by gettting the base context.
		//Intent intent2 = new Intent(this.getBaseContext(), UserIfaceLayoutActivity.class);
		
	    startActivity(intent);//start new activity with intent obj tied to a specific class.	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.intent_demo, menu);
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
