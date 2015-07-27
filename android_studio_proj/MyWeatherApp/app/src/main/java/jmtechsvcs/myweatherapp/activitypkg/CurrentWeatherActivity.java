package jmtechsvcs.myweatherapp.activitypkg;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.utils.WeatherDbProcessing;

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

        //get the current weather using the city id.
        CityWeatherCurrCondTable curr_weather_data = new CityWeatherCurrCondTable();
        curr_weather_data = WeatherDbProcessing.getBeanByCityId(city_id, getApplicationContext(), curr_weather_data);

        //get the current city info using city id.
        CityInfoTable city_info_table = new CityInfoTable();
        city_info_table = WeatherDbProcessing.getBeanByCityId(city_id, getApplicationContext(), city_info_table);

        //make sure that the data valid before u display it.
        if(curr_weather_data != null && city_info_table != null)
        {
            Log.d(LOGTAG,"load data, city id = "+curr_weather_data.getCity_id()+
                    ", temp = "+curr_weather_data.getCurr_main_temp());

            //load city info to ui
            loadCityInfo(city_info_table);

            //load city time related data to ui.
            loadCityTimeInfo(curr_weather_data);

            //load city weather related to ui.
            loadCityWeatherInfo(curr_weather_data);

            //load the weather icon.
            loadCityWeatherIcon();
        }
        else
        {
            Log.d(LOGTAG,"null obj for curr city weather obj..load default data.");
        }
    }

    //this will load all the city related info.
    //city name, country code, lat/long.
    private void loadCityInfo(CityInfoTable cityInfoTable)
    {
        //set the city name and the country code.
        ((TextView)findViewById(R.id.city_name)).setText(cityInfoTable.getName());
        ((TextView)findViewById(R.id.countrycode)).setText(cityInfoTable.getCountry());
        ((TextView)findViewById(R.id.lat_val)).setText(cityInfoTable.getLat()+"");
        ((TextView)findViewById(R.id.lon_val)).setText(cityInfoTable.getLon()+"");
    }

    //this will load time related data about the city or data feed.
    private void loadCityTimeInfo(CityWeatherCurrCondTable currWeatherTable)
    {
        //set the time related data here
        ((TextView)findViewById(R.id.recentfeedtime)).setText(currWeatherTable.getCurr_data_calc_time()+"");
        ((TextView)findViewById(R.id.sunrisetime)).setText(currWeatherTable.getCurr_sys_sunrise_time()+"");
        ((TextView)findViewById(R.id.sunsettime)).setText(currWeatherTable.getCurr_sys_sunset_time()+"");
    }

    //this will load all the weather related info for the city.
    private void loadCityWeatherInfo(CityWeatherCurrCondTable currWeatherTable)
    {
        ((TextView)findViewById(R.id.weatherid)).setText(currWeatherTable.getCurr_weather_id()+"");
        ((TextView)findViewById(R.id.weathermain)).setText(currWeatherTable.getCurr_weather_main()+"");
        ((TextView)findViewById(R.id.weatherdesc)).setText(currWeatherTable.getCurr_weather_desc()+"");
        ((TextView)findViewById(R.id.maintemp)).setText(currWeatherTable.getCurr_main_temp()+"");
        ((TextView)findViewById(R.id.mainpressure)).setText(currWeatherTable.getCurr_main_pressure()+"");
        ((TextView)findViewById(R.id.mainhumidity)).setText(currWeatherTable.getCurr_main_humidity()+"");
        ((TextView)findViewById(R.id.mintemp)).setText(currWeatherTable.getCurr_main_temp_min()+"");
        ((TextView)findViewById(R.id.maxtemp)).setText(currWeatherTable.getCurr_main_temp_max()+"");
        ((TextView)findViewById(R.id.sealevel)).setText(currWeatherTable.getCurr_main_sea_level()+"");
        ((TextView)findViewById(R.id.grndlevel)).setText(currWeatherTable.getCurr_main_grnd_level()+"");
        ((TextView)findViewById(R.id.windspeed)).setText(currWeatherTable.getCurr_wind_speed()+"");
        ((TextView)findViewById(R.id.winddegs)).setText(currWeatherTable.getCurr_wind_degs()+"");
        ((TextView)findViewById(R.id.clouds)).setText(currWeatherTable.getCurr_clouds_all()+"");
        ((TextView)findViewById(R.id.rainlast3h)).setText(currWeatherTable.getCurr_rain_last3hrs()+"");
        ((TextView)findViewById(R.id.snowlast3h)).setText(currWeatherTable.getCurr_snow_last3hrs()+"");
    }

    //this will load the weather icon.
    private void loadCityWeatherIcon()
    {
        //TODO: load the weather icon.
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
