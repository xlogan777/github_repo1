package com.util.mydao_network_layer;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * this class deals with the connections to the network
 * should handle different types of http connections.
 * 
 * author J.Mena
 *
 */
public class NetworkProcessing 
{
	private static String LOGTAG = "NetworkProcessing";
	
	/*
	 * function that does http GET request using a url.
	 */
	public static InputStream HttpGetProcessing(String url)
	{
		//return the input stream.
		InputStream rv = null;
		
		//check for null string, and emtpy string..
		//should handle more error cases, good for now.
		if(url != null & url.length() > 0)
		{
			try 
			{
				// create HttpClient
				HttpClient httpclient = new DefaultHttpClient();
				
				//make the http get 
				HttpGet get_request = new HttpGet(url);

				// make GET request to the given URL
				HttpResponse httpResponse = httpclient.execute(get_request);
				
				//get status code from http response.
				int status = httpResponse.getStatusLine().getStatusCode();

				if(HttpStatus.SC_OK == status)
				{
					// receive response as inputStream, will return a valid input stream
					//otherwise will throw an exception.
					rv = httpResponse.getEntity().getContent();
				}
			} 
			catch (Exception e) 
			{
				Log.d(LOGTAG, "JM..."+e.getMessage());
			}			
		}

		//return input stream.
		return rv;
	}
}
