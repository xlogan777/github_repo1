package jmtechsvcs.myweatherapp.dbpkg;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import jmtechsvcs.myweatherapp.MyWeatherApplication;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DaoSession;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTableDao;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/**
 * Created by jimmy on 7/23/2015.
 */
public class WeatherDbProcessing
{
    private final static String LOGTAG = "WthrJsonToDbProcessing";

    //helper function allows to a dao session from a context reference.
    private static DaoSession getDaoSession(Context context)
    {
        //get the application ctx for this app.
        MyWeatherApplication weather_app = (MyWeatherApplication)context.getApplicationContext();

        //get a stored dao session.
        DaoSession dao_session = weather_app.getDaoSession();

        //return the dao session from the context.
        return dao_session;
    }

    /*
        this will create a image file in the local file system for this image icon and
        save this path to the DB using the dao for this table.
     */
    public static void updateWeatherIcon(String iconId, String iconUrl, byte [] rawImage, Context context)
    {
        try
        {
            //get the dao session.
            DaoSession daoSession = getDaoSession(context);

            //get the dao from the dao session.
            WeatherIconTableDao dao = daoSession.getWeatherIconTableDao();

            //check to see if the icon exists in the DB first.
            List<WeatherIconTable> items =  (List<WeatherIconTable>)dao.queryBuilder().where
                    (
                            WeatherIconTableDao.Properties.Icon_id.eq(iconId)
                    ).list();

            if(items.size() == 0)
            {
                //add this image to the file system first, and confirm this worked before u
                //add it to the DB.
                String image_path = WeatherAppUtils.saveByteToPngFile(context, iconId, rawImage);

                //if we are here, it is because we didnt generate an exception, we need
                //to save this data to the DB, with the path to the image file.
                if(image_path != null && image_path.length() > 0)
                {
                    //create bean to set data for this bean.
                    WeatherIconTable iconTable = new WeatherIconTable();

                    //set the data here.
                    iconTable.setIcon_id(iconId);
                    iconTable.setIcon_url(iconUrl);

                    //for now dont save the image file raw in the DB.
                    //iconTable.setImage_raw(rawImage);

                    iconTable.setImage_path(image_path);

                    //save bean using dao
                    dao.insert(iconTable);
                }
                else
                {
                    Log.d(LOGTAG,"issue with the img path, either null or len = 0");
                }
            }
            else
            {
                Log.d(LOGTAG,"size of search = "+items.size()+", dont save to filesystem, or update db.");
            }
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }
    }

