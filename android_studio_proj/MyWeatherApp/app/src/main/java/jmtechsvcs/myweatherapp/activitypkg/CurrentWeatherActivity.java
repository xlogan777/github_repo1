package jmtechsvcs.myweatherapp.activitypkg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.greendaosrcgen.WeatherIconTable;
import jmtechsvcs.myweatherapp.utils.BeanQueryParams;
import jmtechsvcs.myweatherapp.utils.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.utils.WeatherMapUtils;

/*
    this class handles the display of the current weather data for a city.
 */
public class CurrentWeatherActivity extends ActionBarActivity
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
        long city_id = bundle.getLong("city_id");

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

            //load city info to ui
            loadCityInfo(city_info_table);

            //load city time related data to ui.
            loadCityTimeInfo(curr_weather_data);

            //load city weather related to ui.
            loadCityWeatherInfo(curr_weather_data);

            //load the weather icon.
            loadCityWeatherIcon(weatherIconTable);
        }
        else
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
            ((TextView)findViewById(R.id.winddegs_val)).setText("");
            ((TextView)findViewById(R.id.clouds_val)).setText("");
            ((TextView)findViewById(R.id.rainlast3h_val)).setText("");
            ((TextView)findViewById(R.id.snowlast3h_val)).setText("");

            Log.d(LOGTAG,"null obj for curr city weather obj..load default data.");
        }
    }

    //this will load all the city related info.
    //city name, country code, lat/long.
    private void loadCityInfo(CityInfoTable cityInfoTable)
    {
        //set the city name and the country code.
        ((TextView)findViewById(R.id.cityname)).setText(cityInfoTable.getName());
        ((TextView)findViewById(R.id.countrycode)).setText(cityInfoTable.getCountry());
        ((TextView)findViewById(R.id.lat_val)).setText(cityInfoTable.getLat()+"");
        ((TextView)findViewById(R.id.lon_val)).setText(cityInfoTable.getLon()+"");
    }

    //this will load time related data about the city or data feed.
    private void loadCityTimeInfo(CityWeatherCurrCondTable currWeatherTable)
    {
        //set the time related data here
        ((TextView)findViewById(R.id.recentfeedtime_val)).setText(currWeatherTable.getCurr_data_calc_time()+"");
        ((TextView)findViewById(R.id.sr_time_val)).setText(currWeatherTable.getCurr_sys_sunrise_time()+"");
        ((TextView)findViewById(R.id.ss_time_val)).setText(currWeatherTable.getCurr_sys_sunset_time()+"");
    }

    //this will load all the weather related info for the city.
    private void loadCityWeatherInfo(CityWeatherCurrCondTable currWeatherTable)
    {
        ((TextView)findViewById(R.id.weatherid_val)).setText(currWeatherTable.getCurr_weather_id()+"");
        ((TextView)findViewById(R.id.weathermain_val)).setText(currWeatherTable.getCurr_weather_main()+"");
        ((TextView)findViewById(R.id.weatherdesc_val)).setText(currWeatherTable.getCurr_weather_desc()+"");
        ((TextView)findViewById(R.id.maintemp_val)).setText(currWeatherTable.getCurr_main_temp()+"");
        ((TextView)findViewById(R.id.mainpressure_val)).setText(currWeatherTable.getCurr_main_pressure()+"");
        ((TextView)findViewById(R.id.mainhumidity_val)).setText(currWeatherTable.getCurr_main_humidity()+"");
        ((TextView)findViewById(R.id.mintemp_val)).setText(currWeatherTable.getCurr_main_temp_min()+"");
        ((TextView)findViewById(R.id.maxtemp_val)).setText(currWeatherTable.getCurr_main_temp_max()+"");
        ((TextView)findViewById(R.id.sealevel_val)).setText(currWeatherTable.getCurr_main_sea_level()+"");
        ((TextView)findViewById(R.id.grndlevel_val)).setText(currWeatherTable.getCurr_main_grnd_level()+"");
        ((TextView)findViewById(R.id.windspeed_val)).setText(currWeatherTable.getCurr_wind_speed()+"");
        ((TextView)findViewById(R.id.winddegs_val)).setText(currWeatherTable.getCurr_wind_degs()+"");
        ((TextView)findViewById(R.id.clouds_val)).setText(currWeatherTable.getCurr_clouds_all()+"");
        ((TextView)findViewById(R.id.rainlast3h_val)).setText(currWeatherTable.getCurr_rain_last3hrs()+"");
        ((TextView)findViewById(R.id.snowlast3h_val)).setText(currWeatherTable.getCurr_snow_last3hrs()+"");
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
                Bitmap bitmap = WeatherMapUtils.readPngFile(img_path);

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
            Log.d(LOGTAG,WeatherMapUtils.getStackTrace(e));
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
