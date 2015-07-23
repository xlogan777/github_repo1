package jmtechsvcs.myweatherapp.networklayer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
	public static InputStream httpGetProcessing(String url)
	{
		//return the input stream.
		InputStream rv = null;

		//http connection.
		HttpURLConnection conn = null;

		//check for null string, and emtpy string..
		//should handle more error cases, good for now.
		if(url != null && url.length() > 0)
		{
			try 
			{
				//create url obj
				URL url_get = new URL(url);

				//cast to http conn type
				conn = (HttpURLConnection) url_get.openConnection();

				//setup as a Get request.
				conn.setRequestMethod("GET");
				//conn.setDoInput(true);
				//conn.setDoOutput(true);

				//this will access the url, and get back a status code.
				int status_code = conn.getResponseCode();

				//if we have a valid status get the data.
				if(status_code ==HttpURLConnection.HTTP_OK)
				{
					rv = conn.getInputStream();
				}
			} 
			catch (Exception e) 
			{
				Log.d(LOGTAG, "JM..."+e);
			}
			finally
			{
				//close resources here.
				if(conn != null)
				{
					conn.disconnect();
				}
			}
		}
		else
		{
			Log.d(LOGTAG,"url null or len of url string = 0");
		}

		//return input stream.
		return rv;
	}
}
