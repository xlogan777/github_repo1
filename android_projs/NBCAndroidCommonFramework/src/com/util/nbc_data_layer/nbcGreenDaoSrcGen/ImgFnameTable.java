package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table IMG_FNAME_TABLE.
 */
public class ImgFnameTable {

    private long id;
    /** Not-null value. */
    private String ImageFname;
    private long ImgDetailsID;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ImgFnameTableDao myDao;

    private ImgDetailsTable imgDetailsTable;
    private Long imgDetailsTable__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ImgFnameTable() {
    }

    public ImgFnameTable(long id) {
        this.id = id;
    }

    public ImgFnameTable(long id, String ImageFname, long ImgDetailsID) {
        this.id = id;
        this.ImageFname = ImageFname;
        this.ImgDetailsID = ImgDetailsID;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getImgFnameTableDao() : null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getImageFname() {
        return ImageFname;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setImageFname(String ImageFname) {
        this.ImageFname = ImageFname;
    }

    public long getImgDetailsID() {
        return ImgDetailsID;
    }

    public void setImgDetailsID(long ImgDetailsID) {
        this.ImgDetailsID = ImgDetailsID;
    }

    /** To-one relationship, resolved on first access. */
    public ImgDetailsTable getImgDetailsTable() {
        long __key = this.ImgDetailsID;
        if (imgDetailsTable__resolvedKey == null || !imgDetailsTable__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ImgDetailsTableDao targetDao = daoSession.getImgDetailsTableDao();
            ImgDetailsTable imgDetailsTableNew = targetDao.load(__key);
            synchronized (this) {
                imgDetailsTable = imgDetailsTableNew;
            	imgDetailsTable__resolvedKey = __key;
            }
        }
        return imgDetailsTable;
    }

    public void setImgDetailsTable(ImgDetailsTable imgDetailsTable) {
        if (imgDetailsTable == null) {
            throw new DaoException("To-one property 'ImgDetailsID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.imgDetailsTable = imgDetailsTable;
            ImgDetailsID = imgDetailsTable.getId();
            imgDetailsTable__resolvedKey = ImgDetailsID;
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
