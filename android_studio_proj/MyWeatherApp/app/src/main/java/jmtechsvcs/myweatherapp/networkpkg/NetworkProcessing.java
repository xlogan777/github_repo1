package jmtechsvcs.myweatherapp.networkpkg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

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
    private static final int TIMEOUT = 60000;//timeout for http call.

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
				conn = (HttpURLConnection)url_get.openConnection();

                //set the timeouts to be for this url connection before we make
                //a new connection.
                conn.setConnectTimeout(TIMEOUT);
                conn.setReadTimeout(TIMEOUT);

				//setup as a Get request.
				conn.setRequestMethod("GET");

                //connect to the url.
                conn.connect();

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
							String json_string = WeatherAppUtils.getJsonStringFromStream(rv);

							//set correct payload data here.
							dataPayload.setStringPayload(json_string);

							break;

						case E_BYTE_ARRAY_PAYLOAD_TYPE:

							//get byte array from input stream.
							byte [] data = WeatherAppUtils.getByteArrayFromStream(rv);

							//set correct payload data here.
							dataPayload.setBytePayload(data);

							break;

						case E_BYTE_STREAM_PAYLOAD_TYPE:

							//get the byte stream from the input stream.
							ByteArrayInputStream bis = WeatherAppUtils.getByteSteamFromStream(rv);

							//set the correct payload data here.
							dataPayload.setInputStreamPayload(bis);

							break;
					}
				}
			} 
			catch (Exception e) 
			{
                Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
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
                        Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
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

	//this will check if a connection exists before making a conneciton.
	public static boolean checkInternetConnection(Context context)
	{
        boolean rv = false;

        //get the connectivity mgr here from the context.
		ConnectivityManager con_manager =
				(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //get the network info obj from the connection mgr.
        NetworkInfo net_info = con_manager.getActiveNetworkInfo();

        //check to see if we have a valid net info obj and that we have an avaible network
        //wifi or data, and that we are connected.
		if(net_info != null	&& net_info.isAvailable() && net_info.isConnected())
        {
			rv = true;
		}
        else
        {
            Log.d(LOGTAG,"either null net info obj, or not available network, or not connected..no network conn!");
        }

        return rv;
	}
}
