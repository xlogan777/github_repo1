package jmtechsvcs.myweatherapp.activitypkg;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.fragmentpkg.CityListFragment;
import jmtechsvcs.myweatherapp.fragmentpkg.WeatherStationFragment;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.MathUtils;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

public class WeatherStationDisplayActivity extends ActionBarActivity
{
    private static final String LOGTAG = "WeathtationDispActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_station_display);

        //get the intent to use the data from the parent activity.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //this is the city id needed to ge the current weather data.
        long city_id = bundle.getLong("city_id");

        //get the application context.
        Context context = getApplicationContext();

        BeanQueryParams qp = new BeanQueryParams();
        qp.setCityId(city_id);//city id is used for curr weather data.

        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_CURR_CITY_WEATHER_TABLE_TYPE);
        CityWeatherCurrCondTable curr_weather_data = new CityWeatherCurrCondTable();
        curr_weather_data = WeatherDbProcessing.getBeanByQueryParams(qp, context, curr_weather_data);

        //get the current city info using city id.
        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_CITY_INFO_TABLE_TYPE);
        CityInfoTable city_info_table = new CityInfoTable();
        city_info_table = WeatherDbProcessing.getBeanByQueryParams(qp, context, city_info_table);

        if(curr_weather_data != null && city_info_table != null)
        {
            //get the image icon and allow to have it loaded to the image view.
            //setup the query params for access to the icon data.
            qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_IMG_ICON_TABLE_TYPE);
            qp.setCityId(-1);
            qp.setIconId(curr_weather_data.getCurr_weather_icon());

            WeatherIconTable weatherIconTable = new WeatherIconTable();
            weatherIconTable = WeatherDbProcessing.getBeanByQueryParams(qp, context, weatherIconTable);

            if(weatherIconTable != null)
            {
                //load city info
                loadCityInfo(city_info_table);

                //load curr weather data.
                loadCityWeatherInfo(curr_weather_data);

                //load weather icon
                loadCityWeatherIcon(weatherIconTable);

                //load the weather stations for this curr city data.
                loadWeatherStations(city_id, context);
            }
        }
        else
        {
            setDefaultView();
        }
    }

    private void loadCityInfo(CityInfoTable cityInfoTable)
    {
        //set the city name and the country code.
        ((TextView)findViewById(R.id.cn_name_val)).setText(cityInfoTable.getName());
        ((TextView)findViewById(R.id.cc_name_val)).setText(cityInfoTable.getCountry());
    }

    private void loadCityWeatherInfo(CityWeatherCurrCondTable currWeatherTable)
    {
        String result = WeatherAppUtils.getDefaultStringDiplayDouble(currWeatherTable.getCurr_main_temp());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.tempature_val)).setText(MathUtils.getTempString(
                    currWeatherTable.getCurr_main_temp())+"");
        else
            ((TextView)findViewById(R.id.tempature_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDiplayLong(currWeatherTable.getCurr_main_pressure());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.pressure_val)).setText(MathUtils.getPressureString(
                    currWeatherTable.getCurr_main_pressure()
            )+"");
        else
            ((TextView)findViewById(R.id.pressure_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDiplayLong(
                currWeatherTable.getCurr_main_humidity()
        );
        if(result.length() == 0)
            ((TextView)findViewById(R.id.humidity_val)).setText(MathUtils.getDegreeString(
                    currWeatherTable.getCurr_main_humidity()
            )+"");
        else
            ((TextView)findViewById(R.id.humidity_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDiplayDouble(currWeatherTable.getCurr_main_temp_min());
        if(result.length() == 0)
            ((TextView)findViewById(R.id.temp_min_val)).setText(
                    MathUtils.getTempString(currWeatherTable.getCurr_main_temp_min()) + "");
        else
            ((TextView)findViewById(R.id.temp_min_val)).setText(result);


        result = WeatherAppUtils.getDefaultStringDiplayDouble(
                currWeatherTable.getCurr_main_temp_max()
        );
        if(result.length() == 0)
            ((TextView)findViewById(R.id.temp_max_val)).setText(MathUtils.getTempString(currWeatherTable.getCurr_main_temp_max())+"");
        else
            ((TextView)findViewById(R.id.temp_max_val)).setText(result);
    }

    private void loadCityWeatherIcon(WeatherIconTable weatherIconTable)
    {
        try
        {
            //get the image path from the bean obj.
            String img_path = weatherIconTable.getImage_path();

            if(img_path != null && img_path.length() > 0)
            {
                Bitmap bitmap = WeatherAppUtils.readPngFile(img_path);

                if(bitmap != null)
                {
                    //set the image bit map here.
                    ((ImageView)findViewById(R.id.weather_icon)).setImageBitmap(bitmap);
                    Log.d(LOGTAG, "loaded the image bit map...bit map file = " + img_path);
                }
            }
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }
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
    }

    //data view to empty string since we can't load weather data.
    private void setDefaultView()
    {
        ((TextView)findViewById(R.id.cn_name_val)).setText("no city data available");
        ((TextView)findViewById(R.id.cc_name_val)).setText("no city data available");

        ((TextView)findViewById(R.id.tempature_val)).setText("");
        ((TextView)findViewById(R.id.temp_min_val)).setText("");
        ((TextView)findViewById(R.id.temp_max_val)).setText("");
        ((TextView)findViewById(R.id.humidity_val)).setText("");
        ((TextView)findViewById(R.id.pressure_val)).setText("");
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
        if(id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
