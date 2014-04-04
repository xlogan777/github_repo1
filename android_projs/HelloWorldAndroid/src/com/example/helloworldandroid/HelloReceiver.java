package com.example.helloworldandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class HelloReceiver extends BroadcastReceiver 
{
	private static final String MyTag = "HelloReceiver";
	
	public HelloReceiver() 
	{
		
	}

	/**
	 * this will receive an intent obj and perform appropriate action.
	 * 
	 */
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Log.d(MyTag, "onReceive()...jimbo...");
		
		//perform action
		Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();		
	}
}
