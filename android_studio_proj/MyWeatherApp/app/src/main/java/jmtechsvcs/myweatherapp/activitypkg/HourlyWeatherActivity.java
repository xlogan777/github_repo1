package jmtechsvcs.myweatherapp.activitypkg;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.fragmentpkg.HourlyWeatherFragment;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.HourlyWeatherInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

public class HourlyWeatherActivity extends Activity
{
    private long cityId = 0;
    private static final String LOGTAG = "HourlyWthrForecastAct";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);

        //get the bundle from the intent.
        Intent data = getIntent();
        Bundle daily_bundle = data.getExtras();

        //get the city id from the bundle.
        long city_id = daily_bundle.getLong("cityId");

        //get the city id from the item row for the text view.
        TextView textView = (TextView)findViewById(R.id.city_id_vals);
        textView.setText(daily_bundle.getLong("cityId")+"");

        //get the city name and country code, and add it to the text view.
        textView = (TextView)findViewById(R.id.cn_cc_vals);
        textView.setText(daily_bundle.getString("cn")+", "+daily_bundle.getString("cc"));

        //get the lat from item row and add it to the text view.
        textView = (TextView)findViewById(R.id.lat_vals);
        textView.setText(daily_bundle.getDouble("lat")+"");

        //get the long from the item row and add it to the text view.
        textView = (TextView)findViewById(R.id.lon_vals);
        textView.setText(daily_bundle.getDouble("lon")+"");

        //save the city id to this activity class.
        cityId = city_id;

        //get the application context.
        Context context = getApplicationContext();

        BeanQueryParams qp = new BeanQueryParams();
        qp.setCityId(city_id);

        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_HOURLY_WEATHER_TABLE_LIST_TYPE);

        //get list of weather stations based on the query params.
        List<HourlyWeatherInfoTable> hourly_weather_param =
                WeatherDbProcessing.getBeanByQueryParamsList(qp, context, new HourlyWeatherInfoTable());

        //save only the current dates here.
        List<HourlyWeatherInfoTable> current_date_list = new ArrayList<HourlyWeatherInfoTable>();

        for(HourlyWeatherInfoTable items : hourly_weather_param)
        {
            //create a new date obj from the date seconds.
            //use the from date as the time test.
            Date item_date = new Date(items.getHourly_from_weather_date());
            Date current_date = new Date();

            //check to see if the are the same day.
            boolean status = WeatherAppUtils.isSameDay(item_date, current_date);

            //add to list for current day.
            if(status)
            {
                current_date_list.add(items);
            }
        }

        //need to check to see if we have items to display..if we dont
        //then we need to copy 1 days worth of data to the display list for the next day.
        if(current_date_list.size() == 0 && hourly_weather_param.size() >= 8 )
        {
            //take the next day items from the main list and copy that to the
            //list to be used here.
            current_date_list.addAll(hourly_weather_param.subList(0,8));
        }

        //check to see if we have data to show.
        if(current_date_list != null && current_date_list.size() > 0)
        {
            //create fragment here and provide the list here to show.
            //get the frag mgr and create a tx to add frags.
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            //create the fragment.
            HourlyWeatherFragment frag = new HourlyWeatherFragment();

            //set data to be used by this fragment.
            frag.setHourlyWeatherList(current_date_list);

            //add fragment to main activity layout
            ft.add(R.id.hourly_weather_id, frag);

            //commit this activity.
            ft.commit();
        }
        else
        {
            Log.d(LOGTAG, "nothing to display for hourly weather");
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_hourly_weather, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
