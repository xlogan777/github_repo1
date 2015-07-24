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

import jmtechsvcs.myweatherapp.utils.WeatherMapUtils;

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
	public static DataPayload httpGetProcessing(String url, DataPayload.T_Payload_Type payloadType)
	{
		//create payload obj and set payload type.
		DataPayload dataPayload = new DataPayload();
		dataPayload.setPayloadType(payloadType);

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
				if(status_code == HttpURLConnection.HTTP_OK)
				{
					//get the input stream.
					rv = conn.getInputStream();

					switch(payloadType)
					{
						case E_JSON_PAYLOAD_TYPE:

							//get the json string from the input stream.
							String json_string = WeatherMapUtils.getJsonStringFromStream(rv);

							//set correct payload data here.
							dataPayload.setStringPayload(json_string);

							break;

						case E_BYTE_ARRAY_PAYLOAD_TYPE:

							//get byte array from input stream.
							byte [] data = WeatherMapUtils.getByteArrayFromStream(rv);

							//set correct payload data here.
							dataPayload.setBytePayload(data);

							break;
					}
				}
			} 
			catch (Exception e) 
			{
				Log.d(LOGTAG, "JM..."+e);
			}
			finally
			{
				//close http conn here
				if(conn != null)
				{
					conn.disconnect();
				}

				//close the stream.
				if(rv != null)
				{
					try
					{
						rv.close();
					}
					catch(Exception e)
					{
						Log.d(LOGTAG,""+e);
					}
				}
			}
		}
		else
		{
			Log.d(LOGTAG,"url null or len of url string = 0");
		}

		//return input stream.
		return dataPayload;
	}
}