    //this will parse the json data and use a dao to save the obj to db.
    public static CityWeatherCurrCondTable updateCurrWeatherToDb(String jsonInput, ByteArrayInputStream bis, Context context)
    {
        CityWeatherCurrCondTable rv = null;

        try
        {
            //get the dao session.
            DaoSession daoSession = getDaoSession(context);

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

            long weather_id = WeatherAppUtils.getLongVal(array_item, "id");
            String weather_main = WeatherAppUtils.getStringVal(array_item, "main");
            String weather_desc = WeatherAppUtils.getStringVal(array_item, "description");
            String weather_icon = WeatherAppUtils.getStringVal(array_item, "icon");
            curr_weather_bean.setCurr_weather_id(weather_id);
            curr_weather_bean.setCurr_weather_main(weather_main);
            curr_weather_bean.setCurr_weather_desc(weather_desc);
            curr_weather_bean.setCurr_weather_icon(weather_icon);

            JSONObject main_obj = curr_weather_obj.optJSONObject("main");
            double main_temp = WeatherAppUtils.getDoubleVal(main_obj, "temp");
            long main_pressure = WeatherAppUtils.getLongVal(main_obj, "pressure");
            long main_humidity = WeatherAppUtils.getLongVal(main_obj, "humidity");
            double main_temp_min = WeatherAppUtils.getDoubleVal(main_obj, "temp_min");
            double main_temp_max = WeatherAppUtils.getDoubleVal(main_obj, "temp_max");
            long main_sea_level = WeatherAppUtils.getLongVal(main_obj, "sea_level");
            long main_grnd_level = WeatherAppUtils.getLongVal(main_obj, "grnd_level");
            curr_weather_bean.setCurr_main_temp(main_temp);
            curr_weather_bean.setCurr_main_pressure(main_pressure);
            curr_weather_bean.setCurr_main_humidity(main_humidity);
            curr_weather_bean.setCurr_main_temp_min(main_temp_min);
            curr_weather_bean.setCurr_main_temp_max(main_temp_max);
            curr_weather_bean.setCurr_main_sea_level(main_sea_level);
            curr_weather_bean.setCurr_main_grnd_level(main_grnd_level);

            JSONObject wind_obj = curr_weather_obj.optJSONObject("wind");
            double wind_speed = WeatherAppUtils.getDoubleVal(wind_obj, "speed");
            long wind_degs = WeatherAppUtils.getLongVal(wind_obj, "deg");
            curr_weather_bean.setCurr_wind_speed(wind_speed);
            curr_weather_bean.setCurr_wind_degs(wind_degs);

            JSONObject clouds_obj = curr_weather_obj.optJSONObject("clouds");
            long clouds_all = WeatherAppUtils.getLongVal(clouds_obj, "all");

            JSONObject rain_obj = curr_weather_obj.optJSONObject("rain");
            double rain_3h = WeatherAppUtils.getDoubleVal(rain_obj, "3h");

            JSONObject snow_obj = curr_weather_obj.optJSONObject("snow");
            double snow_3h = WeatherAppUtils.getDoubleVal(snow_obj, "3h");

            curr_weather_bean.setCurr_clouds_all(clouds_all);
            curr_weather_bean.setCurr_rain_last3hrs(rain_3h);
            curr_weather_bean.setCurr_snow_last3hrs(snow_3h);

            long curr_calc_time = WeatherAppUtils.getLongVal(curr_weather_obj, "dt");
            curr_weather_bean.setCurr_data_calc_time(curr_calc_time);

            JSONObject sys_obj = curr_weather_obj.optJSONObject("sys");
            long sys_sunrise = WeatherAppUtils.getLongVal(sys_obj, "sunrise");
            long sys_sunset = WeatherAppUtils.getLongVal(sys_obj, "sunset");
            curr_weather_bean.setCurr_sys_sunrise_time(sys_sunrise);
            curr_weather_bean.setCurr_sys_sunset_time(sys_sunset);

            //parse the xml stream here.
            //create a factory obj for the xml pull parser.
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();

            //create an xml pull parser from the pull parser factory.
            XmlPullParser parser = xmlFactoryObject.newPullParser();

            //set the input to the xml pull parser here.
            parser.setInput(bis, null);

            //get the event type and begin the parsing.
            int event = parser.getEventType();

            //check if u havent reached the end.
            while (event != XmlPullParser.END_DOCUMENT)
            {
                //get the name of the tag
                String name = parser.getName();

                //this will switch on the different event, tag names here
                //like <abc>, or </abc> which are start and end tags.
                switch (event)
                {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(LOGTAG,"start doc parsing.");
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.START_TAG:

                        //need to check for null if the attribute doesnt exist, when requested.
                        //check for the sun tag
                        if(name.equals("sun"))
                        {
                            //need to create long values here. for time
                            String sun_rise = parser.getAttributeValue(null,"rise");
                            long sun_rise_val = WeatherAppUtils.getUtcSecondsFromDateString(sun_rise);

                            String sun_set = parser.getAttributeValue(null, "set");
                            long sun_set_val = WeatherAppUtils.getUtcSecondsFromDateString(sun_set);

                            //update pojo with this times.
                            curr_weather_bean.setCurr_sys_sunrise_time(sun_rise_val);
                            curr_weather_bean.setCurr_sys_sunset_time(sun_set_val);
                        }
                        else if(name.equals("lastupdate"))
                        {
                            //need to create long value here for time
                            String lastupdate = parser.getAttributeValue(null,"value");
                            long last_update_val = WeatherAppUtils.getUtcSecondsFromDateString(lastupdate);
                            curr_weather_bean.setCurr_data_calc_time(last_update_val);
                        }
                        else if(name.equals("speed"))
                        {
                            //this is a string.
                            String speed_name = parser.getAttributeValue(null,"name");
                            String result = WeatherAppUtils.getDefaultStringDiplayString(speed_name);
                            if(result.length() != 0)
                            {
                                curr_weather_bean.setCurr_wind_speed_name(result);
                            }
                            else
                            {
                                curr_weather_bean.setCurr_wind_speed_name(speed_name);
                            }
                        }
                        //NEW FIELDS BEING ADDED TO POJO.
                        else if(name.equals("direction"))
                        {
                            //this is a string
                            String wind_dir = parser.getAttributeValue(null,"code");

                            String result = WeatherAppUtils.getDefaultStringDiplayString(wind_dir);
                            if(result.length() != 0)
                            {
                                curr_weather_bean.setCurr_wind_dirr_code(result);
                            }
                            else
                            {
                                curr_weather_bean.setCurr_wind_dirr_code(wind_dir);
                            }
                        }
                        else if(name.equals("visibility"))
                        {
                            //long, meter conv to miles.
                            String visibility = parser.getAttributeValue(null,"value");
                            String result = WeatherAppUtils.getDefaultStringDiplayString(visibility);
                            if(result.length() != 0)
                            {
                                curr_weather_bean.setCurr_visibility(WeatherAppUtils.DEFAULT_lONG_VAL);
                            }
                            else
                            {
                                long vis = Long.parseLong(visibility);
                                curr_weather_bean.setCurr_visibility(vis);
                            }
                        }
                        else if(name.equals("precipitation"))
                        {
                            //this is a string.
                            String precip_mode = parser.getAttributeValue(null,"mode");
                            String result = WeatherAppUtils.getDefaultStringDiplayString(precip_mode);
                            if(result.length() != 0)
                            {
                                curr_weather_bean.setPrecipitation_mode(result);
                            }
                            else
                            {
                                curr_weather_bean.setPrecipitation_mode(precip_mode);
                            }

                            String precip_value = parser.getAttributeValue(null, "value");
                            result = WeatherAppUtils.getDefaultStringDiplayString(precip_value);
                            if(result.length() != 0)
                            {
                                curr_weather_bean.setPrecipitation_value(WeatherAppUtils.DEFAULT_DOUBLE_VAL);
                            }
                            else
                            {
                                double precip_val = Double.parseDouble(precip_value);
                                curr_weather_bean.setPrecipitation_value(precip_val);
                            }
                        }
                        //NEW FIELDS BEING ADDED TO POJO

                        break;

                    case XmlPullParser.END_TAG:
                        break;
                }

                //get the next event.
                event = parser.next();
            }
            Log.d(LOGTAG,"end doc parsing");

            //save the data to the db.
            curr_weather_dao.insertOrReplace(curr_weather_bean);

            //return this bean back to the caller.
            rv = curr_weather_bean;
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }

        return rv;
    }

