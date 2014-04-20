package com.example.advandedandroidproj;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
 * this will handle all the location based services..it will do it with a asynch thread.
 * this class will implement the callback functions for the location client connection part.
 */
public class LocationBasedDemoActivity extends Activity implements 
GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{
	private static String MyTag = "LocationBasedDemoActivity";
	
	//client inteface to allow to get the location access to mgr
	LocationClient mLocationClient;
	
	//UI components to allow for showing that a connection to google play svcs occured.
	private TextView addressLabel;
	private TextView locationLabel;
	private Button getLocationBtn;
	private Button disconnectBtn;
	private Button connectBtn;
	
/*this is the activity related methods.*/	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_based_demo);
		
		// Create the LocationRequest object
		//this will contain the objs that are also needed such as the context,
		//the 2 interfaces that are needed for the connection code..
		//notification of connetion/no-connection, and connection failure.
		mLocationClient = new LocationClient(this, this, this);
		
		//get the text view objs and save them locally.
		locationLabel = (TextView) findViewById(R.id.locationLabel);
		addressLabel = (TextView) findViewById(R.id.addressLabel);
		
		//get the button obj to setup the anonymous listener to call specific function
		//to handle that event. this is the get the location from gps for location obj.
		getLocationBtn = (Button) findViewById(R.id.getLocation);
		getLocationBtn.setOnClickListener
		(	new View.OnClickListener() 
			{
				public void onClick(View view) 
				{
					//call location function to get the gps location right now.
					displayCurrentLocation();
				}
			}
		);
		
		//get the button obj to setup the anonymous listener to call specific function
		//to handle that event. this will perform the disconnect from the location obj
		//stop the connection from the google play svcs
		disconnectBtn = (Button) findViewById(R.id.disconnect);
		disconnectBtn.setOnClickListener
		(
			new View.OnClickListener() 
			{
				public void onClick(View view) 
				{
					//disconnect from the location obj and update the label with this status.
					mLocationClient.disconnect();
					locationLabel.setText("Got disconnected....");
				}
			}
		);
		
		//get the button obj to setup the anonymous listener to call specific function
		//to handle that event. this will perform the connect from the location obj
		//start the connection from the google play svcs
		connectBtn = (Button) findViewById(R.id.connect);
		connectBtn.setOnClickListener
		(	new View.OnClickListener() 
			{
				public void onClick(View view) 
				{
					//connect the location obj and update the label with this status.
					mLocationClient.connect();
					locationLabel.setText("Got connected....");
				}
			}
		);
	}
	
	@Override
	/**
	 * when this function is kicked off from main activity state machine, always do the connection
	 * to the location obj.
	 */
	protected void onStart() 
	{
		//perform base class on start functionality.
		super.onStart();
		
		// Connect the client.
		mLocationClient.connect();
		
		//update label for connected.
		locationLabel.setText("Got connected....");
	}
	
	@Override
	/**
	 * when this function is kicked off from main activity state machine, always disconnect
	 * from the location obj. so that its not running when you are moved away from activity.
	 */
	protected void onStop() 
	{
		Log.d(MyTag,"doing disconnect");
		
		// Disconnect the client.
		mLocationClient.disconnect();
		
		//perform base class onStop functionality		
		super.onStop();
		
		//update label for not-connected
		locationLabel.setText("Got disconnected....");
	}
/*this is the activity related methods.*/	

