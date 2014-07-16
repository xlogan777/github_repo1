package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ImgFnameTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table IMG_FNAME_TABLE.
*/
public class ImgFnameTableDao extends AbstractDao<ImgFnameTable, Long> {

    public static final String TABLENAME = "IMG_FNAME_TABLE";

    /**
     * Properties of entity ImgFnameTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ImgFname = new Property(1, String.class, "ImgFname", false, "IMG_FNAME");
        public final static Property ImgWidth = new Property(2, long.class, "ImgWidth", false, "IMG_WIDTH");
        public final static Property ImgHeight = new Property(3, long.class, "ImgHeight", false, "IMG_HEIGHT");
        public final static Property ImgCaption = new Property(4, String.class, "ImgCaption", false, "IMG_CAPTION");
        public final static Property ImgCredit = new Property(5, String.class, "ImgCredit", false, "IMG_CREDIT");
        public final static Property ImgUrlLocation = new Property(6, String.class, "ImgUrlLocation", false, "IMG_URL_LOCATION");
    };


    public ImgFnameTableDao(DaoConfig config) {
        super(config);
    }
    
    public ImgFnameTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'IMG_FNAME_TABLE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'IMG_FNAME' TEXT NOT NULL ," + // 1: ImgFname
                "'IMG_WIDTH' INTEGER NOT NULL ," + // 2: ImgWidth
                "'IMG_HEIGHT' INTEGER NOT NULL ," + // 3: ImgHeight
                "'IMG_CAPTION' TEXT NOT NULL ," + // 4: ImgCaption
                "'IMG_CREDIT' TEXT NOT NULL ," + // 5: ImgCredit
                "'IMG_URL_LOCATION' TEXT NOT NULL );"); // 6: ImgUrlLocation
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'IMG_FNAME_TABLE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ImgFnameTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getImgFname());
        stmt.bindLong(3, entity.getImgWidth());
        stmt.bindLong(4, entity.getImgHeight());
        stmt.bindString(5, entity.getImgCaption());
        stmt.bindString(6, entity.getImgCredit());
        stmt.bindString(7, entity.getImgUrlLocation());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ImgFnameTable readEntity(Cursor cursor, int offset) {
        ImgFnameTable entity = new ImgFnameTable( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // ImgFname
            cursor.getLong(offset + 2), // ImgWidth
            cursor.getLong(offset + 3), // ImgHeight
            cursor.getString(offset + 4), // ImgCaption
            cursor.getString(offset + 5), // ImgCredit
            cursor.getString(offset + 6) // ImgUrlLocation
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ImgFnameTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImgFname(cursor.getString(offset + 1));
        entity.setImgWidth(cursor.getLong(offset + 2));
        entity.setImgHeight(cursor.getLong(offset + 3));
        entity.setImgCaption(cursor.getString(offset + 4));
        entity.setImgCredit(cursor.getString(offset + 5));
        entity.setImgUrlLocation(cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ImgFnameTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ImgFnameTable entity) {
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
