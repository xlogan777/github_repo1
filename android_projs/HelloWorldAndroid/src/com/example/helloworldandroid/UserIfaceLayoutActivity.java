package com.example.helloworldandroid;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class UserIfaceLayoutActivity extends ActionBarActivity 
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

				//these are just the same processing, show the ui.
				case R.layout.activity_user_iface_layout_table:
				case R.layout.activity_user_iface_layout_absolute:
				case R.layout.activity_user_iface_layout:
				case R.layout.activity_user_iface_layout_frame:
					break;		
			}
		}
	}
	
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
}
