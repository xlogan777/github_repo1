package jmtechsvcs.myweatherapp.networklayer;

import android.util.Log;

/**
 * Created by Menaj on 7/22/2015.
 */
public class WeatherMapUrls
{
    private static String LOGTAG = "WeatherMapUrls";

    private final static String APP_ID = "APPID=22ecf4a075718bdfcd5657156e3272b5";//weather app id.
    private final static String currentWeatherEndPt = "http://api.openweathermap.org/data/2.5/weather?";
    private final static String weatherIcon = "http://openweathermap.org/img/w/";

    public static String getCurrentWeatherByCityId(String cityId)
    {
        String rv = currentWeatherEndPt+"id="+cityId+"&"+APP_ID;
        Log.d(LOGTAG, "url => "+rv);
        return rv;
    }

    public static String getWeatherIconByIconId(String iconId)
    {
        String rv = weatherIcon+iconId+".png?"+APP_ID;
        Log.d(LOGTAG,"url => "+rv);
        return rv;
    }
}
