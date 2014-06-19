package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.ContentItemLeadMediaTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table CONTENT_ITEM_LEAD_MEDIA_TABLE.
*/
public class ContentItemLeadMediaTableDao extends AbstractDao<ContentItemLeadMediaTable, Long> {

    public static final String TABLENAME = "CONTENT_ITEM_LEAD_MEDIA_TABLE";

    /**
     * Properties of entity ContentItemLeadMediaTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property CmsID = new Property(0, long.class, "CmsID", true, "CMS_ID");
        public final static Property LeadMediaContentType = new Property(1, String.class, "LeadMediaContentType", false, "LEAD_MEDIA_CONTENT_TYPE");
        public final static Property LeadMediaThumbnail = new Property(2, String.class, "LeadMediaThumbnail", false, "LEAD_MEDIA_THUMBNAIL");
        public final static Property LeadMediaExtID = new Property(3, String.class, "LeadMediaExtID", false, "LEAD_MEDIA_EXT_ID");
        public final static Property LeadEmbeddedVideo = new Property(4, String.class, "LeadEmbeddedVideo", false, "LEAD_EMBEDDED_VIDEO");
    };


    public ContentItemLeadMediaTableDao(DaoConfig config) {
        super(config);
    }
    
    public ContentItemLeadMediaTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'CONTENT_ITEM_LEAD_MEDIA_TABLE' (" + //
                "'CMS_ID' INTEGER PRIMARY KEY NOT NULL ," + // 0: CmsID
                "'LEAD_MEDIA_CONTENT_TYPE' TEXT NOT NULL ," + // 1: LeadMediaContentType
                "'LEAD_MEDIA_THUMBNAIL' TEXT NOT NULL ," + // 2: LeadMediaThumbnail
                "'LEAD_MEDIA_EXT_ID' TEXT NOT NULL ," + // 3: LeadMediaExtID
                "'LEAD_EMBEDDED_VIDEO' TEXT NOT NULL );"); // 4: LeadEmbeddedVideo
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'CONTENT_ITEM_LEAD_MEDIA_TABLE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ContentItemLeadMediaTable entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCmsID());
        stmt.bindString(2, entity.getLeadMediaContentType());
        stmt.bindString(3, entity.getLeadMediaThumbnail());
        stmt.bindString(4, entity.getLeadMediaExtID());
        stmt.bindString(5, entity.getLeadEmbeddedVideo());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ContentItemLeadMediaTable readEntity(Cursor cursor, int offset) {
        ContentItemLeadMediaTable entity = new ContentItemLeadMediaTable( //
            cursor.getLong(offset + 0), // CmsID
            cursor.getString(offset + 1), // LeadMediaContentType
            cursor.getString(offset + 2), // LeadMediaThumbnail
            cursor.getString(offset + 3), // LeadMediaExtID
            cursor.getString(offset + 4) // LeadEmbeddedVideo
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ContentItemLeadMediaTable entity, int offset) {
        entity.setCmsID(cursor.getLong(offset + 0));
        entity.setLeadMediaContentType(cursor.getString(offset + 1));
        entity.setLeadMediaThumbnail(cursor.getString(offset + 2));
        entity.setLeadMediaExtID(cursor.getString(offset + 3));
        entity.setLeadEmbeddedVideo(cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ContentItemLeadMediaTable entity, long rowId) {
        entity.setCmsID(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ContentItemLeadMediaTable entity) {
        if(entity != null) {
            return entity.getCmsID();
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
