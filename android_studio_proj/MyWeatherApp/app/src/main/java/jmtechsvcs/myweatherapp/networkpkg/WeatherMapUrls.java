package jmtechsvcs.myweatherapp.networkpkg;

import android.util.Log;

/**
 * Created by Menaj on 7/22/2015.
 */
public class WeatherMapUrls
{
    private static String LOGTAG = "WeatherMapUrls";

    //api key from open weather map api.
    //public final static String API_KEY = "22ecf4a075718bdfcd5657156e3272b5";

    //new api key at this location
    //http://home.openweathermap.org/users/sign_in
    public final static String API_KEY = "0beb3d307c8b73836c9ce5490e120405";

    //api key used for all http calls.
    private final static String APP_ID = "APPID="+API_KEY;//weather app id.

    //current weather end point
    private final static String CURRENT_WEATHER_END_PT = "http://api.openweathermap.org/data/2.5/weather?";

    //weather icon end pt.
    private final static String WEATHER_ICON_END_PT = "http://openweathermap.org/img/w/";

    //weather station info url.
    private final static String WEATHER_STATION_GEO_END_PT = "http://api.openweathermap.org/data/2.5/station/find?";

    //weather station id end pt.
    private final static String CURRENT_WEATHER_STATION_END_PT = "http://api.openweathermap.org/data/2.5/station?id=";

    //daily weather forecast end pt
    private final static String DAILY_WEATHER_FORECAST_END_PT = "http://api.openweathermap.org/data/2.5/forecast/daily?id=";

    //hourly weather forecast end pt
    private final static String HOURLY_WEATHER_FORECAST_END_PT = "http://api.openweathermap.org/data/2.5/forecast?id=";

    public static String getHourlyWeatherForecastEndPt(String cityId)
    {
        String rv = HOURLY_WEATHER_FORECAST_END_PT+cityId+"&mode=xml&cnt=7&units=imperial&"+APP_ID;
        Log.d(LOGTAG,rv);

        return rv;
    }

    public static String getDailyWeatherForecastEndPt(String cityId)
    {
        String rv = DAILY_WEATHER_FORECAST_END_PT+cityId+"&mode=xml&units=imperial&cnt=5&"+APP_ID;
        Log.d(LOGTAG,rv);

        return rv;
    }

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

//    public static String getWeatherStationInfoById(String stationId)
//    {
//        String rv = CURRENT_WEATHER_STATION_END_PT+stationId+"&"+APP_ID;
//        Log.d(LOGTAG,"url => "+rv);
//        return rv;
//    }
}
