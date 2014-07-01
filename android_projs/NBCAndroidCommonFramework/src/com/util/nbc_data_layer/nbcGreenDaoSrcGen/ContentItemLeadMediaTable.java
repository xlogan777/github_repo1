package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table CONTENT_ITEM_LEAD_MEDIA_TABLE.
 */
public class ContentItemLeadMediaTable {

    private long CmsID;
    /** Not-null value. */
    private String LeadMediaContentType;
    /** Not-null value. */
    private String LeadMediaExtID;
    /** Not-null value. */
    private String LeadEmbeddedVideo;
    private long LeadMediaThumbnailType;
    private long leadMediaThumbnailUrlImgTypeRowID;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ContentItemLeadMediaTableDao myDao;

    private UrlImgFileTable urlImgFileTable;
    private Long urlImgFileTable__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ContentItemLeadMediaTable() {
    }

    public ContentItemLeadMediaTable(long CmsID) {
        this.CmsID = CmsID;
    }

    public ContentItemLeadMediaTable(long CmsID, String LeadMediaContentType, String LeadMediaExtID, String LeadEmbeddedVideo, long LeadMediaThumbnailType, long leadMediaThumbnailUrlImgTypeRowID) {
        this.CmsID = CmsID;
        this.LeadMediaContentType = LeadMediaContentType;
        this.LeadMediaExtID = LeadMediaExtID;
        this.LeadEmbeddedVideo = LeadEmbeddedVideo;
        this.LeadMediaThumbnailType = LeadMediaThumbnailType;
        this.leadMediaThumbnailUrlImgTypeRowID = leadMediaThumbnailUrlImgTypeRowID;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContentItemLeadMediaTableDao() : null;
    }

    public long getCmsID() {
        return CmsID;
    }

    public void setCmsID(long CmsID) {
        this.CmsID = CmsID;
    }

    /** Not-null value. */
    public String getLeadMediaContentType() {
        return LeadMediaContentType;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLeadMediaContentType(String LeadMediaContentType) {
        this.LeadMediaContentType = LeadMediaContentType;
    }

    /** Not-null value. */
    public String getLeadMediaExtID() {
        return LeadMediaExtID;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLeadMediaExtID(String LeadMediaExtID) {
        this.LeadMediaExtID = LeadMediaExtID;
    }

    /** Not-null value. */
    public String getLeadEmbeddedVideo() {
        return LeadEmbeddedVideo;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLeadEmbeddedVideo(String LeadEmbeddedVideo) {
        this.LeadEmbeddedVideo = LeadEmbeddedVideo;
    }

    public long getLeadMediaThumbnailType() {
        return LeadMediaThumbnailType;
    }

    public void setLeadMediaThumbnailType(long LeadMediaThumbnailType) {
        this.LeadMediaThumbnailType = LeadMediaThumbnailType;
    }

    public long getLeadMediaThumbnailUrlImgTypeRowID() {
        return leadMediaThumbnailUrlImgTypeRowID;
    }

    public void setLeadMediaThumbnailUrlImgTypeRowID(long leadMediaThumbnailUrlImgTypeRowID) {
        this.leadMediaThumbnailUrlImgTypeRowID = leadMediaThumbnailUrlImgTypeRowID;
    }

    /** To-one relationship, resolved on first access. */
    public UrlImgFileTable getUrlImgFileTable() {
        long __key = this.leadMediaThumbnailUrlImgTypeRowID;
        if (urlImgFileTable__resolvedKey == null || !urlImgFileTable__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UrlImgFileTableDao targetDao = daoSession.getUrlImgFileTableDao();
            UrlImgFileTable urlImgFileTableNew = targetDao.load(__key);
            synchronized (this) {
                urlImgFileTable = urlImgFileTableNew;
            	urlImgFileTable__resolvedKey = __key;
            }
        }
        return urlImgFileTable;
    }

    public void setUrlImgFileTable(UrlImgFileTable urlImgFileTable) {
        if (urlImgFileTable == null) {
            throw new DaoException("To-one property 'leadMediaThumbnailUrlImgTypeRowID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.urlImgFileTable = urlImgFileTable;
            leadMediaThumbnailUrlImgTypeRowID = urlImgFileTable.getId();
            urlImgFileTable__resolvedKey = leadMediaThumbnailUrlImgTypeRowID;
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
    // KEEP METHODS END

}
