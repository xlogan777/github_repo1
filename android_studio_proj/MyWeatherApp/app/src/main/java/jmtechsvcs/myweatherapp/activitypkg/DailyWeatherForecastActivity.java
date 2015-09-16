package jmtechsvcs.myweatherapp.activitypkg;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.fragmentpkg.DailyWeatherFragment;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DailyWeatherInfoTable;

public class DailyWeatherForecastActivity extends ActionBarActivity
{
    private long cityId = 0;
    private static final String LOGTAG = "DailyWthrForecastAct";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //inflate the content layout.
        setContentView(R.layout.activity_daily_weather_forecast);

        //get the bundle from the intent.
        Intent data = getIntent();
        Bundle daily_bundle = data.getExtras();

        //get the city id from the bundle.
        long city_id = daily_bundle.getLong("city_id");

        //save the city id to this activity class.
        cityId = city_id;

        //get the application context.
        Context context = getApplicationContext();

        BeanQueryParams qp = new BeanQueryParams();
        qp.setCityId(city_id);

        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_DAILY_WEATHER_TABLE_LIST_TYPE);

        //get list of weather stations based on the query params.
        List<DailyWeatherInfoTable> daily_weather_param =
                WeatherDbProcessing.getBeanByQueryParamsList(qp, context, new DailyWeatherInfoTable());

        //check to see if we have data to show.
        if(daily_weather_param != null && daily_weather_param.size() > 0)
        {
            //create fragment here and provide the list here to show.
            //get the frag mgr and create a tx to add frags.
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            //create the fragment.
            DailyWeatherFragment frag = new DailyWeatherFragment();

            //set data to be used by this fragment.
            frag.setDailyWeatherList(daily_weather_param);

            //add fragment to main activity layout
            ft.add(R.id.daily_weather_id, frag);

            //commit this activity.
            ft.commit();
        }
        else
        {
            Log.d(LOGTAG,"nothing to display for daily weather");
            //TODO: display something else..could not show data.
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily_weather_forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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
