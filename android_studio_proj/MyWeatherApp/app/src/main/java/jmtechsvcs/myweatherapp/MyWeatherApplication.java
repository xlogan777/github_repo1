package jmtechsvcs.myweatherapp;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import jmtechsvcs.myweatherapp.GreenDaoSrcGen.CityInfoTable;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.CityInfoTableDao;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.DaoMaster;
import jmtechsvcs.myweatherapp.GreenDaoSrcGen.DaoSession;

/**
 * Created by jimmy on 7/21/2015.
 */
//how to create application class.
//http://www.intertech.com/Blog/androids-application-class/

//comments if greendao session or dao master are thread safe.
//https://groups.google.com/forum/#!topic/greendao/NSPX2Vn3ZKk

//core initialization for green dao.
//http://greendao-orm.com/documentation/introduction/

/**
 * this is to provide the main application a way to access from all activities
 * common services.
 *
 * @author Jimmy
 * @version 1.0
 */
public class MyWeatherApplication extends Application
{
    private String MyTag = "MyWeatherApplication";

    //use the dev helper to setup the db here with the db name and the main activity for the context.
    private DaoMaster.DevOpenHelper helper;

    //get the actual sql lite database here..creates the db now.
    private SQLiteDatabase db;

    //use the db ref to get the dao master.
    private DaoMaster daoMaster;

    //get a session via the dao master.
    private DaoSession daoSession;

    @Override
    public void onCreate()
    {
        //setup all the db stuff.
        weatherDbSetup();

        //call this to load all the weather data for cities
        //preloadWeatherData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
//Called by the system when the device configuration changes while your component is running.
    }

    @Override
    public void onLowMemory()
    {
//This is called when the overall system is running low on memory, and actively running processes should trim their memory usage.
    }

    public DaoMaster getDaoMaster()
    {
        return daoMaster;
    }

    public DaoSession getDaoSession()
    {
        return daoSession;
    }

    private void weatherDbSetup()
    {
        helper = new DaoMaster.DevOpenHelper(this, "my_weather_db.db", null);

        //get the actual sql lite database here..creates the db now.
        db = helper.getWritableDatabase();

        //use the db ref to get the dao master.
        daoMaster = new DaoMaster(db);

        //get a session via the dao master.
        daoSession = daoMaster.newSession();
    }

    private void preloadWeatherData()
    {
        //get the dao obj for the city info bean.
        CityInfoTableDao city_info_tbl_dao = daoSession.getCityInfoTableDao();

        //get all the json files with asset manager.
        AssetManager assetManager = getAssets();

        try
        {
            //list of asset files.
            String[] files = assetManager.list("");

            int total_count = 0;

            for(String filename : files)
            {
                //skip over anything that isnt a .json exention.
                Log.d(MyTag, "fname  = " + filename);

                if(!filename.contains(".json"))
                    continue;

                //file input stream from asset mgr.
                InputStream is = assetManager.open(filename);

                //create buffered reader for this file.
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;

                int row_count = 0;
                //loop until we read the entire file.
                while((line=br.readLine())!=null)
                {
                    row_count++;
                    total_count++;
                    JSONObject obj = new JSONObject(line);

                    int id = obj.getInt("_id");
                    String name = obj.getString("name");
                    String country = obj.getString("country");

                    //get a sub obj of the main obj.
                    obj = obj.getJSONObject("coord");
                    double lon = obj.getDouble("lon");
                    double lat = obj.getDouble("lat");

                    //preset the pojo here.
                    CityInfoTable city_info_bean = new CityInfoTable();

                    city_info_bean.setCity_id(id);
                    city_info_bean.setName(name);
                    city_info_bean.setCountry(country);
                    city_info_bean.setLat(lat);
                    city_info_bean.setLon(lon);

                    //save to the db here. it does an upsert action.
                    city_info_tbl_dao.insertOrReplace(city_info_bean);

                    Log.d(MyTag,"added item id = "+id+", file row count = "+row_count+", total cnt = "+total_count);
                }

                Thread.sleep(3000);//sleep for 3 secs

                //close the buffered reader.
                br.close();
            }

            //close the resource.
            assetManager.close();
        }
        catch(Exception e)
        {
            Log.d(MyTag,e.getLocalizedMessage());
        }
    }
}
