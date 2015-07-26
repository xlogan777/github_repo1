package jmtechsvcs.myweatherapp.activitypkg;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.networklayer.NetworkIntentSvc;

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

        if(useDebug)
        {
            //request download of curr city data.
            sendMsgToIntentSvc(4891010);
        }
    }

    private void sendMsgToIntentSvc(long cityId)
    {
        //start the bg service, with helper method to load params to bg processing.
        NetworkIntentSvc.startActionCurrentWeather(this,cityId);
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
