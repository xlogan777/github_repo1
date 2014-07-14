package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import com.util.nbc_data_layer.EntityItemIface;
import com.util.nbc_data_layer.EntityVisitorIface;
import com.util.nbc_data_layer.NBCDataBaseHelper;
//public class ContentItemMediaTable implements EntityItemIface{
// KEEP INCLUDES END
/**
 * Entity mapped to table CONTENT_ITEM_MEDIA_TABLE.
 */
public class ContentItemMediaTable implements EntityItemIface{

    private long CmsID;
    private long MediaUrlType;
    private long MediaPhotoThumbnailUrlType;
    private long MediaThumbnailUrlType;
    private long mediaUrlImgTypeRowID;
    private long mediaPhotoThumbnailUrlImgTypeRowID;
    private long mediaThumbnailUrlImgTypeRowID;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ContentItemMediaTableDao myDao;

    private UrlImgFileTable mediaUrlImgType;
    private Long mediaUrlImgType__resolvedKey;

    private UrlImgFileTable mediaPhotoThumbnailUrlImgType;
    private Long mediaPhotoThumbnailUrlImgType__resolvedKey;

    private UrlImgFileTable mediaThumbnailUrlImgType;
    private Long mediaThumbnailUrlImgType__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ContentItemMediaTable() {
    }

    public ContentItemMediaTable(long CmsID) {
        this.CmsID = CmsID;
    }

    public ContentItemMediaTable(long CmsID, long MediaUrlType, long MediaPhotoThumbnailUrlType, long MediaThumbnailUrlType, long mediaUrlImgTypeRowID, long mediaPhotoThumbnailUrlImgTypeRowID, long mediaThumbnailUrlImgTypeRowID) {
        this.CmsID = CmsID;
        this.MediaUrlType = MediaUrlType;
        this.MediaPhotoThumbnailUrlType = MediaPhotoThumbnailUrlType;
        this.MediaThumbnailUrlType = MediaThumbnailUrlType;
        this.mediaUrlImgTypeRowID = mediaUrlImgTypeRowID;
        this.mediaPhotoThumbnailUrlImgTypeRowID = mediaPhotoThumbnailUrlImgTypeRowID;
        this.mediaThumbnailUrlImgTypeRowID = mediaThumbnailUrlImgTypeRowID;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContentItemMediaTableDao() : null;
    }

    public long getCmsID() {
        return CmsID;
    }

    public void setCmsID(long CmsID) {
        this.CmsID = CmsID;
    }

    public long getMediaUrlType() {
        return MediaUrlType;
    }

    public void setMediaUrlType(long MediaUrlType) {
        this.MediaUrlType = MediaUrlType;
    }

    public long getMediaPhotoThumbnailUrlType() {
        return MediaPhotoThumbnailUrlType;
    }

    public void setMediaPhotoThumbnailUrlType(long MediaPhotoThumbnailUrlType) {
        this.MediaPhotoThumbnailUrlType = MediaPhotoThumbnailUrlType;
    }

    public long getMediaThumbnailUrlType() {
        return MediaThumbnailUrlType;
    }

    public void setMediaThumbnailUrlType(long MediaThumbnailUrlType) {
        this.MediaThumbnailUrlType = MediaThumbnailUrlType;
    }

    public long getMediaUrlImgTypeRowID() {
        return mediaUrlImgTypeRowID;
    }

    public void setMediaUrlImgTypeRowID(long mediaUrlImgTypeRowID) {
        this.mediaUrlImgTypeRowID = mediaUrlImgTypeRowID;
    }

    public long getMediaPhotoThumbnailUrlImgTypeRowID() {
        return mediaPhotoThumbnailUrlImgTypeRowID;
    }

    public void setMediaPhotoThumbnailUrlImgTypeRowID(long mediaPhotoThumbnailUrlImgTypeRowID) {
        this.mediaPhotoThumbnailUrlImgTypeRowID = mediaPhotoThumbnailUrlImgTypeRowID;
    }

    public long getMediaThumbnailUrlImgTypeRowID() {
        return mediaThumbnailUrlImgTypeRowID;
    }

    public void setMediaThumbnailUrlImgTypeRowID(long mediaThumbnailUrlImgTypeRowID) {
        this.mediaThumbnailUrlImgTypeRowID = mediaThumbnailUrlImgTypeRowID;
    }