    //this will update the dao with the json weather station data in the json string to the DB.
    //for this geo location stream.
    public static void updateCurrentWeatherStationInfoGeo(String jsonInput, Context context, long cityId)
    {
        try
        {
            //get the dao session.
            DaoSession daoSession = getDaoSession(context);

            //get the dao to be used later.
            WeatherStationInfoTableDao weather_station_dao =
                    daoSession.getWeatherStationInfoTableDao();

            //the weather stations are a json array of objs here.
            JSONArray weather_station_array = new JSONArray(jsonInput);

            //len of the json array.
            int size = weather_station_array.length();

            int index = 0;

            //loop over the json array.
            for(int i = 0; i < size; i++)
            {
                //track which index we are at.
                //allow for other items to be parsed if we fail some.
                index = i;
                try
                {
                    //get the obj from the json array.
                    JSONObject array_obj = weather_station_array.getJSONObject(i);

                    //create new bean obj each time we update data here.
                    WeatherStationInfoTable weather_station_bean = new WeatherStationInfoTable();

                    //this sets the city id for this bean
                    weather_station_bean.setCity_id(cityId);

                    //must have station information.
                    JSONObject station_obj = array_obj.getJSONObject("station");
                    String station_name = WeatherAppUtils.getStringVal(station_obj, "name");
                    long station_id = WeatherAppUtils.getLongVal(station_obj, "id");
                    weather_station_bean.setStation_name(station_name);
                    weather_station_bean.setStation_id(station_id);

                    //must have the last obj
                    JSONObject last_station_obj = array_obj.getJSONObject("last");

                    //all these objs below may be optional.
                    JSONObject main_station_obj = last_station_obj.optJSONObject("main");
                    double main_temp = WeatherAppUtils.getDoubleVal(main_station_obj, "temp");
                    long main_pressure = WeatherAppUtils.getLongVal(main_station_obj, "pressure");
                    long main_humidity = WeatherAppUtils.getLongVal(main_station_obj, "humidity");
                    weather_station_bean.setStation_temp(main_temp);
                    weather_station_bean.setStation_pressure(main_pressure);
                    weather_station_bean.setStation_humidity(main_humidity);

                    JSONObject last_wind = last_station_obj.optJSONObject("wind");
                    double last_wind_speed = WeatherAppUtils.getDoubleVal(last_wind, "speed");
                    long last_wind_deg = WeatherAppUtils.getLongVal(last_wind, "deg");
                    double last_wind_gust = WeatherAppUtils.getDoubleVal(last_wind, "gust");
                    weather_station_bean.setStation_wind_speed(last_wind_speed);
                    weather_station_bean.setStation_wind_deg(last_wind_deg);
                    weather_station_bean.setStation_wind_gust(last_wind_gust);

                    JSONObject last_visibility = last_station_obj.optJSONObject("visibility");
                    long last_visibility_distance = WeatherAppUtils.getLongVal(last_visibility, "distance");
                    weather_station_bean.setStation_visibility_dist(last_visibility_distance);

                    JSONObject last_calc = last_station_obj.optJSONObject("calc");
                    double last_calc_dewpt = WeatherAppUtils.getDoubleVal(last_calc, "dewpoint");
                    double last_calc_humidex = WeatherAppUtils.getDoubleVal(last_calc, "humidex");
                    weather_station_bean.setStation_calc_dewpt(last_calc_dewpt);
                    weather_station_bean.setStation_calc_humidex(last_calc_humidex);

                    JSONArray last_clouds_array = last_station_obj.optJSONArray("clouds");
                    if(last_clouds_array != null){
                        //only get data from the first item.
                        JSONObject clouds_item = last_clouds_array.getJSONObject(0);
                        long clouds_item_dist = WeatherAppUtils.getLongVal(clouds_item, "distance");
                        String clouds_item_cond = WeatherAppUtils.getStringVal(clouds_item, "condition");
                        weather_station_bean.setStation_clouds_dist(clouds_item_dist);
                        weather_station_bean.setStation_clouds_cond(clouds_item_cond);
                    }

                    JSONObject last_rain = last_station_obj.optJSONObject("rain");
                    long last_rain_1h = WeatherAppUtils.getLongVal(last_rain, "1h");
                    long last_rain_24h = WeatherAppUtils.getLongVal(last_rain, "24h");
                    long last_rain_today = WeatherAppUtils.getLongVal(last_rain, "today");
                    weather_station_bean.setStation_rain_1h(last_rain_1h);
                    weather_station_bean.setStation_rain_24h(last_rain_24h);
                    weather_station_bean.setStation_rain_today(last_rain_today);

                    long last_dt = WeatherAppUtils.getLongVal(last_station_obj, "dt");
                    weather_station_bean.setLast_update_time(last_dt);

                    //save the data to the db.
                    weather_station_dao.insertOrReplace(weather_station_bean);
                }
                catch(Exception e)
                {
                    Log.d(LOGTAG,"issues with item in weather station array loc = "+index);
                    Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
                }
            }//for loop
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }
    }

