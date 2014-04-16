package com.example.advandedandroidproj;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.os.Bundle;

public class NotificationViewActivity extends ActionBarActivity 
{
	private static String MyTag = "NotificationViewActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_view);
		Log.d(MyTag, "Show list of notification items");
	}
}
