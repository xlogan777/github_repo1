package com.example.helloworldandroid;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class UserIfaceLayoutActivity extends ActionBarActivity implements OnClickListener
{
	private static String MyTag = "UserIfaceLayoutActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		int layout_type = 0;		
		
		//this will get the bundle object from the intent with all the extras. 
		Bundle bundle = this.getIntent().getExtras();
		String inputVal = bundle.getString("editField");
		//this line below is another way of getting the data from intent obj w/o getting a bundle.
		//this.getIntent().getStringExtra("editField");this will directly get extra data from intent obj
		
		Log.d(MyTag,"JM..doing the UI sample demo..value for input text = "+inputVal);
		
		if(inputVal != null)
		{				
			if(inputVal.equalsIgnoreCase("Rel_layout"))		
				layout_type = R.layout.activity_user_iface_layout_relative;
			
			else if(inputVal.equalsIgnoreCase("Tab_layout"))		
				layout_type = R.layout.activity_user_iface_layout_table;
			
			else if(inputVal.equalsIgnoreCase("Abs_layout"))
				layout_type = R.layout.activity_user_iface_layout_absolute;
			
			else if(inputVal.equalsIgnoreCase("Fme_layout"))
				layout_type = R.layout.activity_user_iface_layout_frame;
			
			else if(inputVal.equalsIgnoreCase("list_view"))
				layout_type = R.layout.activity_user_iface_layout_list_view;
			
			else if(inputVal.equalsIgnoreCase("grid_view"))
				layout_type = R.layout.activity_user_iface_layout_grid_view;

			else//default is linear layout.
				layout_type = R.layout.activity_user_iface_layout;
			
			//set the content view so we can get any ui view objects from the UI iface contatiner.
			setContentView(layout_type);
			
			//perform what ever specific layout processing is needed after u set the content view.
			switch(layout_type)
			{
				case R.layout.activity_user_iface_layout_relative:
					performRelLayoutProcessing();
					break;
					
				case R.layout.activity_user_iface_layout:
					performLinearLayoutProcessing();
					break;

				//these are just the same processing, show the ui.
				case R.layout.activity_user_iface_layout_table:
				case R.layout.activity_user_iface_layout_absolute:				
				case R.layout.activity_user_iface_layout_frame:
					break;
					
				case R.layout.activity_user_iface_layout_list_view:
					performListView();
					break;
					
				case R.layout.activity_user_iface_layout_grid_view:
					performGridView();
					break;
			}
		}
	}
	
	/**
	 * do some basic linear layout processing.
	 */
	private void performLinearLayoutProcessing()
	{
		TextView txtView = (TextView) findViewById(R.id.text_id_linear_layout);
		
		final String Label = txtView.getText().toString();

/*this area is for UI controls and event handlers demos...with anonymous classes*/
		txtView.setOnClickListener
		( new View.OnClickListener() 
			{
				public void onClick(View v) 
				{
					Toast.makeText
					   (getApplicationContext(), "You have clicked the Label : " + Label, Toast.LENGTH_LONG).show();
				}
			}
		);
		
		final EditText eText;
		final Button btn;
		
		eText = (EditText) findViewById(R.id.edittext_linear_layout);
		btn = (Button) findViewById(R.id.button);
		btn.setOnClickListener
		( new OnClickListener() 
			{
				public void onClick(View v) 
				{
					String str = eText.getText().toString();
					Toast msg = Toast.makeText(getBaseContext(),str,
					Toast.LENGTH_LONG);
					msg.show();
					msg.show();
				}
			}
		);
/*this area is for UI controls and event handlers demos...with anonymous classes*/
		
		//this is event handling with activity class implementing OnClick iface.
		//--- find both the buttons---
		Button sButton = (Button) findViewById(R.id.button_s);
		Button lButton = (Button) findViewById(R.id.button_l);
		
		// -- register click event with first button ---
		sButton.setOnClickListener(this);
		
		// -- register click event with second button ---
		lButton.setOnClickListener(this);
	}
	
	/**
	 * this function will do some basic processing after the view has been loaded to 
	 * then update the UI..
	 */
	private void performRelLayoutProcessing()
	{
		//create simple date obj using this format, and us as locale.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.US);			
		
		//preload the date obj with current system time.
		Date date = new Date(System.nanoTime());
		
		//get string rep of this time.
		String nowDate = dateFormat.format(date);
		
		//get the txt field via id and set the text for the txt field using date string.
		TextView dateView = (TextView)findViewById(R.id.dates);
		dateView.setText(nowDate);
		
		//get time format with us locale
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss",Locale.US);
		
		// use date obj to get time as string.
		String nowTime = timeFormat.format(date);
		
		//find txt field and set txt for time.
		TextView timeView = (TextView)findViewById(R.id.times);
		timeView.setText(nowTime);
	}
	
	/**
	 * this will have a string array and create an array adapter to then provide it to the list
	 * view to setup a vertical scoll list on the ui.
	 */
	private void performListView()
	{
		// Array of strings...to be as a scrollable ui
		String [] countryArray = {"India", "Pakistan", "USA", "UK"};
		
		//this is creating an array adapter of strings that will be used to create text views objs to then
		//be setup in the list view ui display. the adapter makes the mapping from arrays of strings to
		//text view objs..it does it by calling toString() on each array element.
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(this, R.layout.activity_user_iface_layout_listview_textfield, countryArray);
		
		//this will get the list view by the id
		ListView listView = (ListView) findViewById(R.id.country_list);
				
		//setup the array adapter with the list view obj..this will now render the ui with array as list view for 
		//text view objs.
		listView.setAdapter(adapter);		
	}
	
	/**
	 * this will do the grid view processing to display 2D, (rows/cols)
	 * for data.
	 */
	private void performGridView()
	{
		//get gride view obj from layout.
		GridView gridview = (GridView) findViewById(R.id.gridview);
		
		//setup the image adapter to this grid view to allow to show the image under this activity.
		//this one call does the display of all the image thumbnails to the gridview for display.
		//it used the gridview id from the layout file to accomplish this.
		gridview.setAdapter(new ImageAdapter(this));
		
		//anonymous class used when clicking of a particular image to show that one in full screen.		
		gridview.setOnItemClickListener
		( new OnItemClickListener() 
			{
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
				{
					// Send intent to SingleViewActivity
					Intent i = new Intent(getApplicationContext(), SingleViewActivity.class);
					
					// Pass image index in intent to allow for other activity to use it
					//to find the image and place that image in the called activity for rendering.
					//u have the position from the clicked thumnnail.
					i.putExtra("id", position);
					
					//start the new activity
					startActivity(i);
				}
			}
		);	
	}
	
	/**
	 * function to be implemented from the onclickListener iface.
	 */
	public void onClick(View v)
	{
		if(v.getId() == R.id.button_s)
		{			
			//get the text view item. 
			TextView txtView = (TextView) findViewById(R.id.text_id_styles);
			
			// -- change text size --
			txtView.setTextSize(20);
			
			Toast.makeText(getBaseContext(), "Button S pressed handler via implements iface", Toast.LENGTH_LONG).show();
		}
		
		else if(v.getId() == R.id.button_l)
		{
			//get the text view item. 
			TextView txtView = (TextView) findViewById(R.id.text_id_styles);
			
			// -- change text size --
			txtView.setTextSize(40);
			
			Toast.makeText(getBaseContext(), "Button L pressed handler via implements iface", Toast.LENGTH_LONG).show();
		}
	}
}
