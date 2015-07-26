package jmtechsvcs.myweatherapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import jmtechsvcs.myweatherapp.greendaosrcgen.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityWeatherCurrCondTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgen.DaoSession;

/**
 * Created by jimmy on 7/23/2015.
 */
public class WeatherJsonToDbProcessing
{
    private final static String LOGTAG = "WthrJsonToDbProcessing";

    //this will parse the json data and use a dao to save the obj to db.
    public static void updateCurrWeatherToDb(String jsonInput, DaoSession daoSession)
    {
        try
        {
            //get the dao to be used later.
            CityWeatherCurrCondTableDao curr_weather_dao = daoSession.getCityWeatherCurrCondTableDao();

            //create bean to be used by dao.
            CityWeatherCurrCondTable curr_weather_bean = new CityWeatherCurrCondTable();

            //main json obj for curr weather
            JSONObject curr_weather_obj = new JSONObject(jsonInput);

            //this needs to be here since we need this..fail the processing.
            long city_id = curr_weather_obj.getLong("id");
            curr_weather_bean.setCity_id(city_id);

            //get the weather conditions..this json array needs to be here.
            //with atleast 1 item., if this fails, dont continue...fail the processing.
            JSONArray weather_array = curr_weather_obj.getJSONArray("weather");
            JSONObject array_item = weather_array.getJSONObject(0);

            long weather_id = WeatherMapUtils.getLongVal(array_item, "id");
            String weather_main = WeatherMapUtils.getStringVal(array_item, "main");
            String weather_desc = WeatherMapUtils.getStringVal(array_item, "description");
            String weather_icon = WeatherMapUtils.getStringVal(array_item, "icon");
            curr_weather_bean.setCurr_weather_id(weather_id);
            curr_weather_bean.setCurr_weather_main(weather_main);
            curr_weather_bean.setCurr_weather_desc(weather_desc);
            curr_weather_bean.setCurr_weather_icon(weather_icon);

            JSONObject main_obj = curr_weather_obj.optJSONObject("main");
            double main_temp = WeatherMapUtils.getDoubleVal(main_obj,"temp");
            long main_pressure = WeatherMapUtils.getLongVal(main_obj, "pressure");
            long main_humidity = WeatherMapUtils.getLongVal(main_obj, "humidity");
            double main_temp_min = WeatherMapUtils.getDoubleVal(main_obj,"temp_min");
            double main_temp_max = WeatherMapUtils.getDoubleVal(main_obj,"temp_max");
            long main_sea_level = WeatherMapUtils.getLongVal(main_obj, "sea_level");
            long main_grnd_level = WeatherMapUtils.getLongVal(main_obj, "grnd_level");
            curr_weather_bean.setCurr_main_temp(main_temp);
            curr_weather_bean.setCurr_main_pressure(main_pressure);
            curr_weather_bean.setCurr_main_humidity(main_humidity);
            curr_weather_bean.setCurr_main_temp_min(main_temp_min);
            curr_weather_bean.setCurr_main_temp_max(main_temp_max);
            curr_weather_bean.setCurr_main_sea_level(main_sea_level);
            curr_weather_bean.setCurr_main_grnd_level(main_grnd_level);

            JSONObject wind_obj = curr_weather_obj.optJSONObject("wind");
            double wind_speed = WeatherMapUtils.getDoubleVal(wind_obj, "speed");
            long wind_degs = WeatherMapUtils.getLongVal(wind_obj, "deg");
            curr_weather_bean.setCurr_wind_speed(wind_speed);
            curr_weather_bean.setCurr_wind_degs(wind_degs);

            JSONObject clouds_obj = curr_weather_obj.optJSONObject("clouds");
            long clouds_all = WeatherMapUtils.getLongVal(clouds_obj, "all");

            JSONObject rain_obj = curr_weather_obj.optJSONObject("rain");
            long rain_3h = WeatherMapUtils.getLongVal(rain_obj, "3h");

            JSONObject snow_obj = curr_weather_obj.optJSONObject("snow");
            long snow_3h = WeatherMapUtils.getLongVal(snow_obj, "3h");

            curr_weather_bean.setCurr_clouds_all(clouds_all);
            curr_weather_bean.setCurr_rain_last3hrs(rain_3h);
            curr_weather_bean.setCurr_snow_last3hrs(snow_3h);

            long curr_calc_time = WeatherMapUtils.getLongVal(curr_weather_obj, "dt");
            curr_weather_bean.setCurr_data_calc_time(curr_calc_time);

            JSONObject sys_obj = curr_weather_obj.optJSONObject("sys");
            long sys_sunrise = WeatherMapUtils.getLongVal(sys_obj, "sunrise");
            long sys_sunset = WeatherMapUtils.getLongVal(sys_obj, "sunset");
            curr_weather_bean.setCurr_sys_sunrise_time(sys_sunrise);
            curr_weather_bean.setCurr_sys_sunset_time(sys_sunset);

            //save the data to the db.
            curr_weather_dao.insertOrReplace(curr_weather_bean);
        }
        catch (Exception e)
        {
            Log.d(LOGTAG,""+e);
        }
    }
}
