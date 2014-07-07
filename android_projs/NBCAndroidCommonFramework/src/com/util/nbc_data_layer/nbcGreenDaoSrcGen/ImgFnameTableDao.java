package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
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
        public final static Property ImageFname = new Property(1, String.class, "ImageFname", false, "IMAGE_FNAME");
        public final static Property ImgHeight = new Property(2, long.class, "ImgHeight", false, "IMG_HEIGHT");
        public final static Property ImgWidth = new Property(3, long.class, "ImgWidth", false, "IMG_WIDTH");
        public final static Property ImgDetailsID = new Property(4, long.class, "ImgDetailsID", false, "IMG_DETAILS_ID");
    };

    private DaoSession daoSession;


    public ImgFnameTableDao(DaoConfig config) {
        super(config);
    }
    
    public ImgFnameTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'IMG_FNAME_TABLE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'IMAGE_FNAME' TEXT NOT NULL ," + // 1: ImageFname
                "'IMG_HEIGHT' INTEGER NOT NULL ," + // 2: ImgHeight
                "'IMG_WIDTH' INTEGER NOT NULL ," + // 3: ImgWidth
                "'IMG_DETAILS_ID' INTEGER NOT NULL );"); // 4: ImgDetailsID
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
        stmt.bindString(2, entity.getImageFname());
        stmt.bindLong(3, entity.getImgHeight());
        stmt.bindLong(4, entity.getImgWidth());
        stmt.bindLong(5, entity.getImgDetailsID());
    }

    @Override
    protected void attachEntity(ImgFnameTable entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
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
            cursor.getString(offset + 1), // ImageFname
            cursor.getLong(offset + 2), // ImgHeight
            cursor.getLong(offset + 3), // ImgWidth
            cursor.getLong(offset + 4) // ImgDetailsID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ImgFnameTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImageFname(cursor.getString(offset + 1));
        entity.setImgHeight(cursor.getLong(offset + 2));
        entity.setImgWidth(cursor.getLong(offset + 3));
        entity.setImgDetailsID(cursor.getLong(offset + 4));
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
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getImgDetailsTableDao().getAllColumns());
            builder.append(" FROM IMG_FNAME_TABLE T");
            builder.append(" LEFT JOIN IMG_DETAILS_TABLE T0 ON T.'IMG_DETAILS_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected ImgFnameTable loadCurrentDeep(Cursor cursor, boolean lock) {
        ImgFnameTable entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        ImgDetailsTable imgDetailsTable = loadCurrentOther(daoSession.getImgDetailsTableDao(), cursor, offset);
         if(imgDetailsTable != null) {
            entity.setImgDetailsTable(imgDetailsTable);
        }

        return entity;    
    }

    public ImgFnameTable loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<ImgFnameTable> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<ImgFnameTable> list = new ArrayList<ImgFnameTable>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<ImgFnameTable> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<ImgFnameTable> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
