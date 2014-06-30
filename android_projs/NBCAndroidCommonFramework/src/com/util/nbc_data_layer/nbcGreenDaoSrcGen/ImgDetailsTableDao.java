package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgDetailsTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table IMG_DETAILS_TABLE.
*/
public class ImgDetailsTableDao extends AbstractDao<ImgDetailsTable, Long> {

    public static final String TABLENAME = "IMG_DETAILS_TABLE";

    /**
     * Properties of entity ImgDetailsTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property ImgCredit = new Property(1, String.class, "ImgCredit", false, "IMG_CREDIT");
        public final static Property ImgCaption = new Property(2, String.class, "ImgCaption", false, "IMG_CAPTION");
    };


    public ImgDetailsTableDao(DaoConfig config) {
        super(config);
    }
    
    public ImgDetailsTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'IMG_DETAILS_TABLE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: id
                "'IMG_CREDIT' TEXT NOT NULL ," + // 1: ImgCredit
                "'IMG_CAPTION' TEXT NOT NULL );"); // 2: ImgCaption
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'IMG_DETAILS_TABLE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ImgDetailsTable entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindString(2, entity.getImgCredit());
        stmt.bindString(3, entity.getImgCaption());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ImgDetailsTable readEntity(Cursor cursor, int offset) {
        ImgDetailsTable entity = new ImgDetailsTable( //
            cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // ImgCredit
            cursor.getString(offset + 2) // ImgCaption
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ImgDetailsTable entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setImgCredit(cursor.getString(offset + 1));
        entity.setImgCaption(cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ImgDetailsTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ImgDetailsTable entity) {
        if(entity != null) {
            return entity.getId();
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
