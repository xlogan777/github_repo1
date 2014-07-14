package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table URL_IMG_FILE_TABLE.
 */
public class UrlImgFileTable {

    private Long id;
    private long CmsID;
    private long UrlTypeID;
    /** Not-null value. */
    private String UrlLocation;
    private long imgFnameID;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient UrlImgFileTableDao myDao;

    private ImgFnameTable imgFnameTable;
    private Long imgFnameTable__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public UrlImgFileTable() {
    }

    public UrlImgFileTable(Long id) {
        this.id = id;
    }

    public UrlImgFileTable(Long id, long CmsID, long UrlTypeID, String UrlLocation, long imgFnameID) {
        this.id = id;
        this.CmsID = CmsID;
        this.UrlTypeID = UrlTypeID;
        this.UrlLocation = UrlLocation;
        this.imgFnameID = imgFnameID;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUrlImgFileTableDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCmsID() {
        return CmsID;
    }

    public void setCmsID(long CmsID) {
        this.CmsID = CmsID;
    }

    public long getUrlTypeID() {
        return UrlTypeID;
    }

    public void setUrlTypeID(long UrlTypeID) {
        this.UrlTypeID = UrlTypeID;
    }

    /** Not-null value. */
    public String getUrlLocation() {
        return UrlLocation;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUrlLocation(String UrlLocation) {
        this.UrlLocation = UrlLocation;
    }

    public long getImgFnameID() {
        return imgFnameID;
    }

    public void setImgFnameID(long imgFnameID) {
        this.imgFnameID = imgFnameID;
    }

    /** To-one relationship, resolved on first access. */
    public ImgFnameTable getImgFnameTable() {
        long __key = this.imgFnameID;
        if (imgFnameTable__resolvedKey == null || !imgFnameTable__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ImgFnameTableDao targetDao = daoSession.getImgFnameTableDao();
            ImgFnameTable imgFnameTableNew = targetDao.load(__key);
            synchronized (this) {
                imgFnameTable = imgFnameTableNew;
            	imgFnameTable__resolvedKey = __key;
            }
        }
        return imgFnameTable;
    }

    public void setImgFnameTable(ImgFnameTable imgFnameTable) {
        if (imgFnameTable == null) {
            throw new DaoException("To-one property 'imgFnameID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.imgFnameTable = imgFnameTable;
            imgFnameID = imgFnameTable.getId();
            imgFnameTable__resolvedKey = imgFnameID;
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
