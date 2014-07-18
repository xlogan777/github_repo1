package com.util.nbc_data_layer.nbcGreenDaoSrcGen;


import com.util.nbc_data_layer.nbcGreenDaoSrcGen.DaoSession;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS


// KEEP INCLUDES - put your custom includes here
import com.util.nbc_data_layer.EntityItemIface;
import com.util.nbc_data_layer.EntityVisitorIface;
import com.util.nbc_data_layer.NBCDataBaseHelper.T_UrlTypeToId;
import com.util.nbc_data_layer.dataTypes.CntTypeIface;
//public class ContentItemsTable implements EntityItemIface, CntTypeIface{
// KEEP INCLUDES END
/**
 * Entity mapped to table CONTENT_ITEMS_TABLE.
 */
public class ContentItemsTable implements EntityItemIface, CntTypeIface{

    private long CmsID;
    /** Not-null value. */
    private String ContentType;
    private boolean Sponsored;
    /** Not-null value. */
    private String ShareUrl;
    /** Not-null value. */
    private String Link;
    /** Not-null value. */
    private String Guid;
    /** Not-null value. */
    private String PubDate;
    /** Not-null value. */
    private String PubDisplayDate;
    /** Not-null value. */
    private String SlugKeyword;
    /** Not-null value. */
    private String ContentTargetPath;
    private long cntLeadMediaCmsID;
    private long cntMediaCmsID;
    private long cntItemDetailCmsID;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ContentItemsTableDao myDao;

    private ContentItemLeadMediaTable contentItemLeadMediaTable;
    private Long contentItemLeadMediaTable__resolvedKey;

    private ContentItemMediaTable contentItemMediaTable;
    private Long contentItemMediaTable__resolvedKey;

    private ContentItemDetailTable contentItemDetailTable;
    private Long contentItemDetailTable__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ContentItemsTable() {
    }

    public ContentItemsTable(long CmsID) {
        this.CmsID = CmsID;
    }

    public ContentItemsTable(long CmsID, String ContentType, boolean Sponsored, String ShareUrl, String Link, String Guid, String PubDate, String PubDisplayDate, String SlugKeyword, String ContentTargetPath, long cntLeadMediaCmsID, long cntMediaCmsID, long cntItemDetailCmsID) {
        this.CmsID = CmsID;
        this.ContentType = ContentType;
        this.Sponsored = Sponsored;
        this.ShareUrl = ShareUrl;
        this.Link = Link;
        this.Guid = Guid;
        this.PubDate = PubDate;
        this.PubDisplayDate = PubDisplayDate;
        this.SlugKeyword = SlugKeyword;
        this.ContentTargetPath = ContentTargetPath;
        this.cntLeadMediaCmsID = cntLeadMediaCmsID;
        this.cntMediaCmsID = cntMediaCmsID;
        this.cntItemDetailCmsID = cntItemDetailCmsID;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContentItemsTableDao() : null;
    }

    public long getCmsID() {
        return CmsID;
    }

    public void setCmsID(long CmsID) {
        this.CmsID = CmsID;
    }

