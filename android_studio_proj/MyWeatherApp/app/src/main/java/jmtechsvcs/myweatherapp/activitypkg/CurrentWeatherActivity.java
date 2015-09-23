package jmtechsvcs.myweatherapp.activitypkg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTable;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.utilspkg.MathUtils;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/*
    this class handles the display of the current weather data for a city.
 */
public class CurrentWeatherActivity extends AppCompatActivity
{
    private static final String LOGTAG = "CurrentWeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        //get the intent to use the data from the parent activity.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //this is the city id needed to ge the current weather data.
        long city_id = bundle.getLong("cityId");

        //get the application context.
        Context context = getApplicationContext();

        //get the current weather using the city id. build the query params for each specific data type
        //being accessed.
        BeanQueryParams qp = new BeanQueryParams();
        qp.setCityId(city_id);//city id is used for both weather cond, and city info.

        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_CURR_CITY_WEATHER_TABLE_TYPE);
        CityWeatherCurrCondTable curr_weather_data = new CityWeatherCurrCondTable();
        curr_weather_data = WeatherDbProcessing.getBeanByQueryParams(qp, context, curr_weather_data);

        //get the current city info using city id.
        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_CITY_INFO_TABLE_TYPE);
        CityInfoTable city_info_table = new CityInfoTable();
        city_info_table = WeatherDbProcessing.getBeanByQueryParams(qp, context, city_info_table);

        //make sure that the data valid before u display it.
        if(curr_weather_data != null &&
           city_info_table != null)
        {
            Log.d(LOGTAG,"load data, city id = "+curr_weather_data.getCity_id()+
                    ", temp = "+curr_weather_data.getCurr_main_temp());

            //get the image icon and allow to have it loaded to the image view.
            //setup the query params for access to the icon data.
            qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_IMG_ICON_TABLE_TYPE);
            qp.setCityId(-1);
            qp.setIconId(curr_weather_data.getCurr_weather_icon());

            WeatherIconTable weatherIconTable = new WeatherIconTable();
            weatherIconTable = WeatherDbProcessing.getBeanByQueryParams(qp, context, weatherIconTable);

            //load the weather icon.
            if(weatherIconTable != null)
            {
                loadCityWeatherIcon(weatherIconTable);

                //load city info to ui
                loadCityInfo(city_info_table);

                //load city time related data to ui.
                loadCityTimeInfo(curr_weather_data);

                //load city weather related to ui.
                loadCityWeatherInfo(curr_weather_data);
            }
            else
            {
                setDefaultView();
            }
        }
        else
        {
           setDefaultView();
        }
    }

    public void setDefaultView()
    {
        ((TextView)findViewById(R.id.cityname)).setText("no city data available");
        ((TextView)findViewById(R.id.countrycode)).setText("no city data available");

        ((TextView)findViewById(R.id.lat_val)).setText("");
        ((TextView)findViewById(R.id.lon_val)).setText("");
        ((TextView)findViewById(R.id.recentfeedtime_val)).setText("");
        ((TextView)findViewById(R.id.sr_time_val)).setText("");
        ((TextView)findViewById(R.id.ss_time_val)).setText("");
        ((TextView)findViewById(R.id.weatherid_val)).setText("");
        ((TextView)findViewById(R.id.weathermain_val)).setText("");
        ((TextView)findViewById(R.id.weatherdesc_val)).setText("");
        ((TextView)findViewById(R.id.maintemp_val)).setText("");
        ((TextView)findViewById(R.id.mainpressure_val)).setText("");
        ((TextView)findViewById(R.id.mainhumidity_val)).setText("");
        ((TextView)findViewById(R.id.mintemp_val)).setText("");
        ((TextView)findViewById(R.id.maxtemp_val)).setText("");
        ((TextView)findViewById(R.id.sealevel_val)).setText("");
        ((TextView)findViewById(R.id.grndlevel_val)).setText("");
        ((TextView)findViewById(R.id.windspeed_val)).setText("");
        ((TextView)findViewById(R.id.clouds_val)).setText("");

        //NEW XML BASED FIELDS.
        ((TextView)findViewById(R.id.windspeedname_val)).setText("");
        ((TextView)findViewById(R.id.windspeeddirrection_val)).setText("");
        ((TextView)findViewById(R.id.visibility_val)).setText("");
        ((TextView)findViewById(R.id.precipitationmode_val)).setText("");
        ((TextView)findViewById(R.id.precipitationvalue_val)).setText("");


        //not being used, replaced with xml based data.
//        ((TextView)findViewById(R.id.winddegs_val)).setText("");
//        ((TextView)findViewById(R.id.rainlast3h_val)).setText("");
//        ((TextView)findViewById(R.id.snowlast3h_val)).setText("");

        //added more fields to show data.

        Log.d(LOGTAG,"null obj for curr city weather obj..load default data.");
    }

    //this will load all the city related info.
    //city name, country code, lat/long.
    private void loadCityInfo(CityInfoTable cityInfoTable)
    {
        //set the city name and the country code.
        ((TextView)findViewById(R.id.cityname)).setText(cityInfoTable.getName());
        ((TextView)findViewById(R.id.countrycode)).setText(cityInfoTable.getCountry());

        //do conversion here
        ((TextView)findViewById(R.id.lat_val)).setText(MathUtils.getDegreeString(cityInfoTable.getLat())+"");
        ((TextView)findViewById(R.id.lon_val)).setText(MathUtils.getDegreeString(cityInfoTable.getLon())+"");
    }

    //this will load time related data about the city or data feed.
    private void loadCityTimeInfo(CityWeatherCurrCondTable currWeatherTable)
    {
        //set the time related data here
        //do conversions here.
        String result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_data_calc_time());

        if(result.length() == 0)
            ((TextView)findViewById(R.id.recentfeedtime_val)).setText(
                WeatherAppUtils.getUtcFromUtcSeconds(currWeatherTable.getCurr_data_calc_time())+"");
        else
            ((TextView)findViewById(R.id.recentfeedtime_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_sys_sunrise_time());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.sr_time_val)).setText(
                    WeatherAppUtils.getUtcFromUtcSeconds(currWeatherTable.getCurr_sys_sunrise_time())+"");
        else
            ((TextView)findViewById(R.id.sr_time_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_sys_sunset_time());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.ss_time_val)).setText(
                    WeatherAppUtils.getUtcFromUtcSeconds(currWeatherTable.getCurr_sys_sunset_time())+"");
        else
            ((TextView)findViewById(R.id.ss_time_val)).setText(result);
    }

    //this will load all the weather related info for the city.
    private void loadCityWeatherInfo(CityWeatherCurrCondTable currWeatherTable)
    {
        String result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_weather_id());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.weatherid_val)).setText(
                    currWeatherTable.getCurr_weather_id() + ""
            );
        else
            ((TextView)findViewById(R.id.weatherid_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(currWeatherTable.getCurr_weather_main());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.weathermain_val)).setText(currWeatherTable.getCurr_weather_main()+"");
        else
            ((TextView)findViewById(R.id.weathermain_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(
                currWeatherTable.getCurr_weather_desc()
        );
        if(result.length() == 0)
            ((TextView)findViewById(R.id.weatherdesc_val)).setText(currWeatherTable.getCurr_weather_desc()+"");
        else
            ((TextView)findViewById(R.id.weatherdesc_val)).setText(result);

        //do conversions here.
        result = WeatherAppUtils.getDefaultStringDisplayDouble(currWeatherTable.getCurr_main_temp());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.maintemp_val)).setText(MathUtils.getTempString(
                    currWeatherTable.getCurr_main_temp())+"");
        else
            ((TextView)findViewById(R.id.maintemp_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_main_pressure());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.mainpressure_val)).setText(MathUtils.getPressureString(
                    currWeatherTable.getCurr_main_pressure()
            )+"");
        else
            ((TextView)findViewById(R.id.mainpressure_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(
                currWeatherTable.getCurr_main_humidity()
        );
        if(result.length() == 0)
            ((TextView)findViewById(R.id.mainhumidity_val)).setText(MathUtils.getPercentString(
                    currWeatherTable.getCurr_main_humidity()
            )+"");
        else
            ((TextView)findViewById(R.id.mainhumidity_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(currWeatherTable.getCurr_main_temp_min());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.mintemp_val)).setText(
                        MathUtils.getTempString(currWeatherTable.getCurr_main_temp_min()) + "");
        else
            ((TextView)findViewById(R.id.mintemp_val)).setText(result);


        result = WeatherAppUtils.getDefaultStringDisplayDouble(
                currWeatherTable.getCurr_main_temp_max()
        );
        if(result.length() == 0)
            ((TextView)findViewById(R.id.maxtemp_val)).setText(MathUtils.getTempString(currWeatherTable.getCurr_main_temp_max())+"");
        else
            ((TextView)findViewById(R.id.maxtemp_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_main_sea_level());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.sealevel_val)).setText(MathUtils.getPressureString(currWeatherTable.getCurr_main_sea_level())+"");
        else
            ((TextView)findViewById(R.id.sealevel_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(
                currWeatherTable.getCurr_main_grnd_level()
        );
        if(result.length() == 0)
            ((TextView)findViewById(R.id.grndlevel_val)).setText(MathUtils.getPressureString(
                    currWeatherTable.getCurr_main_grnd_level()
            )+"");
        else
            ((TextView)findViewById(R.id.grndlevel_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(currWeatherTable.getCurr_wind_speed());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.windspeed_val)).setText(MathUtils.getVelocityString(currWeatherTable.getCurr_wind_speed())+"");
        else
            ((TextView)findViewById(R.id.windspeed_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_clouds_all());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.clouds_val)).setText(
                    MathUtils.getPercentString(currWeatherTable.getCurr_clouds_all()) + ""
            );
        else
            ((TextView)findViewById(R.id.clouds_val)).setText(result);

        //ADDED NEW FIELDS BEING RETRIEVED FROM XML.
        result = WeatherAppUtils.getDefaultStringDisplayString(currWeatherTable.getCurr_wind_speed_name());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.windspeedname_val)).setText(currWeatherTable.getCurr_wind_speed_name()+"");
        else
            ((TextView)findViewById(R.id.windspeedname_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(currWeatherTable.getCurr_wind_dirr_code());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.windspeeddirrection_val)).setText(currWeatherTable.getCurr_wind_dirr_code()+"");
        else
            ((TextView)findViewById(R.id.windspeeddirrection_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(currWeatherTable.getPrecipitation_mode());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.precipitationmode_val)).setText(currWeatherTable.getPrecipitation_mode()+"");
        else
            ((TextView)findViewById(R.id.precipitationmode_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_visibility());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.visibility_val)).setText(MathUtils.getDistanceString(currWeatherTable.getCurr_visibility(),'M')+"");
        else
            ((TextView)findViewById(R.id.visibility_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(currWeatherTable.getPrecipitation_value());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.precipitationvalue_val)).setText(MathUtils.getPercentWhole(currWeatherTable.getPrecipitation_value())+"");
        else
            ((TextView)findViewById(R.id.precipitationvalue_val)).setText(result);

        //THIS UI FOR THESE FIELDS ARE BEING REPLACED BY XML BASED FIELDS.
