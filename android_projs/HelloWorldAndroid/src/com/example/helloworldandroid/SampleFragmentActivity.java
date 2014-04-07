package com.example.helloworldandroid;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class SampleFragmentActivity extends ActionBarActivity 
{

	private static final String MyTag = "SampleFragmentActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_sample_fragment);

//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		Log.d(MyTag, "inside this frag activity demo");
		super.onCreate(savedInstanceState);
		
		Configuration config = getResources().getConfiguration();
		
		//FragmentManager fragmentManager = getFragmentManager();		
		//FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		//call the support fragmgr for backwards compat
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		//specify specific fragment transaction.
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		/**
		 * do the portrait view then switch to landscape view after 3 secs.
		 */
		PM_Fragment pm_fragment = new PM_Fragment();
		fragmentTransaction.replace(android.R.id.content, pm_fragment);		
		fragmentTransaction.commit();
		
/*		try
		{
			Log.d(MyTag, "sleep for 3 secs");
			
			Thread.sleep(3000);
		}
		catch(Exception e){}

		fragmentTransaction = fragmentManager.beginTransaction();
		
		LM_Fragment ls_fragment = new LM_Fragment();
		fragmentTransaction.replace(android.R.id.content, ls_fragment);
*/
		
//		/**
//		* Check the device orientation and act accordingly*/
//		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) 
//		{
//			/**
//			* Landscape mode of the device
//			*/
//			LM_Fragment ls_fragment = new LM_Fragment();
//			fragmentTransaction.replace(android.R.id.content, ls_fragment);
//		}
//		else
//		{
//			/**
//			* Portrait mode of the device
//			*/
//			PM_Fragment pm_fragment = new PM_Fragment();
//			fragmentTransaction.replace(android.R.id.content, pm_fragment);
//		}
		
		//fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sample, menu);
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
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_sample,
//					container, false);
//			return rootView;
//		}
//	}

}
