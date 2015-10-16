package jmtechsvcs.myweatherapp.utilspkg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import org.joda.time.LocalDate;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.HourlyWeatherInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTable;
import jmtechsvcs.myweatherapp.networkpkg.DataPayload;
import jmtechsvcs.myweatherapp.networkpkg.NetworkProcessing;
import jmtechsvcs.myweatherapp.networkpkg.WeatherMapUrls;

/**
 * Created by jimmy on 7/23/2015.
 */
public class WeatherAppUtils
{
    private static final String LOGTAG = "WeatherAppUtils";

    //these show that we have a failed retrieval of the value and replaced with this
    //instead, showing we are missing data and do nothing.
    public final static long DEFAULT_lONG_VAL = Long.MAX_VALUE;
    public final static double DEFAULT_DOUBLE_VAL = Double.MAX_VALUE;
    public final static String DEFAULT_STRING_VAL = "DEFAULT_STRING_VAL";
    public final static String UTC_DATE_FORMAT_hms = "yyyy-MM-dd'T'HH:mm:ss";
    public final static String UTC_DATE_FORMAT = "yyyy-MM-dd";

    //action to be used for stopping of the loading spinner
    public static final String STOP_SPINNER_ACTION = "STOP_SPINNER_ACTION";

    //action to be used for invoking current weather activity.
    public static final String START_CURRENT_WEATHER_ACTIVITY_ACTION =
            "START_CURRENT_WEATHER_ACTIVITY_ACTION";

    //action to be used for daily weather loading.
    public static final String START_DAILY_WEATHER_ACTIVITY_ACTION =
            "START_DAILY_WEATHER_ACTIVITY_ACTION";

    //action to be use for weather station loading.
    public static final String START_WEATHER_STATION_ACTIVITY_ACTION =
            "START_WEATHER_STATION_ACTIVITY_ACTION";

    public static final String START_CURRENT_HOURLY_FORECAST_ACTIVITY_ACTION =
            "START_CURRENT_HOURLY_FORECAST_ACTIVITY_ACTION";

    //used to show how much to allow to fill for hourly data.
    public static final int HOURLY_FILL_LIST_SIZE = 8;

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

    //used the get byte aray method to return an array and wrap that array into
    //a byte array input stream and return that to the caller.
    public static ByteArrayInputStream getByteSteamFromStream(InputStream inputStream)
    {
        //get the byte array.
        byte [] byte_data = getByteArrayFromStream(inputStream);
        ByteArrayInputStream data = null;

        //check if the data is null, if not, then create a
        //byte array input stream.
        if(byte_data != null)
        {
            //create new obj with byte array inside the stream.
            data = new ByteArrayInputStream(byte_data);
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

        Log.d(LOGTAG, (Double.compare(rv, DEFAULT_DOUBLE_VAL) == 0 ? "using default double max val" : "double val = "+rv));

        return rv;
    }

    public static String getStringVal(JSONObject jsonObject, String key)
    {
        String rv;

        if(jsonObject != null)
            rv = jsonObject.optString(key, DEFAULT_STRING_VAL);
        else
            rv = DEFAULT_STRING_VAL;

        Log.d(LOGTAG, (rv.equals(DEFAULT_STRING_VAL) ? "using default String val" : "string val = " + rv));

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
    public static Bitmap readPngFile(String imagePath, int width, int height) throws IOException
    {
        //path to image file created.
        File file_path = new File(imagePath);

        //get a bitmap from the image path and decoded for bitmap.
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file_path));

        //resize bitmap to new width x new height, from method params and no filter.
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

        //return resized bitmap.
        return resizedBitmap;
    }

    //dialog list of items used for choices.
    public static String [] getDialogList()
    {
        String [] weather_opts =
                {"Current Weather Info",
                 "5 Day Forecast",
                 "1 Day, 3 hourly forecast",
                 "Current Weather From Stations"};

        return weather_opts;
    }

    public static String getDefaultStringDisplayLong(long data)
    {
        String rv = "No Data Available";
        if(data != DEFAULT_lONG_VAL)
            rv = "";

        return rv;
    }

    public static String getDefaultStringDisplayDouble(double data)
    {
        String rv = "No Data Available";
        if(Double.compare(data,DEFAULT_DOUBLE_VAL) != 0 &&
           Double.compare(data,Double.NaN)!= 0)
            rv = "";

        return rv;
    }

    public static String getDefaultStringDisplayString(String data)
    {
        String rv = "No Data Available";
        //check for valid obj ref, data > 0 at ref, and not equal to default string.
        if(data != null && data.length() > 0 && !data.equals(DEFAULT_STRING_VAL))
            rv = "";

        return rv;
    }

