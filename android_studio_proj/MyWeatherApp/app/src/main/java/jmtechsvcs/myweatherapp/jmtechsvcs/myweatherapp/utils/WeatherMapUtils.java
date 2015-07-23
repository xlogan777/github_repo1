package jmtechsvcs.myweatherapp.jmtechsvcs.myweatherapp.utils;

import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by jimmy on 7/23/2015.
 */
public class WeatherMapUtils
{
    private static final String LOGTAG = "WeatherMapUtils";

    //returns a json string from the input stream or empty string if something went wrong.
    public static String getJsonStringFromStream(InputStream inputStream)
    {
        //this is to hold the buffer for json data.
        char[] buffer = new char[1024];
        StringBuilder sb = new StringBuilder(200);//default to 200 to improve performance.
        int numRead = 0;

        try
        {
            //creater reader io obj from input stream with utf-8 encoding.
            Reader input = new InputStreamReader(inputStream, "UTF-8");

            //get data from reader and store into char buff.
            while ((numRead = input.read(buffer, 0, buffer.length)) > 0) {
                //save buffer to string builder with chars read.
                sb.append(buffer, 0, numRead);
            }

            inputStream.close();
            input.close();
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, ""+e);
        }

        return sb.toString();
    }
}