    /*
        added annotation for unchecked cast since we know it it ok.
        uses the input args to get the correct java bean to return based on compile time
        checking of the data type passed in.
     */
    @SuppressWarnings("unchecked")
    public static <CityBeanType> CityBeanType getBeanByQueryParams
    (BeanQueryParams queryParams, Context context, CityBeanType beanType)
    {
        //data type used in this generic function.
        CityBeanType rv = null;

        try
        {
            //get the dao session.
            DaoSession daoSession = getDaoSession(context);

            //item list.
            List<CityBeanType> items = new ArrayList<CityBeanType>();

            if(beanType instanceof CityInfoTable &&
               queryParams.getQueryParamType() == BeanQueryParams.T_Query_Param_Type.E_CITY_INFO_TABLE_TYPE)
            {
                //get the current city weather dao.
                CityInfoTableDao dao = daoSession.getCityInfoTableDao();

                //get the java bean using the dao obj but use the city id to find it.
                items =  (List<CityBeanType>)dao.queryBuilder().where
                        (
                                CityInfoTableDao.Properties.City_id.eq(queryParams.getCityId())
                        ).list();
            }
            else if(beanType instanceof CityWeatherCurrCondTable &&
                    queryParams.getQueryParamType() == BeanQueryParams.T_Query_Param_Type.E_CURR_CITY_WEATHER_TABLE_TYPE)
            {
                CityWeatherCurrCondTableDao dao = daoSession.getCityWeatherCurrCondTableDao();

                //get the java bean using the dao obj but use the city id to find it.
                items = (List<CityBeanType>)dao.queryBuilder().where
                        (
                                CityWeatherCurrCondTableDao.Properties.City_id.eq(queryParams.getCityId())
                        ).list();
            }

            else if(beanType instanceof WeatherIconTable &&
                    queryParams.getQueryParamType() == BeanQueryParams.T_Query_Param_Type.E_IMG_ICON_TABLE_TYPE)
            {
                WeatherIconTableDao dao = daoSession.getWeatherIconTableDao();

                //get the java bean using the dao obj but use the city id to find it.
                items = (List<CityBeanType>)dao.queryBuilder().where
                        (
                                WeatherIconTableDao.Properties.Icon_id.eq(queryParams.getIconId())
                        ).list();
            }

            if(items.size() == 1)
            {
                rv = (CityBeanType)items.get(0);
            }
            else if(items.size() == 0)
            {
                Log.d(LOGTAG, "didnt find the item yet for data type = "+beanType.getClass().getName());
            }
            else
            {
                Log.d(LOGTAG, "list size = " + items.size() + ", this is an issue for type = "+beanType.getClass().getName());
            }
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }

        return rv;
    }

