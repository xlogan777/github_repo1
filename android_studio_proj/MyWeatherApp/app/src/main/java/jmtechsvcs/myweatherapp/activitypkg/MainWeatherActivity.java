package jmtechsvcs.myweatherapp.activitypkg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.io.IOException;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.networkpkg.NetworkIntentSvc;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;
//added more comments

//test
public class MainWeatherActivity extends AppCompatActivity {

    private static String LOGTAG = "MainWeatherActivity";
    private static boolean useDebug = false;

//http://stackoverflow.com/questions/14744496/extract-database-of-an-application-from-android-device-through-adb

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        long city_id = 4891010;//elmhurst US
        //long city_id = 4349599;//brooklyn park US
        if(useDebug)
        {
            //request download of curr city data.
            //sendMsgToIntentSvc(city_id);
            sendMsgToIntentSvc(city_id);

            //add this to the android back stack for here to the next activity
            //being activated.
            Intent intent = new Intent(this, CurrentWeatherActivity.class);
            Bundle bundle = new Bundle();
            //bundle.putLong("city_id",city_id);
            bundle.putLong("city_id",city_id);

            //add the bundle to the intent.
            intent.putExtras(bundle);

            //start the activity with info on the city id to be used there.
            startActivity(intent);
        }

//        ProgressDialog progress = ProgressDialog.show(this, "Loading", "Wait while loading...");
//
//        try
//        {
//            Thread.sleep(30000);
//        }
//        catch (Exception e){
//
//        }
//
//// To dismiss the dialog
//        progress.dismiss();

        //ProgressDialog.show(this, "Loading", "Wait while loading...");

        //testing the parsing of the weather_station.json file.
        //WeatherDbProcessing.updateCurrentWeatherStationInfoGeo("",getApplicationContext());

        //Intent intent = new Intent(this, CitySearchActivity.class);
        //Intent intent = new Intent(this, DailyWeatherForecastActivity.class);
        //startActivity(intent);
    }

    private void sendMsgToIntentSvc(long cityId)
    {
        //start the bg service, with helper method to load params to bg processing.
        //NetworkIntentSvc.startActionCurrentWeather(this, cityId);
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
