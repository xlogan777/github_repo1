package jmtechsvcs.myweatherapp.greendaosrcgenpkg;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherIconTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WEATHER_ICON_TABLE".
*/
public class WeatherIconTableDao extends AbstractDao<WeatherIconTable, String> {

    public static final String TABLENAME = "WEATHER_ICON_TABLE";

    /**
     * Properties of entity WeatherIconTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Icon_id = new Property(0, String.class, "icon_id", true, "ICON_ID");
        public final static Property Icon_url = new Property(1, String.class, "icon_url", false, "ICON_URL");
        public final static Property Image_path = new Property(2, String.class, "image_path", false, "IMAGE_PATH");
        public final static Property Image_raw = new Property(3, byte[].class, "image_raw", false, "IMAGE_RAW");
    };


    public WeatherIconTableDao(DaoConfig config) {
        super(config);
    }
    
    public WeatherIconTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WEATHER_ICON_TABLE\" (" + //
                "\"ICON_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: icon_id
                "\"ICON_URL\" TEXT NOT NULL ," + // 1: icon_url
                "\"IMAGE_PATH\" TEXT NOT NULL ," + // 2: image_path
                "\"IMAGE_RAW\" BLOB NOT NULL );"); // 3: image_raw
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WEATHER_ICON_TABLE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, WeatherIconTable entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getIcon_id());
        stmt.bindString(2, entity.getIcon_url());
        stmt.bindString(3, entity.getImage_path());
        stmt.bindBlob(4, entity.getImage_raw());
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public WeatherIconTable readEntity(Cursor cursor, int offset) {
        WeatherIconTable entity = new WeatherIconTable( //
            cursor.getString(offset + 0), // icon_id
            cursor.getString(offset + 1), // icon_url
            cursor.getString(offset + 2), // image_path
            cursor.getBlob(offset + 3) // image_raw
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, WeatherIconTable entity, int offset) {
        entity.setIcon_id(cursor.getString(offset + 0));
        entity.setIcon_url(cursor.getString(offset + 1));
        entity.setImage_path(cursor.getString(offset + 2));
        entity.setImage_raw(cursor.getBlob(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(WeatherIconTable entity, long rowId) {
        return entity.getIcon_id();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(WeatherIconTable entity) {
        if(entity != null) {
            return entity.getIcon_id();
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