    //http://stackoverflow.com/questions/12927859/why-greendao-doesnt-support-like-operator-completely
    //check the last item.
    @SuppressWarnings("unchecked")
    public static <CityBeanType> List<CityBeanType> getBeanByQueryParamsList
            (BeanQueryParams queryParams, Context context, CityBeanType beanType)
    {
        //data type used in this generic function.
        List<CityBeanType> rv = null;

        try
        {
            //get the dao session.
            DaoSession daoSession = getDaoSession(context);

            //item list.
            List<CityBeanType> items = new ArrayList<CityBeanType>();

            if(beanType instanceof CityInfoTable &&
                    queryParams.getQueryParamType() == BeanQueryParams.T_Query_Param_Type.E_CITY_INFO_TABLE_LIST_TYPE)
            {
                //get the current city weather dao.
                CityInfoTableDao dao = daoSession.getCityInfoTableDao();

                //check to see if we have a coutry code..if so us it in the query.
                if(queryParams.getCountryCode() != null && queryParams.getCountryCode().length() > 0)
                {
                    items = (List<CityBeanType>)dao.queryBuilder().where
                            (
                                    CityInfoTableDao.Properties.Name.like("%"+queryParams.getCityName()+"%"),
                                    CityInfoTableDao.Properties.Country.like("%"+queryParams.getCountryCode()+"%")
                            ).list();
                }

                //check just for city name if we didnt find anything with cityname and CC.
                //doing query with just city name
                if(items.size() == 0)
                {
                    items = (List<CityBeanType>)dao.queryBuilder().where
                            (CityInfoTableDao.Properties.Name.like("%"+queryParams.getCityName()+"%")).list();
                }

                //return back the list to the caller.
                rv = items;
            }
            else if(beanType instanceof WeatherStationInfoTable &&
                    queryParams.getQueryParamType() == BeanQueryParams.T_Query_Param_Type.E_WEATHER_STATION_TABLE_LIST_TYPE)
            {
                //get the dao for weather station info.
                WeatherStationInfoTableDao dao = daoSession.getWeatherStationInfoTableDao();

                //get the java bean using the dao obj but use the city id to find it.
                items =  (List<CityBeanType>)dao.queryBuilder().where
                        (
                                WeatherStationInfoTableDao.Properties.City_id.eq(queryParams.getCityId())
                        ).list();

                //return back the list to the caller.
                rv = items;
            }

            if(items.size() == 0)
            {
                Log.d(LOGTAG,"no items found");
            }
            else
            {
                Log.d(LOGTAG, "list size = " + items.size() + ", this is a type = "+beanType.getClass().getName());
            }
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }

        return rv;
    }
}