/*interface functions for google play svcs*/
	@Override
	/**
	 * for ConnectionCallbacks, showing that a connection has been made from the interface
	 * overriding functions.
	 */
	public void onConnected(Bundle dataBundle) 
	{
		// Display the connection status
		Toast.makeText(this, "Connected..enjoy", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	/**
	 * for ConnectionCallbacks, showing that a connection has been diconnected from the interface
	 * overriding functions.
	 */
	public void onDisconnected() 
	{
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	/**
	 * showing the failed connection for that interface function.
	 */
	public void onConnectionFailed(ConnectionResult connectionResult) 
	{
		// Display the error code on failure
		Toast.makeText(this, "Connection Failure : " +
		connectionResult.getErrorCode() + " value is "+connectionResult.toString(),
		Toast.LENGTH_SHORT).show();
		
		
	}
/*interface functions for google play svcs*/

	/**
	 * this function will get the current location from the location google play svc
	 * and get the recent location.
	 */
	public void displayCurrentLocation() 
	{		
		//dont allow to move next if not connected.
		if(!mLocationClient.isConnected())
		{
			Log.d(MyTag, "not connected..dont do anything");
			return;
		}
		
		// Get the current location's latitude & longitude
		//via the locationClient obj, will return most recent location obj.
		Location currentLocation = mLocationClient.getLastLocation();

		//create string obj with lat and long of location obj.
		String msg = "Current Location: " +
		Double.toString(currentLocation.getLatitude()) + "," +
		Double.toString(currentLocation.getLongitude());
		
		// Display the current location in the UI
		locationLabel.setText(msg);
		
		// To display the current address in the UI using a background thread
		// since this may take some time, and dont want to hold up the 
		//GUI thread(main thread) for this processing.
		
		//create asynch task with this activity.
		GetAddressTask gat = new GetAddressTask(this);
		
		//execute its function here with the current location.
		gat.execute(currentLocation);
	}
	
	/*
	* Following is a subclass of AsyncTask which has been used to get
	* address corresponding to the given latitude & longitude.
	* this will allow to process in the background and post back info to main thread(UI)
	*/
	private class GetAddressTask extends AsyncTask<Location, Void, String>
	{
		//save the context of this activity.
		Context mContext;
		
		public GetAddressTask(Context context) 
		{
			super();
			mContext = context;
		}
		
		/*
		* When the task finishes, onPostExecute() displays the address.
		* this function runs in the context of the GUI thread(main thread)
		* once the doInBackGround is done. it uses the data from the output
		* of doInBackGround as the input for this function.
		*/
		@Override
		protected void onPostExecute(String address) 
		{
			// Display the current address in the UI
			addressLabel.setText(address);
		}
		
		@Override
		/*
		 * this will perform background processing and return the result 
		 * for the onPostExecute to do its job in the UI thread.
		 * 
		 */
		protected String doInBackground(Location... params) 
		{
			//creates easy to use obj to be configured with local settings.
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			
			// Get the current location from the input parameter list
			//gets the first location obj from the varargs of the function params.
			Location loc = params[0];
			
			// Create a list to contain the result address
			List<Address> addresses = null;
			
			try 
			{
				//this returns a list obj with results set to 1 in the list.
				//this does a network lookup...takes time to do this.
				//this is using the lat and long from the location obj to 
				//get an address.
				addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
			}
			
			catch (IOException e1) 
			{
				Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
				
				e1.printStackTrace();
				
				return ("IO Exception trying to get address");
			}
			
			catch (IllegalArgumentException e2) 
			{
				// Error message to post in the log
				String errorString = "Illegal arguments " +
				Double.toString(loc.getLatitude()) +
				" , " +
				Double.toString(loc.getLongitude()) +
				" passed to address service";
				
				Log.e("LocationSampleActivity", errorString);
				
				e2.printStackTrace();
				
				return errorString;
			}
			
			// if the geocoder returned and address
			if (addresses != null && addresses.size() > 0)
			{
				// Get the first address
				Address address = addresses.get(0);
				
				/*
				* Format the first line of address (if available),
				* city, and country name.
				*/
				String addressText = String.format(
				"%s, %s, %s",
				// If there's a street address, add it
				address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
						
				// Locality is usually a city
				address.getLocality(),
				
				// The country of the address
				address.getCountryName());
				
				// Return the text
				return addressText;
			} 
			else 
			{
				return "No address found";
			}
		}//doInBackground
	}// AsyncTask class	
}
