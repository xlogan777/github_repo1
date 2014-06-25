package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgDetailsTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgFnameTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.UrlImgFileTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.GalleryContentTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.RelatedItemsTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemMediaTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemDetailTableDao;
import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemsTableDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        ImgDetailsTableDao.createTable(db, ifNotExists);
        ImgFnameTableDao.createTable(db, ifNotExists);
        UrlImgFileTableDao.createTable(db, ifNotExists);
        GalleryContentTableDao.createTable(db, ifNotExists);
        RelatedItemsTableDao.createTable(db, ifNotExists);
        ContentItemLeadMediaTableDao.createTable(db, ifNotExists);
        ContentItemMediaTableDao.createTable(db, ifNotExists);
        ContentItemDetailTableDao.createTable(db, ifNotExists);
        ContentItemsTableDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        ImgDetailsTableDao.dropTable(db, ifExists);
        ImgFnameTableDao.dropTable(db, ifExists);
        UrlImgFileTableDao.dropTable(db, ifExists);
        GalleryContentTableDao.dropTable(db, ifExists);
        RelatedItemsTableDao.dropTable(db, ifExists);
        ContentItemLeadMediaTableDao.dropTable(db, ifExists);
        ContentItemMediaTableDao.dropTable(db, ifExists);
        ContentItemDetailTableDao.dropTable(db, ifExists);
        ContentItemsTableDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(ImgDetailsTableDao.class);
        registerDaoClass(ImgFnameTableDao.class);
        registerDaoClass(UrlImgFileTableDao.class);
        registerDaoClass(GalleryContentTableDao.class);
        registerDaoClass(RelatedItemsTableDao.class);
        registerDaoClass(ContentItemLeadMediaTableDao.class);
        registerDaoClass(ContentItemMediaTableDao.class);
        registerDaoClass(ContentItemDetailTableDao.class);
        registerDaoClass(ContentItemsTableDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