    /** To-one relationship, resolved on first access. */
    public UrlImgFileTable getMediaUrlImgType() {
        long __key = this.mediaUrlImgTypeRowID;
        if (mediaUrlImgType__resolvedKey == null || !mediaUrlImgType__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UrlImgFileTableDao targetDao = daoSession.getUrlImgFileTableDao();
            UrlImgFileTable mediaUrlImgTypeNew = targetDao.load(__key);
            synchronized (this) {
                mediaUrlImgType = mediaUrlImgTypeNew;
            	mediaUrlImgType__resolvedKey = __key;
            }
        }
        return mediaUrlImgType;
    }

    public void setMediaUrlImgType(UrlImgFileTable mediaUrlImgType) {
        if (mediaUrlImgType == null) {
            throw new DaoException("To-one property 'mediaUrlImgTypeRowID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.mediaUrlImgType = mediaUrlImgType;
            mediaUrlImgTypeRowID = mediaUrlImgType.getId();
            mediaUrlImgType__resolvedKey = mediaUrlImgTypeRowID;
        }
    }

    /** To-one relationship, resolved on first access. */
    public UrlImgFileTable getMediaPhotoThumbnailUrlImgType() {
        long __key = this.mediaPhotoThumbnailUrlImgTypeRowID;
        if (mediaPhotoThumbnailUrlImgType__resolvedKey == null || !mediaPhotoThumbnailUrlImgType__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UrlImgFileTableDao targetDao = daoSession.getUrlImgFileTableDao();
            UrlImgFileTable mediaPhotoThumbnailUrlImgTypeNew = targetDao.load(__key);
            synchronized (this) {
                mediaPhotoThumbnailUrlImgType = mediaPhotoThumbnailUrlImgTypeNew;
            	mediaPhotoThumbnailUrlImgType__resolvedKey = __key;
            }
        }
        return mediaPhotoThumbnailUrlImgType;
    }

    public void setMediaPhotoThumbnailUrlImgType(UrlImgFileTable mediaPhotoThumbnailUrlImgType) {
        if (mediaPhotoThumbnailUrlImgType == null) {
            throw new DaoException("To-one property 'mediaPhotoThumbnailUrlImgTypeRowID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.mediaPhotoThumbnailUrlImgType = mediaPhotoThumbnailUrlImgType;
            mediaPhotoThumbnailUrlImgTypeRowID = mediaPhotoThumbnailUrlImgType.getId();
            mediaPhotoThumbnailUrlImgType__resolvedKey = mediaPhotoThumbnailUrlImgTypeRowID;
        }
    }

    /** To-one relationship, resolved on first access. */
    public UrlImgFileTable getMediaThumbnailUrlImgType() {
        long __key = this.mediaThumbnailUrlImgTypeRowID;
        if (mediaThumbnailUrlImgType__resolvedKey == null || !mediaThumbnailUrlImgType__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UrlImgFileTableDao targetDao = daoSession.getUrlImgFileTableDao();
            UrlImgFileTable mediaThumbnailUrlImgTypeNew = targetDao.load(__key);
            synchronized (this) {
                mediaThumbnailUrlImgType = mediaThumbnailUrlImgTypeNew;
            	mediaThumbnailUrlImgType__resolvedKey = __key;
            }
        }
        return mediaThumbnailUrlImgType;
    }

    public void setMediaThumbnailUrlImgType(UrlImgFileTable mediaThumbnailUrlImgType) {
        if (mediaThumbnailUrlImgType == null) {
            throw new DaoException("To-one property 'mediaThumbnailUrlImgTypeRowID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.mediaThumbnailUrlImgType = mediaThumbnailUrlImgType;
            mediaThumbnailUrlImgTypeRowID = mediaThumbnailUrlImgType.getId();
            mediaThumbnailUrlImgType__resolvedKey = mediaThumbnailUrlImgTypeRowID;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
	@Override
	public void accept(EntityVisitorIface entityVisitorIface, NBCDataBaseHelper.T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable) 
	{
		entityVisitorIface.visit(this,typeID,urlImgFileTable);
	}
	
	@Override
	public void accept(EntityVisitorIface entityVisitorIface, DaoSession daoSession, ContentItemsTable cntItemsTable) 
	{
		entityVisitorIface.visit(this, daoSession, cntItemsTable);
	}
	
	@Override
	public void accept(EntityVisitorIface entityVisitorIface, DaoSession daoSession) 
	{	
		//dont do anything here.
	}
    // KEEP METHODS END
}
