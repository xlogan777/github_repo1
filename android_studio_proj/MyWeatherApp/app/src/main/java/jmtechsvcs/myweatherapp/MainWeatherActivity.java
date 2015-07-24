package jmtechsvcs.myweatherapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.util.List;

import jmtechsvcs.myweatherapp.GreenDaoSrcGen.CityInfoTable;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.CityInfoTableDao;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.DaoSession;
import jmtechsvcs.myweatherapp.networklayer.NetworkIntentSvc;
import jmtechsvcs.myweatherapp.networklayer.NetworkProcessing;
import jmtechsvcs.myweatherapp.networklayer.WeatherMapUrls;
import jmtechsvcs.myweatherapp.utils.WeatherJsonToDbProcessing;
import jmtechsvcs.myweatherapp.utils.WeatherMapUtils;

//test
public class MainWeatherActivity extends ActionBarActivity {

    private static String LOGTAG = "MainWeatherActivity";
    private static boolean useDebug = true;

//http://stackoverflow.com/questions/14744496/extract-database-of-an-application-from-android-device-through-adb

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

//        //get the application ctx for this app.
//        MyWeatherApplication weather_app = (MyWeatherApplication)getApplicationContext();
//
//        //get the dao session stored in the context.
//        DaoSession dao_session = weather_app.getDaoSession();
//
//        //get the dao from the session.
//        CityInfoTableDao dao = dao_session.getCityInfoTableDao();
//
//        //get data from dao with a specific id.
//        List<CityInfoTable> city_info_list = dao.queryBuilder().where
//                (CityInfoTableDao.Properties.City_id.eq(4891010)).list();
//
//        //check to see if we have valid data in the list before  we use it.
//        if(city_info_list.size() > 0)
//            Log.d(LOGTAG,"city name = "+city_info_list.get(0).getName());

        //get the urls for http end pt retrieval.
        //WeatherMapUrls.getCurrentWeatherByCityId("4891010");
        //WeatherMapUrls.getWeatherIconByIconId("10d");

        if(useDebug)
        {
            //request download of curr city data.
            sendMsgToIntentSvc(4891010);
        }

//        try
//        {
//            AssetManager assetManager = getAssets();
//
//            InputStream is = assetManager.open("sample_curr_weather.json");
//            String json_data = WeatherMapUtils.getJsonStringFromStream(is);
//
//            //assetManager.close();
//
//            //parse the json data and save it to the table.
//            WeatherJsonToDbProcessing.updateCurrWeatherToDb(json_data, dao_session);
//        }
//        catch (Exception e)
//        {
//            Log.d(LOGTAG,""+e);
//        }
    }

    private void sendMsgToIntentSvc(long cityId)
    {
        //a way to start the service with fake constructor args..
        //NetworkIntentSvc.startActionBaz(this,"","");

        //create intent with this activity as the sending activity, and the calling service.
        Intent mServiceIntent = new Intent(this, NetworkIntentSvc.class);

        //set the action to this intent.
        mServiceIntent.setAction(NetworkIntentSvc.CURRENT_WEATHER_ACTION);

        //create bundle to save data in it.
        Bundle bundle = new Bundle();
        bundle.putLong("cityId",cityId);

        //save bundle to this intent.
        mServiceIntent.putExtras(bundle);

        //start the service
        startService(mServiceIntent);

        Log.d(LOGTAG, "started service to get current weather data...with city id = "+cityId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
