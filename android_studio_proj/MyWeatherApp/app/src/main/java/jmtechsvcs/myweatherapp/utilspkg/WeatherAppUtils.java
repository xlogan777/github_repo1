package jmtechsvcs.myweatherapp.utilspkg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

/**
 * Created by jimmy on 7/23/2015.
 */
public class WeatherAppUtils
{
    private static final String LOGTAG = "WeatherAppUtils";

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
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
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
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
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

        Log.d(LOGTAG, (rv == DEFAULT_STRING_VAL ? "using default String val" : "string val = " + rv));

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

    /*
        this will create a png file in the specified images dir, and compress the bit map
        thats was generated from the byte array and save this bitmap as a png compressed
        file to the images dir.
     */
    public static String saveByteToPngFile(Context context, String iconId, byte [] rawImage)
    throws IOException
    {
        // path to /data/data/yourapp/app_data/weather_imgs
        //create app directory here if it doesnt exist, or get the path to the
        //directory.
        File directory = context.getDir("weather_imgs", Context.MODE_PRIVATE);

        //file to create as a png file using the image icon id.
        //attach the file output stream to it.
        File image_path = new File(directory, iconId+".png");
        FileOutputStream fos = new FileOutputStream(image_path);

        //create a bitmap obj here.
        Bitmap bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length);

        // Use the compress method on the BitMap object to write image to the OutputStream
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, fos);

        //close the output stream.
        fos.close();

        //returns the file path to this newly created image file.
        return image_path.getAbsolutePath();
    }

    /*
        this will take a image path from internal app storage and
        create a bitmap and return that back to the caller.
     */
    public static Bitmap readPngFile(String imagePath) throws IOException
    {
        //path to image file created.
        File file_path = new File(imagePath);

        //get a bitmap from the image path and decoded for bitmap.
        Bitmap rv = BitmapFactory.decodeStream(new FileInputStream(file_path));

//        FileInputStream fis = context.openFileInput(imagePath);
//        Bitmap b = BitmapFactory.decodeStream(fis);
//        fis.close();

        //ImageView img=(ImageView)findViewById(R.id.imgPicker);
        //img.setImageBitmap(b);

        return rv;
    }

    public static String [] getDialogList()
    {
        String [] weather_opts =
                {"Current City Weather",
                 "5 day / 3 hour forecast",
                  "16 day / daily forecast"};

        return weather_opts;
    }
}
