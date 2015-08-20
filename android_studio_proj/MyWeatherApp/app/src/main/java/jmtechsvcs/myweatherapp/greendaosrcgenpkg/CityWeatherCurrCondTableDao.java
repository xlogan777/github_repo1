package jmtechsvcs.myweatherapp.greendaosrcgenpkg;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CITY_WEATHER_CURR_COND_TABLE".
*/
public class CityWeatherCurrCondTableDao extends AbstractDao<CityWeatherCurrCondTable, Long> {

    public static final String TABLENAME = "CITY_WEATHER_CURR_COND_TABLE";

    /**
     * Properties of entity CityWeatherCurrCondTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property City_id = new Property(0, long.class, "city_id", true, "CITY_ID");
        public final static Property Curr_weather_id = new Property(1, Long.class, "curr_weather_id", false, "CURR_WEATHER_ID");
        public final static Property Curr_weather_main = new Property(2, String.class, "curr_weather_main", false, "CURR_WEATHER_MAIN");
        public final static Property Curr_weather_desc = new Property(3, String.class, "curr_weather_desc", false, "CURR_WEATHER_DESC");
        public final static Property Curr_weather_icon = new Property(4, String.class, "curr_weather_icon", false, "CURR_WEATHER_ICON");
        public final static Property Curr_main_temp = new Property(5, Double.class, "curr_main_temp", false, "CURR_MAIN_TEMP");
        public final static Property Curr_main_pressure = new Property(6, Long.class, "curr_main_pressure", false, "CURR_MAIN_PRESSURE");
        public final static Property Curr_main_humidity = new Property(7, Long.class, "curr_main_humidity", false, "CURR_MAIN_HUMIDITY");
        public final static Property Curr_main_temp_min = new Property(8, Double.class, "curr_main_temp_min", false, "CURR_MAIN_TEMP_MIN");
        public final static Property Curr_main_temp_max = new Property(9, Double.class, "curr_main_temp_max", false, "CURR_MAIN_TEMP_MAX");
        public final static Property Curr_main_sea_level = new Property(10, Long.class, "curr_main_sea_level", false, "CURR_MAIN_SEA_LEVEL");
        public final static Property Curr_main_grnd_level = new Property(11, Long.class, "curr_main_grnd_level", false, "CURR_MAIN_GRND_LEVEL");
        public final static Property Curr_wind_speed = new Property(12, Double.class, "curr_wind_speed", false, "CURR_WIND_SPEED");
        public final static Property Curr_wind_degs = new Property(13, Long.class, "curr_wind_degs", false, "CURR_WIND_DEGS");
        public final static Property Curr_wind_speed_name = new Property(14, String.class, "curr_wind_speed_name", false, "CURR_WIND_SPEED_NAME");
        public final static Property Curr_wind_dirr_code = new Property(15, String.class, "curr_wind_dirr_code", false, "CURR_WIND_DIRR_CODE");
        public final static Property Curr_clouds_all = new Property(16, Long.class, "curr_clouds_all", false, "CURR_CLOUDS_ALL");
        public final static Property Curr_visibility = new Property(17, Long.class, "curr_visibility", false, "CURR_VISIBILITY");
        public final static Property Precipitation_mode = new Property(18, String.class, "precipitation_mode", false, "PRECIPITATION_MODE");
        public final static Property Precipitation_value = new Property(19, Double.class, "precipitation_value", false, "PRECIPITATION_VALUE");
        public final static Property Curr_rain_last3hrs = new Property(20, Double.class, "curr_rain_last3hrs", false, "CURR_RAIN_LAST3HRS");
        public final static Property Curr_snow_last3hrs = new Property(21, Double.class, "curr_snow_last3hrs", false, "CURR_SNOW_LAST3HRS");
        public final static Property Curr_data_calc_time = new Property(22, Long.class, "curr_data_calc_time", false, "CURR_DATA_CALC_TIME");
        public final static Property Curr_sys_sunrise_time = new Property(23, Long.class, "curr_sys_sunrise_time", false, "CURR_SYS_SUNRISE_TIME");
        public final static Property Curr_sys_sunset_time = new Property(24, Long.class, "curr_sys_sunset_time", false, "CURR_SYS_SUNSET_TIME");
    };


    public CityWeatherCurrCondTableDao(DaoConfig config) {
        super(config);
    }
    
    public CityWeatherCurrCondTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CITY_WEATHER_CURR_COND_TABLE\" (" + //
                "\"CITY_ID\" INTEGER PRIMARY KEY NOT NULL ," + // 0: city_id
                "\"CURR_WEATHER_ID\" INTEGER," + // 1: curr_weather_id
                "\"CURR_WEATHER_MAIN\" TEXT," + // 2: curr_weather_main
                "\"CURR_WEATHER_DESC\" TEXT," + // 3: curr_weather_desc
                "\"CURR_WEATHER_ICON\" TEXT," + // 4: curr_weather_icon
                "\"CURR_MAIN_TEMP\" REAL," + // 5: curr_main_temp
                "\"CURR_MAIN_PRESSURE\" INTEGER," + // 6: curr_main_pressure
                "\"CURR_MAIN_HUMIDITY\" INTEGER," + // 7: curr_main_humidity
                "\"CURR_MAIN_TEMP_MIN\" REAL," + // 8: curr_main_temp_min
                "\"CURR_MAIN_TEMP_MAX\" REAL," + // 9: curr_main_temp_max
                "\"CURR_MAIN_SEA_LEVEL\" INTEGER," + // 10: curr_main_sea_level
                "\"CURR_MAIN_GRND_LEVEL\" INTEGER," + // 11: curr_main_grnd_level
                "\"CURR_WIND_SPEED\" REAL," + // 12: curr_wind_speed
                "\"CURR_WIND_DEGS\" INTEGER," + // 13: curr_wind_degs
                "\"CURR_WIND_SPEED_NAME\" TEXT," + // 14: curr_wind_speed_name
                "\"CURR_WIND_DIRR_CODE\" TEXT," + // 15: curr_wind_dirr_code
                "\"CURR_CLOUDS_ALL\" INTEGER," + // 16: curr_clouds_all
                "\"CURR_VISIBILITY\" INTEGER," + // 17: curr_visibility
                "\"PRECIPITATION_MODE\" TEXT," + // 18: precipitation_mode
                "\"PRECIPITATION_VALUE\" REAL," + // 19: precipitation_value
                "\"CURR_RAIN_LAST3HRS\" REAL," + // 20: curr_rain_last3hrs
                "\"CURR_SNOW_LAST3HRS\" REAL," + // 21: curr_snow_last3hrs
                "\"CURR_DATA_CALC_TIME\" INTEGER," + // 22: curr_data_calc_time
                "\"CURR_SYS_SUNRISE_TIME\" INTEGER," + // 23: curr_sys_sunrise_time
                "\"CURR_SYS_SUNSET_TIME\" INTEGER);"); // 24: curr_sys_sunset_time
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CITY_WEATHER_CURR_COND_TABLE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CityWeatherCurrCondTable entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCity_id());
 
        Long curr_weather_id = entity.getCurr_weather_id();
        if (curr_weather_id != null) {
            stmt.bindLong(2, curr_weather_id);
        }
 
        String curr_weather_main = entity.getCurr_weather_main();
        if (curr_weather_main != null) {
            stmt.bindString(3, curr_weather_main);
        }
 
        String curr_weather_desc = entity.getCurr_weather_desc();
        if (curr_weather_desc != null) {
            stmt.bindString(4, curr_weather_desc);
        }
 
        String curr_weather_icon = entity.getCurr_weather_icon();
        if (curr_weather_icon != null) {
            stmt.bindString(5, curr_weather_icon);
        }
 
        Double curr_main_temp = entity.getCurr_main_temp();
        if (curr_main_temp != null) {
            stmt.bindDouble(6, curr_main_temp);
        }
 
        Long curr_main_pressure = entity.getCurr_main_pressure();
        if (curr_main_pressure != null) {
            stmt.bindLong(7, curr_main_pressure);
        }
 
        Long curr_main_humidity = entity.getCurr_main_humidity();
        if (curr_main_humidity != null) {
            stmt.bindLong(8, curr_main_humidity);
        }
 
        Double curr_main_temp_min = entity.getCurr_main_temp_min();
        if (curr_main_temp_min != null) {
            stmt.bindDouble(9, curr_main_temp_min);
        }
 
        Double curr_main_temp_max = entity.getCurr_main_temp_max();
        if (curr_main_temp_max != null) {
            stmt.bindDouble(10, curr_main_temp_max);
        }
 
        Long curr_main_sea_level = entity.getCurr_main_sea_level();
        if (curr_main_sea_level != null) {
            stmt.bindLong(11, curr_main_sea_level);
        }
 
        Long curr_main_grnd_level = entity.getCurr_main_grnd_level();
        if (curr_main_grnd_level != null) {
            stmt.bindLong(12, curr_main_grnd_level);
        }
 
        Double curr_wind_speed = entity.getCurr_wind_speed();
        if (curr_wind_speed != null) {
            stmt.bindDouble(13, curr_wind_speed);
        }
 
        Long curr_wind_degs = entity.getCurr_wind_degs();
        if (curr_wind_degs != null) {
            stmt.bindLong(14, curr_wind_degs);
        }
 
        String curr_wind_speed_name = entity.getCurr_wind_speed_name();
        if (curr_wind_speed_name != null) {
            stmt.bindString(15, curr_wind_speed_name);
        }
 
        String curr_wind_dirr_code = entity.getCurr_wind_dirr_code();
        if (curr_wind_dirr_code != null) {
            stmt.bindString(16, curr_wind_dirr_code);
        }
 
        Long curr_clouds_all = entity.getCurr_clouds_all();
        if (curr_clouds_all != null) {
            stmt.bindLong(17, curr_clouds_all);
        }
 
        Long curr_visibility = entity.getCurr_visibility();
        if (curr_visibility != null) {
            stmt.bindLong(18, curr_visibility);
        }
 
        String precipitation_mode = entity.getPrecipitation_mode();
        if (precipitation_mode != null) {
            stmt.bindString(19, precipitation_mode);
        }
 
        Double precipitation_value = entity.getPrecipitation_value();
        if (precipitation_value != null) {
            stmt.bindDouble(20, precipitation_value);
        }
 
        Double curr_rain_last3hrs = entity.getCurr_rain_last3hrs();
        if (curr_rain_last3hrs != null) {
            stmt.bindDouble(21, curr_rain_last3hrs);
        }
 
        Double curr_snow_last3hrs = entity.getCurr_snow_last3hrs();
        if (curr_snow_last3hrs != null) {
            stmt.bindDouble(22, curr_snow_last3hrs);
        }
 
        Long curr_data_calc_time = entity.getCurr_data_calc_time();
        if (curr_data_calc_time != null) {
            stmt.bindLong(23, curr_data_calc_time);
        }
 
        Long curr_sys_sunrise_time = entity.getCurr_sys_sunrise_time();
        if (curr_sys_sunrise_time != null) {
            stmt.bindLong(24, curr_sys_sunrise_time);
        }
 
        Long curr_sys_sunset_time = entity.getCurr_sys_sunset_time();
        if (curr_sys_sunset_time != null) {
            stmt.bindLong(25, curr_sys_sunset_time);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CityWeatherCurrCondTable readEntity(Cursor cursor, int offset) {
        CityWeatherCurrCondTable entity = new CityWeatherCurrCondTable( //
            cursor.getLong(offset + 0), // city_id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // curr_weather_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // curr_weather_main
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // curr_weather_desc
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // curr_weather_icon
            cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5), // curr_main_temp
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // curr_main_pressure
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // curr_main_humidity
            cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8), // curr_main_temp_min
            cursor.isNull(offset + 9) ? null : cursor.getDouble(offset + 9), // curr_main_temp_max
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // curr_main_sea_level
            cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11), // curr_main_grnd_level
            cursor.isNull(offset + 12) ? null : cursor.getDouble(offset + 12), // curr_wind_speed
            cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13), // curr_wind_degs
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // curr_wind_speed_name
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // curr_wind_dirr_code
            cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16), // curr_clouds_all
            cursor.isNull(offset + 17) ? null : cursor.getLong(offset + 17), // curr_visibility
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // precipitation_mode
            cursor.isNull(offset + 19) ? null : cursor.getDouble(offset + 19), // precipitation_value
            cursor.isNull(offset + 20) ? null : cursor.getDouble(offset + 20), // curr_rain_last3hrs
            cursor.isNull(offset + 21) ? null : cursor.getDouble(offset + 21), // curr_snow_last3hrs
            cursor.isNull(offset + 22) ? null : cursor.getLong(offset + 22), // curr_data_calc_time
            cursor.isNull(offset + 23) ? null : cursor.getLong(offset + 23), // curr_sys_sunrise_time
            cursor.isNull(offset + 24) ? null : cursor.getLong(offset + 24) // curr_sys_sunset_time
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CityWeatherCurrCondTable entity, int offset) {
        entity.setCity_id(cursor.getLong(offset + 0));
        entity.setCurr_weather_id(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setCurr_weather_main(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCurr_weather_desc(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCurr_weather_icon(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCurr_main_temp(cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5));
        entity.setCurr_main_pressure(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setCurr_main_humidity(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setCurr_main_temp_min(cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8));
        entity.setCurr_main_temp_max(cursor.isNull(offset + 9) ? null : cursor.getDouble(offset + 9));
        entity.setCurr_main_sea_level(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setCurr_main_grnd_level(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
        entity.setCurr_wind_speed(cursor.isNull(offset + 12) ? null : cursor.getDouble(offset + 12));
        entity.setCurr_wind_degs(cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13));
        entity.setCurr_wind_speed_name(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCurr_wind_dirr_code(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setCurr_clouds_all(cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16));
        entity.setCurr_visibility(cursor.isNull(offset + 17) ? null : cursor.getLong(offset + 17));
        entity.setPrecipitation_mode(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setPrecipitation_value(cursor.isNull(offset + 19) ? null : cursor.getDouble(offset + 19));
        entity.setCurr_rain_last3hrs(cursor.isNull(offset + 20) ? null : cursor.getDouble(offset + 20));
        entity.setCurr_snow_last3hrs(cursor.isNull(offset + 21) ? null : cursor.getDouble(offset + 21));
        entity.setCurr_data_calc_time(cursor.isNull(offset + 22) ? null : cursor.getLong(offset + 22));
        entity.setCurr_sys_sunrise_time(cursor.isNull(offset + 23) ? null : cursor.getLong(offset + 23));
        entity.setCurr_sys_sunset_time(cursor.isNull(offset + 24) ? null : cursor.getLong(offset + 24));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CityWeatherCurrCondTable entity, long rowId) {
        entity.setCity_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CityWeatherCurrCondTable entity) {
        if(entity != null) {
            return entity.getCity_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
