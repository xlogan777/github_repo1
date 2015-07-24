package jmtechsvcs.myweatherapp.utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by jimmy on 7/23/2015.
 */
public class WeatherMapUtils
{
    private static final String LOGTAG = "WeatherMapUtils";

    //these show that we have a failed retrieval of the value and replaced with this
    //instead, showing we are missing data and do nothing.
    private final static long DEFAULT_lONG_VAL = Long.MAX_VALUE;
    private final static double DEFAULT_DOUBLE_VAL = Double.MAX_VALUE;
    private final static String DEFAULT_STRING_VAL = "DEFAULT_STRING_VAL";

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

    /*
        helper method to return a default value when json obj is null or when
        the optional key just returns the default value if we didnt find it.
     */
    public static long getLongVal(JSONObject jsonObject, String key)
    {
        long rv;

        if(jsonObject != null)
            rv = jsonObject.optLong(key, DEFAULT_lONG_VAL);
        else
            rv = DEFAULT_lONG_VAL;

        Log.d(LOGTAG, (rv == DEFAULT_lONG_VAL ? "using default long max val" : "long val = "+rv));

        return rv;
    }

    public static double getDoubleVal(JSONObject jsonObject, String key)
    {
        double rv;

        if(jsonObject != null)
            rv = jsonObject.optDouble(key, DEFAULT_DOUBLE_VAL);
        else
            rv = DEFAULT_DOUBLE_VAL;

        Log.d(LOGTAG, (rv == DEFAULT_DOUBLE_VAL ? "using default double max val" : "double val = "+rv));

        return rv;
    }

    public static String getStringVal(JSONObject jsonObject, String key)
    {
        String rv;

        if(jsonObject != null)
            rv = jsonObject.optString(key, DEFAULT_STRING_VAL);
        else
            rv = DEFAULT_STRING_VAL;

        Log.d(LOGTAG, (rv == DEFAULT_STRING_VAL ? "using default String val" : "string val = "+rv));

        return rv;
    }
}
