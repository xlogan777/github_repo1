package jmtechsvcs.myweatherapp.dbpkg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DailyWeatherInfoTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DaoMaster;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.HourlyWeatherInfoTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTableDao;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTableDao;

/**
 * Created by jimmy on 8/20/2015.
 */
public class WeatherDbHelper extends SQLiteOpenHelper
{
    //this allows for automatic upgrade of data bases when the version number
    //is increased. incease it by 1 as u see fit.
    private static final int SCHEMA_VERSION = 6;//db version.

    private static String LOGTAG = "WeatherDbHelper";

    //use to create the db.
    public WeatherDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super(context, name, factory, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d(LOGTAG, "Creating tables for schema version " + SCHEMA_VERSION);
        DaoMaster.createAllTables(db, true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(newVersion > oldVersion)
        {
            Log.d(LOGTAG, "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");

            //drop the table here if it exists.
            CityWeatherCurrCondTableDao.dropTable(db, true);

            //drop the table here if it exists.
            WeatherIconTableDao.dropTable(db, true);

            //drop the table here if it exists.
            WeatherStationInfoTableDao.dropTable(db, true);

            //drop the table here if it exists.
            DailyWeatherInfoTableDao.dropTable(db, true);

            //drop the table if it exists here.
            HourlyWeatherInfoTableDao.dropTable(db, true);

            //create all needed tables.
            onCreate(db);
        }
        else
        {
            Log.d(LOGTAG,"didnt upgrade from version " + oldVersion + " to " + newVersion );
        }
    }
}
