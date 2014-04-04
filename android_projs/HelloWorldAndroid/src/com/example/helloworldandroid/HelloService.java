package com.example.helloworldandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class HelloService extends Service 
{
	private static final String MyTag = "HelloService";
	
	/** indicates how to behave if the service is killed */
	int mStartMode;
	
	/** interface for clients that bind */
	IBinder mBinder = null;//for now just set to null..since we are not binding a service.
	
	/** indicates whether onRebind should be used */
	boolean mAllowRebind;
	
	/** Called when the service is being created. */
	@Override
	public void onCreate() 
	{
		Log.d(MyTag, "onCreate()");
	}
	
	/** The service is starting, due to a call to startService() */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		Log.d(MyTag, "onStartCommand()");
		
		// Let it continue running until it is stopped.
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		return START_STICKY;
	}
	
	/** A client is binding to the service with bindService() */
	@Override
	public IBinder onBind(Intent intent) 
	{
		Log.d(MyTag, "onBind()");
		return mBinder;
	}
	
	/** Called when all clients have unbound with unbindService() */
	@Override
	public boolean onUnbind(Intent intent) 
	{
		Log.d(MyTag, "onUnBind()");
		return mAllowRebind;
	}
	
	/** Called when a client is binding to the service with bindService()*/
	@Override
	public void onRebind(Intent intent) 
	{
		Log.d(MyTag, "onRebind()");
	}
	/** Called when The service is no longer used and is being destroyed */
	@Override
	public void onDestroy() 
	{		
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		Log.d(MyTag, "onDestroy()");
	}
}
