package com.example.helloworldandroid;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//testing...

public class MainActivity extends ActionBarActivity 
//public class MainActivity extends Activity
{
	//name to call out tag name
	private static final String MyTag = "MainActivity";
	
	/*
	 * all the functions are showing all the different types of callbacks
	 * for an android activity.
	 * 
	 * onCreate, onStart, onResume are called when the app is loaded for first time. in that order.
	 */
	
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//this below call is loading the layout type which is an xml file from /res/layout/activity_main.xml
		//this is the formal way of loading the layout with view objs..buttons, text fields..etc.
		//this is generally done in the onCreate method as such when the activity is first called.
		setContentView(R.layout.activity_main);
		
		//set the text view to string in strings.xml file.
		//setting the text in the window pane.
//		TextView msgTextView = (TextView) findViewById(R.id.text);
//		msgTextView.setText(R.string.jimbo_hello);
		
		Log.d(MyTag, ""+R.string.jimbo_hello);
				
		//logging to logcat.
		Log.d(MyTag, "hello world222!!!");
		Log.d(MyTag, "The onCreate() event");
	}

	/*
	 * onStart, onResume is called when app is brought back to main front (running) 
	 */
	
	/** Called when the activity is about to become visible. */
	@Override
	protected void onStart() 
	{
		super.onStart();
		Log.d(MyTag, "The onStart() event");
	}
	
	/** Called when the activity has become visible. */
	@Override
	protected void onResume() 
	{
		super.onResume();
		Log.d(MyTag, "The onResume() event");
	}
	
	/*
	 * onPause, onStop is called when you move away from the app, like pressing the call button.
	 * 
	 * onPause, onStop, onDestroy is called when u press button back to go to main screen of phone...it ends app lifecycle.
	 */
	
	/** Called when another activity is taking focus. */
	@Override
	protected void onPause() 
	{
		super.onPause();
		Log.d(MyTag, "The onPause() event");
	}
	
	/** Called when the activity is no longer visible. */
	@Override
	protected void onStop() 
	{
		super.onStop();
		Log.d(MyTag, "The onStop() event");
	}
	
	/** Called just before the activity is destroyed. */
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		Log.d(MyTag, "The onDestroy() event");
		
		//delete all the rows from the DB...here..since null is being passed in the whereAs part..first null.
		//this is part of shutdown.
		int rows = this.getContentResolver().delete(HelloContentProvider.CONTENT_URI, null, null);
		
		//this will get the specific content provider from the content resolver
		HelloContentProvider my_provider = 
				(HelloContentProvider)this.getContentResolver().acquireContentProviderClient(HelloContentProvider.CONTENT_URI).getLocalContentProvider();
		
		//drop table and close db connection.
		my_provider.closeDB();
		
		Log.d(MyTag, "JM...num rows deleted is = "+rows);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * this will show the starting and stopping of services...
	 */
	
	public void startService(View view)
	{
		//call base class start service and provide the service class from this main activity
		//provide it a Intent obj with the name of the service.
		//this is a explicit intent with specific class to handle this intent.
		super.startService(new Intent(this.getBaseContext(), HelloService.class));
	}

	public void stopService(View view)
	{
		//call base class stop service and provide the service class from this main activity
		//provide it a Intent obj with the name of the service.
		//this is a explicit intent with specific class to handle this intent.
		super.stopService(new Intent(this.getBaseContext(), HelloService.class));		
	}
	
	/**
	 * this will perform the broadcast intent testing
	 */
	public void broadcastIntent(View view)
	{
		//create intent obj
		Intent intent = new Intent();
		
		//set custom action
		//this is an implicit intent, doesn't name a specific component.
		//in this case it is handled by a broadcast receiver.
		intent.setAction("com.example.helloworldandroid.CUSTOM_INTENT");
		
		//start broadcast of custom intent.
		sendBroadcast(intent);
		
		Log.d(MyTag, "finished execution of broadcast intent...");
	}
	
	/**
	 * this will perform the content provider specific actions...
	 */
	
	//this function will add students to the db
	public void onClickAddName(View view) 
	{
		// Add a new student record
		//create a object to contain values from the UI.
		ContentValues values = new ContentValues();	
		
		//these will put into a map for the db field name, with the data from the UI..
		values.put(HelloContentProvider.NAME, ((EditText)findViewById(R.id.txtName)).getText().toString());
		values.put(HelloContentProvider.GRADE, ((EditText)findViewById(R.id.txtGrade)).getText().toString());
		
		//this will perform the insert in db via the content uri and the map of values from the UI.
		Uri uri = getContentResolver().insert(HelloContentProvider.CONTENT_URI, values);
				
		//this will display on the UI the new uri with the row number in the activity window.
		Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
	}
	
	//this function will get students from db
	public void onClickRetrieveStudents(View view) 
	{
		// Retrieve student records
		//returns a cursor object or null..this will call the ContentProvider query function via the uri, and return the
		//results in name sorted order by using the field [name] attrib from db.
		Cursor c = this.getContentResolver().query(HelloContentProvider.CONTENT_URI, null, null, null, "name");
		
		if (c != null) 
		{
			//move cursor to before the first row.
			c.moveToPosition(-1);
			
			//will move cursor to the next row, which should be the first row.
			while(c.moveToNext())
			{
				Toast.makeText(this,
						c.getString(c.getColumnIndex(HelloContentProvider._ID)) +
						", " + c.getString(c.getColumnIndex( HelloContentProvider.NAME)) +
						", " + c.getString(c.getColumnIndex( HelloContentProvider.GRADE)),
						Toast.LENGTH_SHORT).show();
			}
		}
		
		//close cursor obj.
		c.close();
	}
	
	/**
	 * 
	 * this will show the fragment demo stuff.
	 */
	public void onClickStartFragmentDemo(View view)
	{
		Log.d(MyTag,"hitting the fragment demo here...");
		
		//start new activity via intent object.
		//this is a explicit intent with specific class to handle this intent.
		Intent intent = new Intent(this, SampleFragmentActivity.class);
	    startActivity(intent);
	}
	
	/**
	 * 
	 * this will call another activity to do the demo for web browser and
	 * phone call test.
	 */
	public void onClickStartIntentDemos(View view)
	{
		Log.d(MyTag, "inside onClickStartIntentDemo");
		
		//call intent demo activity explicitly by setting up an intent obj explicitly.
		Intent intent = new Intent(this, IntentDemoActivity.class);
	    startActivity(intent);		
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
