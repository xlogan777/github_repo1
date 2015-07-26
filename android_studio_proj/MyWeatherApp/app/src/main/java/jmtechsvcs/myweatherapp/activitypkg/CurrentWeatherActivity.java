package jmtechsvcs.myweatherapp.activitypkg;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import jmtechsvcs.myweatherapp.MyWeatherApplication;
import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityWeatherCurrCondTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgen.DaoSession;
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

        //get the current weather data here
        CityWeatherCurrCondTable curr_weather_data =
                WeatherDbProcessing.getCurrentWeatherCity(city_id, getApplicationContext());

        //make sure that the data valid before u display it.
        if(curr_weather_data != null)
        {
            Log.d(LOGTAG,"load data, city id = "+curr_weather_data.getCity_id()+
                    ", temp = "+curr_weather_data.getCurr_main_temp());

            //load the data to the ui.
        }
        else
        {
            Log.d(LOGTAG,"null obj for curr city weather obj..load default data.");
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
