package com.example.advandedandroidproj;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/*import for json data*/



import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.annotation.SuppressLint;

/*
 * this is the sample class to do json parsing for weather stuff...
 */
public class JsonParserActivity extends Activity
{
	//google url for weather 
	private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
	
	//these are the view objs that will show data here
	private EditText location,country,temperature,humidity,pressure;
	
	//internal obj that will be used to parse json..via http connection
	private HandleJSON obj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_json_parser);
		
		/*get all the view objs from the context and init local vars*/
		location = (EditText)findViewById(R.id.editText1);
		country = (EditText)findViewById(R.id.editText2);
		temperature = (EditText)findViewById(R.id.editText3);
		humidity = (EditText)findViewById(R.id.editText4);
		pressure = (EditText)findViewById(R.id.editText5);
	}
	
	/*
	 * call back function to allow for kicking off the demo for parsing json.
	 */
	public void onClickReadWeather(View view)
	{
		Log.d("JsonParserActivity", "doing the parsing for json when button clicked.");
		
		String url = "newyork";//default to ny for weather gathering.
		location.setText(url);//set the text for the state.
		
		//build final url...
		String finalUrl = url1 + url;
		
		//set text for final url for viewing in the activity.
		country.setText(finalUrl);
		
		//create obj to handler json
		obj = new HandleJSON(finalUrl);
		obj.fetchJSON();//get the json data
		
		//empty while loop to allow for getting json data and then showing
		//it to the user...this is very bad...dont do it like this..only for demo.
		while(obj.parsingComplete);
		
		//if we get here then we properly parsed json data, and display it on the activity.
		country.setText(obj.getCountry());
		temperature.setText(obj.getTemperature());
		humidity.setText(obj.getHumidity());
		pressure.setText(obj.getPressure());
	}
	
/*private class to read json data*/
	
	private class HandleJSON 
	{
		//vars to handle the json name/value data
		private String country = "county";
		private String temperature = "temperature";
		private String humidity = "humidity";
		private String pressure = "pressure";
		private String urlString = null;
		
		//vars to be used as a way to signal Ui thread to continue when 
		//internal thread is done.
		public volatile boolean parsingComplete = true;
		
		public HandleJSON(String url)
		{
			this.urlString = url;
		}
		
		public String getCountry()
		{
			return country;
		}
		
		public String getTemperature()
		{
			return temperature;
		}
		
		public String getHumidity()
		{
			return humidity;
		}
		
		public String getPressure()
		{
			return pressure;
		}
		
		@SuppressLint("NewApi")
		/*
		 * this will parse json data give a json string
		 */
		public void readAndParseJSON(String in) 
		{
			try 
			{
				//create json obj reader with string
				JSONObject reader = new JSONObject(in);
				
				//create json obj from sys key name
				//get country field from this obj.
				JSONObject sys = reader.getJSONObject("sys");				
				country = sys.getString("country");
				
				//get json object from main key
				JSONObject main = reader.getJSONObject("main");
				
				//get data from main json obj to fill other fields.
				temperature = main.getString("temp");
				pressure = main.getString("pressure");
				humidity = main.getString("humidity");
				
				//signal to UI thread that parsing is done.
				parsingComplete = false;
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void fetchJSON()
		{
			//create new thread to do this background processing.
			Thread thread = new Thread
			(	new Runnable()
				{
					@Override
					public void run() 
					{
						try 
						{
							//create url obj with url string
							URL url = new URL(urlString);
							
							//establish http connection obj with url connecton
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							
							//set the param for this http connection, calling REST api for weather, via GET 
							conn.setReadTimeout(10000 /* milliseconds */);//timeout for reads
							conn.setConnectTimeout(15000 /* milliseconds */);//connection timeout
							conn.setRequestMethod("GET");//request method type
							conn.setDoInput(true);//telling connection to allow input
							
							// Starts the query
							conn.connect();//establish the connection
							
							//get the stream from connection
							InputStream stream = conn.getInputStream();
							
							//java.util.Scanner scanner = new java.util.Scanner(stream).useDelimiter("\\A");
							
							//create a scanner using the stream and use a delimiter for it.
							java.util.Scanner scanner = new java.util.Scanner(stream);
							scanner.useDelimiter("\\A");//returned this scanner
							
							//if we have data then get the data
							String data = scanner.hasNext() ? scanner.next() : "";
							
							readAndParseJSON(data);//parse json							
							scanner.close();//close scanner
							stream.close();//close stream
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
				}
			);
			
			//start the thread to run in the background.
			thread.start();
		}
	}//private class
}
