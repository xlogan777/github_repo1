package jmtechsvcs.myweatherapp.activitypkg;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DailyWeatherInfoTable;

//class to display the daily weather forecast using list activity as a default
//container.
public class DailyWeatherForecastActivity extends ListActivity
{
    private long cityId = 0;
    private static final String LOGTAG = "DailyWthrForecastAct";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //allow for scrolling inside the list activity container.
        this.getListView().setScrollContainer(true);

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
            //create the weather adapter for daily weather
            DailyWeatherAdapter dailyWeatherAdapter =
                    new DailyWeatherAdapter(this, daily_weather_param);

            //set the weather adapter here.
            setListAdapter(dailyWeatherAdapter);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        DailyWeatherInfoTable selectedFromList = (DailyWeatherInfoTable)getListView().getItemAtPosition(position);
        Log.d(LOGTAG,"clicked on this item for the daily weather pos = "+position+
                ", date = "+new Date(selectedFromList.getDaily_weather_date())+", city id = "+cityId);

        //TODO: use the item clicked on, to use the date of 3hrs weather data for the city id,
        //use the city id, and the date of this item selected to return back the list of
        //3 hourly items to display in another activity.
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