//        result = WeatherAppUtils.getDefaultStringDisplayDouble(currWeatherTable.getCurr_wind_degs());
//        if(result.length() == 0)
//            ((TextView)findViewById(R.id.winddegs_val)).setText(MathUtils.getDegreeString(
//                    currWeatherTable.getCurr_wind_degs()
//            )+"");
//        else
//            ((TextView)findViewById(R.id.winddegs_val)).setText(result);

//        result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_rain_last3hrs());
//        if(result.length() == 0)
//            ((TextView)findViewById(R.id.rainlast3h_val)).setText(MathUtils.convertToRequestedDistanceUnit(currWeatherTable.getCurr_rain_last3hrs(),'I')+"");
//        else
//            ((TextView)findViewById(R.id.rainlast3h_val)).setText(result);
//
//        result = WeatherAppUtils.getDefaultStringDisplayLong(currWeatherTable.getCurr_snow_last3hrs());
//        if(result.length() == 0)
//            ((TextView)findViewById(R.id.snowlast3h_val)).setText(
//                    MathUtils.convertToRequestedDistanceUnit(
//                            currWeatherTable.getCurr_snow_last3hrs(), 'I'
//                    ) + ""
//            );
//        else
//            ((TextView)findViewById(R.id.snowlast3h_val)).setText(result);
    }

    //this will load the weather icon.
    private void loadCityWeatherIcon(WeatherIconTable weatherIconTable)
    {
        try
        {
            //get the image path from the bean obj.
            String img_path = weatherIconTable.getImage_path();

            if(img_path != null && img_path.length() > 0)
            {
                Bitmap bitmap = WeatherAppUtils.readPngFile(img_path, 150, 150);

                if(bitmap != null)
                {
                    //set the image bit map here.
                    ((ImageView)findViewById(R.id.weather_icon)).setImageBitmap(bitmap);
                    Log.d(LOGTAG,"loaded the image bit map...bit map file = "+img_path);
                }
            }
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
