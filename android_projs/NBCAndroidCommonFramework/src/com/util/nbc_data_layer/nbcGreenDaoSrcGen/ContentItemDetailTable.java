package com.util.nbc_data_layer.nbcGreenDaoSrcGen;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table CONTENT_ITEM_DETAIL_TABLE.
 */
public class ContentItemDetailTable {

    private long CmsID;
    /** Not-null value. */
    private String DisplayTimeStamp;
    private boolean Flag;
    /** Not-null value. */
    private String Title;
    /** Not-null value. */
    private String FullTitle;
    /** Not-null value. */
    private String SubTitle;
    /** Not-null value. */
    private String Description;
    private int VideoLength;
    private boolean UsingPlaceholderImg;
    private boolean USWorldTarget;
    /** Not-null value. */
    private String ContentSectionName;
    /** Not-null value. */
    private String ContentSectionNameCss;
    /** Not-null value. */
    private String ContentSubSectionName;
    /** Not-null value. */
    private String ContentSubSectionNameCss;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ContentItemDetailTable() {
    }

    public ContentItemDetailTable(long CmsID) {
        this.CmsID = CmsID;
    }

    public ContentItemDetailTable(long CmsID, String DisplayTimeStamp, boolean Flag, String Title, String FullTitle, String SubTitle, String Description, int VideoLength, boolean UsingPlaceholderImg, boolean USWorldTarget, String ContentSectionName, String ContentSectionNameCss, String ContentSubSectionName, String ContentSubSectionNameCss) {
        this.CmsID = CmsID;
        this.DisplayTimeStamp = DisplayTimeStamp;
        this.Flag = Flag;
        this.Title = Title;
        this.FullTitle = FullTitle;
        this.SubTitle = SubTitle;
        this.Description = Description;
        this.VideoLength = VideoLength;
        this.UsingPlaceholderImg = UsingPlaceholderImg;
        this.USWorldTarget = USWorldTarget;
        this.ContentSectionName = ContentSectionName;
        this.ContentSectionNameCss = ContentSectionNameCss;
        this.ContentSubSectionName = ContentSubSectionName;
        this.ContentSubSectionNameCss = ContentSubSectionNameCss;
    }

    public long getCmsID() {
        return CmsID;
    }

    public void setCmsID(long CmsID) {
        this.CmsID = CmsID;
    }

    /** Not-null value. */
    public String getDisplayTimeStamp() {
        return DisplayTimeStamp;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDisplayTimeStamp(String DisplayTimeStamp) {
        this.DisplayTimeStamp = DisplayTimeStamp;
    }

    public boolean getFlag() {
        return Flag;
    }

    public void setFlag(boolean Flag) {
        this.Flag = Flag;
    }

    /** Not-null value. */
    public String getTitle() {
        return Title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /** Not-null value. */
    public String getFullTitle() {
        return FullTitle;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFullTitle(String FullTitle) {
        this.FullTitle = FullTitle;
    }

    /** Not-null value. */
    public String getSubTitle() {
        return SubTitle;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setSubTitle(String SubTitle) {
        this.SubTitle = SubTitle;
    }

    /** Not-null value. */
    public String getDescription() {
        return Description;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getVideoLength() {
        return VideoLength;
    }

    public void setVideoLength(int VideoLength) {
        this.VideoLength = VideoLength;
    }

    public boolean getUsingPlaceholderImg() {
        return UsingPlaceholderImg;
    }

    public void setUsingPlaceholderImg(boolean UsingPlaceholderImg) {
        this.UsingPlaceholderImg = UsingPlaceholderImg;
    }

    public boolean getUSWorldTarget() {
        return USWorldTarget;
    }

    public void setUSWorldTarget(boolean USWorldTarget) {
        this.USWorldTarget = USWorldTarget;
    }

    /** Not-null value. */
    public String getContentSectionName() {
        return ContentSectionName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContentSectionName(String ContentSectionName) {
        this.ContentSectionName = ContentSectionName;
    }

    /** Not-null value. */
    public String getContentSectionNameCss() {
        return ContentSectionNameCss;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContentSectionNameCss(String ContentSectionNameCss) {
        this.ContentSectionNameCss = ContentSectionNameCss;
    }

    /** Not-null value. */
    public String getContentSubSectionName() {
        return ContentSubSectionName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContentSubSectionName(String ContentSubSectionName) {
        this.ContentSubSectionName = ContentSubSectionName;
    }

    /** Not-null value. */
    public String getContentSubSectionNameCss() {
        return ContentSubSectionNameCss;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setContentSubSectionNameCss(String ContentSubSectionNameCss) {
        this.ContentSubSectionNameCss = ContentSubSectionNameCss;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
