package jmtechsvcs.myweatherapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import jmtechsvcs.myweatherapp.GreenDaoSrcGen.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.CityWeatherCurrCondTableDao;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.DaoSession;

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

            long city_id = curr_weather_obj.getLong("id");
            curr_weather_bean.setCity_id(city_id);

            /*
            "weather": [ //only use the first item in the json array here!
        {
            "id": 803,//weather condition id
            "main": "Clouds",//group of weather params.(can be "Rain, Snow"..)
            "description": "broken clouds",//weather desc in the group
            "icon": "04n"//weather icon id, this shows the image icon
        }
    ],
             */
            JSONArray weather_array = curr_weather_obj.getJSONArray("weather");
            JSONObject array_item = weather_array.getJSONObject(0);
            long weather_id = array_item.getLong("id");
            String weather_main = array_item.getString("main");
            String weather_desc = array_item.getString("description");
            String weather_icon = array_item.getString("icon");

            curr_weather_bean.setCurr_weather_id(weather_id);
            curr_weather_bean.setCurr_weather_main(weather_main);
            curr_weather_bean.setCurr_weather_desc(weather_desc);
            curr_weather_bean.setCurr_weather_icon(weather_icon);


            /*
            "main": {
        "temp": 296.03,//kelvin
        "pressure": 1019,//atmospheric pressure, hPa units
        "humidity": 83,//humidity %
        "temp_min": 295.93,//kelvin
        "temp_max": 296.15,//kelvin
		"sea_level": 1020//atmospheric pressure, hPa units
		"grnd_level":1010//atmospheric pressure, hPa units
    },
             */

            JSONObject main_obj = curr_weather_obj.getJSONObject("main");
            double main_temp = main_obj.getDouble("temp");
            long main_pressure = main_obj.getLong("pressure");
            long main_humidity = main_obj.getLong("humidity");
            double main_temp_min = main_obj.getDouble("temp_min");
            double main_temp_max = main_obj.getDouble("temp_max");
            long main_sea_level = main_obj.getLong("sea_level");
            long main_grnd_level = main_obj.getLong("grnd_level");

            curr_weather_bean.setCurr_main_temp(main_temp);
            curr_weather_bean.setCurr_main_pressure(main_pressure);
            curr_weather_bean.setCurr_main_humidity(main_humidity);
            curr_weather_bean.setCurr_main_temp_min(main_temp_min);
            curr_weather_bean.setCurr_main_temp_max(main_temp_max);
            curr_weather_bean.setCurr_main_sea_level(main_sea_level);
            curr_weather_bean.setCurr_main_grnd_level(main_grnd_level);

            /*
             "wind": {
        "speed": 4.1,//meters/sec
        "deg": 160//degrees
    },
             */

            JSONObject wind_obj = curr_weather_obj.getJSONObject("wind");
            double wind_speed = wind_obj.getDouble("speed");
            long wind_degs = wind_obj.getLong("deg");

            curr_weather_bean.setCurr_wind_speed(wind_speed);
            curr_weather_bean.setCurr_wind_degs(wind_degs);

            /*
            "clouds": {
        "all": 75 //cloudiness %
    },

    "rain":{"3h":3},//rain volume for the last 3 hrs
	"snow":{"3h":3},//snow volume for the last 3 hrs
             */
            JSONObject clouds_obj = curr_weather_obj.getJSONObject("clouds");
            long clouds_all = clouds_obj.getLong("all");

            JSONObject rain_obj = curr_weather_obj.getJSONObject("rain");
            long rain_3h = rain_obj.getLong("3h");

            JSONObject snow_obj = curr_weather_obj.getJSONObject("snow");
            long snow_3h = snow_obj.getLong("3h");

            curr_weather_bean.setCurr_clouds_all(clouds_all);
            curr_weather_bean.setCurr_rain_last3hrs(rain_3h);
            curr_weather_bean.setCurr_snow_last3hrs(snow_3h);

            /*
            "dt": 1437667656,//time of this calculation, unix utc
             */

            long curr_calc_time = curr_weather_obj.getLong("dt");
            curr_weather_bean.setCurr_data_calc_time(curr_calc_time);

            /*
            "sys": {
        "sunrise": 1437597912,//unix utc
        "sunset": 1437638505//unix utc
    }
             */

            JSONObject sys_obj = curr_weather_obj.getJSONObject("sys");
            long sys_sunrise = sys_obj.getLong("sunrise");
            long sys_sunset = sys_obj.getLong("sunset");

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
