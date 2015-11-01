package jmtechsvcs.myweatherapp;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import de.greenrobot.dao.query.QueryBuilder;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbHelper;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DailyWeatherInfoTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DaoMaster;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DaoSession;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.HourlyWeatherInfoTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTableDao;
import jmtechsvcs.myweatherapp.networkpkg.WeatherMapUrls;
import jmtechsvcs.myweatherapp.utilspkg.GoogleAdMob;
import jmtechsvcs.myweatherapp.utilspkg.GoogleAnalyticsTracking;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

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
//this is a test.
public class MyWeatherApplication extends Application
{
    private static String LOGTAG = "MyWeatherApplication";

    private static final String DB_PATH = "/data/data/jmtechsvcs.myweatherapp/databases/";
    final static String DB_NAME = "my_weather_db.db";

    //use the dev helper to setup the db here with the db name and the main activity for the context.
    private WeatherDbHelper helper;

    //get the actual sql lite database here..creates the db now.
    private SQLiteDatabase db;

    //use the db ref to get the dao master.
    private DaoMaster daoMaster;

    //use the dao session as a cache.
    private DaoSession daoSession;

    @Override
    public void onCreate()
    {
        super.onCreate();

        //setup all the db stuff.
        weatherDbSetup();

        //call this to load all the weather data for cities
        //preloadWeatherData();

        Log.d(LOGTAG, "onCreate called for WeatherApplication class");

        //init the google analytics tracker with the application context.
        GoogleAnalyticsTracking.intializeTracker(this);

        //init the google ad mob requests for city search.
        GoogleAdMob.initCitySearchAdRequest();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    public DaoSession getDaoSession()
    {
        return daoSession;
    }

    //check if the databases exists or not.
    //also confirms that the DB is a file.
    //returns true if so.
    private boolean checkDataBase()
    {
        File databaseFile = new File(DB_PATH + DB_NAME);
        boolean status = databaseFile.exists();

        return status;
    }

    //this will copy the DB file from the asset folder to the
    //database folder for this app.
    private void copyDataBase()
    {
        AssetManager assetManager = null;
        InputStream myInput = null;
        OutputStream myOutput = null;

        try
        {
            //get the asset mgr.
            //never close the asset manager.
            assetManager = getAssets();

            //Open your local db as the input stream
            myInput = assetManager.open(DB_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;

            //Open the empty db as the output stream
            myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while((length = myInput.read(buffer)) > 0){
                myOutput.write(buffer, 0, length);
            }

            //flush the output stream.
            myOutput.flush();
        }
        catch(Exception e)
        {
            Log.d(LOGTAG,WeatherAppUtils.getStackTrace(e));
        }
        finally
        {
            if(myOutput != null)
            {
                try
                {
                    myOutput.close();
                }
                catch(Exception e)
                {
                    Log.d(LOGTAG,WeatherAppUtils.getStackTrace(e));
                }
            }

            if(myInput != null)
            {
                try
                {
                    myInput.close();
                }
                catch(Exception e){
                    Log.d(LOGTAG,WeatherAppUtils.getStackTrace(e));
                }
            }
        }
    }

    //this will load the file from the assets folder and put it to the /databases folder
    //so that we can start with the city info data
    private void loadDbFromAssets()
    {
        //check if the database exists
        boolean databaseExist = checkDataBase();

        //create the helper class to assist in creating/opening the db.
        helper = new WeatherDbHelper(this, DB_NAME, null);

        //get the actual sql lite database here..creates the db now.
        db = helper.getWritableDatabase();

        if(databaseExist)
        {
            Log.d(LOGTAG,"database = "+DB_NAME+", already exists just use it.");
        }
        else
        {
            //copy the db from the assets folder to the current db just created.
            //this will overwrite the db just created recently with the one in the
            //assets folder.
            copyDataBase();

            //after we copy the db from the assets folder we lost all the tables
            //created. need to create them if we did a copy.
            CityWeatherCurrCondTableDao.createTable(db, true);
            WeatherIconTableDao.createTable(db, true);
            WeatherStationInfoTableDao.createTable(db, true);
            DailyWeatherInfoTableDao.createTable(db, true);
            HourlyWeatherInfoTableDao.createTable(db, true);
        }// end if else dbExist
    }

    private void loadWeatherIconFromAssets()
    {
        //get all image from from the asset mgr folder.
        //never close the asset manager.
        AssetManager assetManager = getAssets();

        try
        {
            String icon_folder = "weather_icons";

            //list of asset files from this folder.
            String[] files = assetManager.list(icon_folder);

            for(String filename : files)
            {
                //skip over anything that isnt a .png exention.
                Log.d(LOGTAG, "fname  = " + filename);

                if(!filename.contains(".png"))
                    continue;

                //file input stream from asset mgr.
                InputStream is = assetManager.open(icon_folder+"/"+filename);

                //get the byte array from the input stream.
                byte [] binary_file = WeatherAppUtils.getByteArrayFromStream(is);

                if(binary_file != null)
                {
                    //split the fname with "."
                    String [] split_str = filename.split("\\.");

                    //use the file name with no extension for the icon id.
                    String icon_id = split_str[0];

                    //create the weather icon url.
                    String weather_icon_url = WeatherMapUrls.getWeatherIconByIconId(icon_id);

                    //save the icon data into the DB, with the icon url.
                    WeatherDbProcessing.updateWeatherIcon
                            (icon_id, weather_icon_url, binary_file, this);
                }
                else
                {
                    Log.d(LOGTAG,"got empty binary file for fname = "+filename);
                }
            }
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }
    }

    private void weatherDbSetup()
    {
        //load db from asset folder if it doesnt exist
        loadDbFromAssets();

        //use the db ref to get the dao master.
        daoMaster = new DaoMaster(db);

        //get a new session from this dao master.
        daoSession = daoMaster.newSession();

        //load the weather icon from the assets folder.
        loadWeatherIconFromAssets();

        //enable logging of sql statements for
        //enableSqlDebugging();
    }

    private void enableSqlDebugging()
    {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    private void preloadWeatherData()
    {
        //get the dao obj for the city info bean.
        CityInfoTableDao city_info_tbl_dao = daoSession.getCityInfoTableDao();

        //get all the json files with asset manager.
        //never close the asset manager.
        AssetManager assetManager = getAssets();

        try
        {
            //list of asset files.
            String[] files = assetManager.list("");

            int total_count = 0;

            for(String filename : files)
            {
                //skip over anything that isnt a .json exention.
                Log.d(LOGTAG, "fname  = " + filename);

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

                    Log.d(LOGTAG,"added item id = "+id+", file row count = "+row_count+", total cnt = "+total_count);
                }

                Thread.sleep(3000);//sleep for 3 secs

                //close the buffered reader.
                br.close();
            }
        }
        catch(Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }
    }
}
