package jmtechsvcs.myweatherapp;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import jmtechsvcs.myweatherapp.GreenDaoSrcGen.CityInfoTable;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.CityInfoTableDao;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.DaoMaster;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.DaoSession;


public class MainWeatherActivity extends ActionBarActivity {

    private String MyTag = "MainWeatherActivity";
//http://stackoverflow.com/questions/14744496/extract-database-of-an-application-from-android-device-through-adb

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        MyWeatherApplication weather_app = (MyWeatherApplication)getApplicationContext();
        DaoSession dao_session = weather_app.getDaoSession();

        CityInfoTableDao dao = dao_session.getCityInfoTableDao();

        //get data from dao with a specific id.
        List<CityInfoTable> city_info_list = dao.queryBuilder().where
                (CityInfoTableDao.Properties.City_id.eq(5368381)).list();

        Log.d(MyTag,"city name = "+city_info_list.get(0).getName());
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
