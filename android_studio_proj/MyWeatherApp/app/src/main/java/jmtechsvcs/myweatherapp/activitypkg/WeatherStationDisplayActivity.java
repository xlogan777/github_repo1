package jmtechsvcs.myweatherapp.activitypkg;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.fragmentpkg.WeatherStationFragment;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.GoogleAnalyticsTracking;

public class WeatherStationDisplayActivity extends AppCompatActivity
{
    private static final String LOGTAG = "WeathtationDispActivity";
    private long cityId;
    private double lat;
    private double lon;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_station_display);

        //send the tracking ofthe viewing of this screen.
        GoogleAnalyticsTracking.sendScreenViewEvents(WeatherStationDisplayActivity.class.getSimpleName());

        //get the intent to use the data from the parent activity.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //this is the city id needed to ge the current weather data.
        cityId = bundle.getLong("cityId");
        lat = bundle.getDouble("lat");
        lon = bundle.getDouble("lon");
        cityName = bundle.getString("cn");

        //get the city id from the item row for the text view.
        TextView textView = (TextView)findViewById(R.id.city_id_vals);
        textView.setText(cityId+"");

        //get the city name and country code, and add it to the text view.
        textView = (TextView)findViewById(R.id.cn_cc_vals);
        textView.setText(cityName + ", "+ bundle.getString("cc"));

        //get the lat from item row and add it to the text view.
        textView = (TextView)findViewById(R.id.lat_vals);
        textView.setText(lat+"");

        //get the long from the item row and add it to the text view.
        textView = (TextView)findViewById(R.id.lon_vals);
        textView.setText(lon+"");

        //get the application context.
        Context context = getApplicationContext();

        //load the weather stations for this curr city data.
        loadWeatherStations(cityId, context);
    }

    //get the weather station data via dao, and create fragment for this to display.
    private void loadWeatherStations(long cityId, Context context)
    {
        BeanQueryParams qp = new BeanQueryParams();
        qp.setCityId(cityId);//city id is used for weather station data.

        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_WEATHER_STATION_TABLE_LIST_TYPE);

        //get list of weather stations based on the query params.
        List<WeatherStationInfoTable> weather_station_list =
                WeatherDbProcessing.getBeanByQueryParamsList(qp, context, new WeatherStationInfoTable());

        //check to see if we have data to show.
        if(weather_station_list != null && weather_station_list.size() > 0)
        {
            //create fragment here and provide the list here to show.
            //get the frag mgr and create a tx to add frags.
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            //create the fragment.
            WeatherStationFragment frag = new WeatherStationFragment();

            //set data to be used by this fragment.
            frag.setWeatherStationList(weather_station_list);

            //add fragment to main activity layout
            ft.add(R.id.weather_station_id, frag);

            //commit this activity.
            ft.commit();
        }
        else
        {
            Log.d(LOGTAG, "nothing to display for weather stations");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_station_display, menu);
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
        if(id == R.id.weather_station_id_menu_item)
        {
            //send analytics event when picking the event to show the weather station maps.
            GoogleAnalyticsTracking.sendAnalyticsEvent
                    ("Weather_Map_Category", "Weather_Map_Clicked", "ShowWeatherStationMaps");

            //create intent and save city id and save bundle to intent.
            Intent intent = new Intent(this, WeatherStationMapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("city_id", cityId);
            bundle.putDouble("lat", lat);
            bundle.putDouble("lon", lon);
            bundle.putString("cn",cityName);
            intent.putExtras(bundle);

            //launch the weather maps activity
            this.startActivity(intent);

            //return true
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
