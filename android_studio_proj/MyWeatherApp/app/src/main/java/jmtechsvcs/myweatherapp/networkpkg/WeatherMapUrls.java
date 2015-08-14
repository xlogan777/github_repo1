package jmtechsvcs.myweatherapp.networkpkg;

import android.util.Log;

/**
 * Created by Menaj on 7/22/2015.
 */
public class WeatherMapUrls
{
    private static String LOGTAG = "WeatherMapUrls";

    private final static String APP_ID = "APPID=22ecf4a075718bdfcd5657156e3272b5";//weather app id.
    private final static String CURRENT_WEATHER_END_PT = "http://api.openweathermap.org/data/2.5/weather?";
    private final static String WEATEHR_ICON_END_PT = "http://openweathermap.org/img/w/";

    //TODO:
    /*
        may need to get the updated time from the xml feed based current data
        since there is an issue with the current time, sunrise, sunset times in utc..
        vs the one from the xml feed.
     */

    public static String getCurrentWeatherByCityId(String cityId)
    {
        String rv = CURRENT_WEATHER_END_PT+"id="+cityId+"&"+APP_ID;
        Log.d(LOGTAG, "url => "+rv);
        return rv;
    }

    public static String getWeatherIconByIconId(String iconId)
    {
        String rv = WEATEHR_ICON_END_PT+iconId+".png?"+APP_ID;
        Log.d(LOGTAG,"url => "+rv);
        return rv;
    }
}