    /** Not-null value. */
    public String getContentType() {
        return ContentType;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContentType(String ContentType) {
        this.ContentType = ContentType;
    }

    public boolean getSponsored() {
        return Sponsored;
    }

    public void setSponsored(boolean Sponsored) {
        this.Sponsored = Sponsored;
    }

    /** Not-null value. */
    public String getShareUrl() {
        return ShareUrl;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setShareUrl(String ShareUrl) {
        this.ShareUrl = ShareUrl;
    }

    /** Not-null value. */
    public String getLink() {
        return Link;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLink(String Link) {
        this.Link = Link;
    }

    /** Not-null value. */
    public String getGuid() {
        return Guid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    /** Not-null value. */
    public String getPubDate() {
        return PubDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPubDate(String PubDate) {
        this.PubDate = PubDate;
    }

    /** Not-null value. */
    public String getPubDisplayDate() {
        return PubDisplayDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPubDisplayDate(String PubDisplayDate) {
        this.PubDisplayDate = PubDisplayDate;
    }

    /** Not-null value. */
    public String getSlugKeyword() {
        return SlugKeyword;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSlugKeyword(String SlugKeyword) {
        this.SlugKeyword = SlugKeyword;
    }

    /** Not-null value. */
    public String getContentTargetPath() {
        return ContentTargetPath;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContentTargetPath(String ContentTargetPath) {
        this.ContentTargetPath = ContentTargetPath;
    }

    public long getCntLeadMediaCmsID() {
        return cntLeadMediaCmsID;
    }

    public void setCntLeadMediaCmsID(long cntLeadMediaCmsID) {
        this.cntLeadMediaCmsID = cntLeadMediaCmsID;
    }

    public long getCntMediaCmsID() {
        return cntMediaCmsID;
    }

    public void setCntMediaCmsID(long cntMediaCmsID) {
        this.cntMediaCmsID = cntMediaCmsID;
    }

    public long getCntItemDetailCmsID() {
        return cntItemDetailCmsID;
    }

    public void setCntItemDetailCmsID(long cntItemDetailCmsID) {
        this.cntItemDetailCmsID = cntItemDetailCmsID;
    }

    /** To-one relationship, resolved on first access. */
    public ContentItemLeadMediaTable getContentItemLeadMediaTable() {
        long __key = this.cntLeadMediaCmsID;
        if (contentItemLeadMediaTable__resolvedKey == null || !contentItemLeadMediaTable__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContentItemLeadMediaTableDao targetDao = daoSession.getContentItemLeadMediaTableDao();
            ContentItemLeadMediaTable contentItemLeadMediaTableNew = targetDao.load(__key);
            synchronized (this) {
                contentItemLeadMediaTable = contentItemLeadMediaTableNew;
            	contentItemLeadMediaTable__resolvedKey = __key;
            }
        }
        return contentItemLeadMediaTable;
    }

    public void setContentItemLeadMediaTable(ContentItemLeadMediaTable contentItemLeadMediaTable) {
        if (contentItemLeadMediaTable == null) {
            throw new DaoException("To-one property 'cntLeadMediaCmsID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.contentItemLeadMediaTable = contentItemLeadMediaTable;
            cntLeadMediaCmsID = contentItemLeadMediaTable.getCmsID();
            contentItemLeadMediaTable__resolvedKey = cntLeadMediaCmsID;
        }
    }

    /** To-one relationship, resolved on first access. */
    public ContentItemMediaTable getContentItemMediaTable() {
        long __key = this.cntMediaCmsID;
        if (contentItemMediaTable__resolvedKey == null || !contentItemMediaTable__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContentItemMediaTableDao targetDao = daoSession.getContentItemMediaTableDao();
            ContentItemMediaTable contentItemMediaTableNew = targetDao.load(__key);
            synchronized (this) {
                contentItemMediaTable = contentItemMediaTableNew;
            	contentItemMediaTable__resolvedKey = __key;
            }
        }
        return contentItemMediaTable;
    }

    public void setContentItemMediaTable(ContentItemMediaTable contentItemMediaTable) {
        if (contentItemMediaTable == null) {
            throw new DaoException("To-one property 'cntMediaCmsID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.contentItemMediaTable = contentItemMediaTable;
            cntMediaCmsID = contentItemMediaTable.getCmsID();
            contentItemMediaTable__resolvedKey = cntMediaCmsID;
        }
    }

    /** To-one relationship, resolved on first access. */
    public ContentItemDetailTable getContentItemDetailTable() {
        long __key = this.cntItemDetailCmsID;
        if (contentItemDetailTable__resolvedKey == null || !contentItemDetailTable__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContentItemDetailTableDao targetDao = daoSession.getContentItemDetailTableDao();
            ContentItemDetailTable contentItemDetailTableNew = targetDao.load(__key);
            synchronized (this) {
                contentItemDetailTable = contentItemDetailTableNew;
            	contentItemDetailTable__resolvedKey = __key;
            }
        }
        return contentItemDetailTable;
    }

    public void setContentItemDetailTable(ContentItemDetailTable contentItemDetailTable) {
        if (contentItemDetailTable == null) {
            throw new DaoException("To-one property 'cntItemDetailCmsID' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.contentItemDetailTable = contentItemDetailTable;
            cntItemDetailCmsID = contentItemDetailTable.getCmsID();
            contentItemDetailTable__resolvedKey = cntItemDetailCmsID;
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
	public void accept(EntityVisitorIface entityVisitorIface, T_UrlTypeToId typeID, UrlImgFileTable urlImgFileTable) 
	{
		//dont do anything here
	}

	@Override
	public void accept(EntityVisitorIface entityVisitorIface, DaoSession daoSession, ContentItemsTable cntItemsTable) 
	{
		//dont do anything here
	}

	@Override
	public void accept(EntityVisitorIface entityVisitorIface, DaoSession daoSession) 
	{
		entityVisitorIface.visit(this, daoSession);
	}
    // KEEP METHODS END

}
