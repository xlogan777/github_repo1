package jmtechsvcs.myweatherapp.utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Arrays;

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
    //it does not close the input stream..that is left to the caller.
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
            while ((numRead = input.read(buffer, 0, buffer.length)) > 0)
            {
                //save buffer to string builder with chars read.
                sb.append(buffer, 0, numRead);
            }

            //close the reader.
            input.close();
        }
        catch(Exception e)
        {
            Log.d(LOGTAG,WeatherMapUtils.getStackTrace(e));
        }

        return sb.toString();
    }

    //reads byte data from the input stream
    //if an error occured the it returns null in the ref back to caller.
    public static byte[] getByteArrayFromStream(InputStream inputStream)
    {
        byte [] data = null;

        try
        {
            //create byte array obj.
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            //create buff to hold bytes read.
            byte [] buff = new byte[1024];//1kb buff
            int bytes_read = 0;

            //read raw bytes to buff, and track bytes read
            while( (bytes_read = inputStream.read(buff,0,buff.length)) >0 )
            {
                //save bytes reads to output stream
                bos.write(buff,0,bytes_read);
            }

            //get byte array from outstream
            data = bos.toByteArray();

            //close the output stream.
            bos.close();
        }
        catch(Exception e)
        {
            Log.d(LOGTAG,WeatherMapUtils.getStackTrace(e));
        }

        return data;
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

    /*
        this method will format a stack trace just like the printStackTrace method.. with the error message
     */
    public static String getStackTrace(Exception exception)
    {
        //create a string writer for the print writer
        StringWriter sw = new StringWriter();

        //print the java stack trace to the print writer which is wrapping to the string writer.
        exception.printStackTrace(new PrintWriter(sw));

        //get the string from the string writer.
        return sw.toString();

        //TODO: may need to write these expections to a errors file...not sure..
    }
}
