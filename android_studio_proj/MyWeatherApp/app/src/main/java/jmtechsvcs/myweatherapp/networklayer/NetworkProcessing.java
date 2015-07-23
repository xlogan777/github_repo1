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
	public enum T_Http_Get_Type
	{
		E_HTTP_CLIENT_TYPE,
		E_HTTP_URL_TYPE
	}
	private static String LOGTAG = "NetworkProcessing";

	/*
	 * function that does http GET request using a url.
	 */
	public static InputStream httpGetProcessing(String url, T_Http_Get_Type httpGetType)
	{
		//return the input stream.
		InputStream rv = null;

		//check for null string, and emtpy string..
		//should handle more error cases, good for now.
		if(url != null && url.length() > 0)
		{
			try 
			{
				switch(httpGetType)
				{
					case E_HTTP_CLIENT_TYPE:

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

						break;

					case E_HTTP_URL_TYPE:

						//create url obj
						URL url_get = new URL(url);

						//cast to http conn type
						HttpURLConnection conn = (HttpURLConnection) url_get.openConnection();

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

						break;
				}
			} 
			catch (Exception e) 
			{
				Log.d(LOGTAG, "JM..."+e);
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
