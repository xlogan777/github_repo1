package jmtechsvcs.myweatherapp.networkpkg;

import android.util.Log;

/**
 * Created by Menaj on 7/22/2015.
 */
public class WeatherMapUrls
{
    private static String LOGTAG = "WeatherMapUrls";

    //api key used for all http calls.
    private final static String APP_ID = "APPID=22ecf4a075718bdfcd5657156e3272b5";//weather app id.

    //current weather end point
    private final static String CURRENT_WEATHER_END_PT = "http://api.openweathermap.org/data/2.5/weather?";

    //weather icon end pt.
    private final static String WEATHER_ICON_END_PT = "http://openweathermap.org/img/w/";

    //weather station info url.
    private final static String WEATHER_STATION_GEO_END_PT = "http://api.openweathermap.org/data/2.5/station/find?";

    //weather station id end pt.
    private final static String CURRENT_WEATHER_STATION_END_PT = "http://api.openweathermap.org/data/2.5/station?id=";

    public static String getCurrentWeatherByCityId(String cityId)
    {
        String rv = CURRENT_WEATHER_END_PT+"id="+cityId+"&units=imperial&"+APP_ID;
        Log.d(LOGTAG, "url => "+rv);
        return rv;
    }

    public static String getCurrentWeatherByCityIdXml(String cityId)
    {
        String rv = CURRENT_WEATHER_END_PT+"id="+cityId+"&mode=xml&units=imperial&"+APP_ID;
        Log.d(LOGTAG, "url => "+rv);
        return rv;
    }

    public static String getWeatherIconByIconId(String iconId)
    {
        String rv = WEATHER_ICON_END_PT+iconId+".png?"+APP_ID;
        Log.d(LOGTAG,"url => "+rv);
        return rv;
    }

    public static String getWeatherStationInfoByGeo(String lat, String lon, String cnt)
    {
        String rv = WEATHER_STATION_GEO_END_PT+"lat="+lat+"&lon="+lon+"&cnt="+cnt+"&"+APP_ID;
        Log.d(LOGTAG,"url => "+rv);
        return rv;
    }

    public static String getWeatherStationInfoById(String stationId)
    {
        String rv = CURRENT_WEATHER_STATION_END_PT+stationId+"&"+APP_ID;
        Log.d(LOGTAG,"url => "+rv);
        return rv;
    }
}