    //parse utc formatted string and get a long seconds value.
    public static long getUtcSecondsFromDateString(String input, String format)
    {
        long rv = DEFAULT_lONG_VAL;

        try
        {
            //2015-08-15T11:00:07, from xml feed.
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            Date inputDate = dateFormat.parse(input);
            Log.d(LOGTAG,inputDate.toString());
            rv = inputDate.getTime();
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }

        return rv;
    }

    //get a string value from utc seconds in the format specified.
    public static String getUtcFromUtcSeconds(long utcSeconds, String format)
    {
        String rv = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        rv = dateFormat.format(new Date(utcSeconds));
        Log.d(LOGTAG,"date = "+rv);

        return rv;
    }

    public static void localSleep(long seconds)
    {
        long my_secs = -1;
        if(seconds > 20 && seconds < 5)
        {
            my_secs = 5;
        }
        else
        {
            my_secs = seconds;
        }

        my_secs *= 1000;//make it millis

        try
        {
            Thread.sleep(my_secs);
        }
        catch (Exception e)
        {
            WeatherAppUtils.getStackTrace(e);
        }
    }

    public static boolean isSameDay(Date date1, Date date2)
    {
        //use local date, to compare dates.
        LocalDate date_1 = new LocalDate(date1);
        LocalDate date_2 = new LocalDate(date2);

        boolean is_equal = date_1.isEqual(date_2);
        Log.d(LOGTAG, "date 1 equals to date 2 = "+is_equal);

        //return if local date 1 is equal to date 2.
        return is_equal;
    }

    //return the bit map using the weather icon table.
    public static Bitmap loadCityWeatherIcon(WeatherIconTable weatherIconTable)
    {
        Bitmap bitmap = null;

        try
        {
            //get the image path from the bean obj.
            String img_path = weatherIconTable.getImage_path();

            if(img_path != null && img_path.length() > 0)
            {
                bitmap = WeatherAppUtils.readPngFile(img_path, 150, 150);
            }
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }

        return bitmap;
    }

    public static WeatherIconTable getWeatherIconTable(String iconId, Context context)
    {
        //create bean query param.
        BeanQueryParams qp = new BeanQueryParams();

        //get the image icon and allow to have it loaded to the image view.
        //setup the query params for access to the icon data.
        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_IMG_ICON_TABLE_TYPE);
        qp.setCityId(-1);
        qp.setIconId(iconId);

        //create obj ref here to be used by the generic method for query params.
        WeatherIconTable weatherIconTable = new WeatherIconTable();
        weatherIconTable = WeatherDbProcessing.getBeanByQueryParams(qp, context, weatherIconTable);

        return weatherIconTable;
    }

    public static void getAndSaveIconData(String iconId, Context context)
    {
        //check if this icon exists in the DB first before the effort to get new icon and
        //do db stuff.
        boolean iconFound = WeatherDbProcessing.weatherIconExists(iconId, context);

        //only try to get the data if the icon doesnt exists in the db.
        if(!iconFound)
        {
            //create the weather icon url.
            String weather_icon_url = WeatherMapUrls.getWeatherIconByIconId(iconId);

            //get the payload from the http get for the weather icon.
            DataPayload payload = NetworkProcessing.httpGetProcessing
                    (weather_icon_url, DataPayload.T_Payload_Type.E_BYTE_ARRAY_PAYLOAD_TYPE);

            //save the icon data into the DB.
            WeatherDbProcessing.updateWeatherIcon
                    (iconId, weather_icon_url, payload.getBytePayload(), context);
        }
    }

    //this will take the city id and do a search, and from there
    //save into the provided list the data u need for it based on the param passed as
    //a control mechanism.
    public static void getHourlyWeatherData
    (
     long cityId,
     List<HourlyWeatherInfoTable> hourlyWeatherList,
     int fillSize,
     Context context
    )
    {
        BeanQueryParams qp = new BeanQueryParams();
        qp.setCityId(cityId);

        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_HOURLY_WEATHER_TABLE_LIST_TYPE);

        //get list of weather stations based on the query params.
        List<HourlyWeatherInfoTable> hourly_weather_param =
                WeatherDbProcessing.getBeanByQueryParamsList(qp, context, new HourlyWeatherInfoTable());

        //track the size to copy.
        int end_size = 0;

        //get the first 8 items and display it to the user.
        if(hourly_weather_param.size() >= fillSize)
        {

            end_size = fillSize;
        }
        else
        {
            end_size = hourly_weather_param.size();
        }

        //only if we have a valid ref list and its empty will we add data to it.
        if(hourlyWeatherList != null && hourlyWeatherList.size() == 0)
        {
            //copy sublist from list of dao to list to display.
            hourlyWeatherList.addAll(hourly_weather_param.subList(0,end_size));
        }
        else
        {
            Log.d(LOGTAG,"we have a either null or filled in list for hourly weather...this is an issue.");
        }
    }
}
