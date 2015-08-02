package jmtechsvcs.myweatherapp.greendaosrcgen;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "WEATHER_ICON_TABLE".
 */
public class WeatherIconTable {

    private Long id;
    /** Not-null value. */
    private String icon_id;
    /** Not-null value. */
    private String icon_url;
    /** Not-null value. */
    private String image_path;
    private byte[] image_raw;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public WeatherIconTable() {
    }

    public WeatherIconTable(Long id) {
        this.id = id;
    }

    public WeatherIconTable(Long id, String icon_id, String icon_url, String image_path, byte[] image_raw) {
        this.id = id;
        this.icon_id = icon_id;
        this.icon_url = icon_url;
        this.image_path = image_path;
        this.image_raw = image_raw;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getIcon_id() {
        return icon_id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setIcon_id(String icon_id) {
        this.icon_id = icon_id;
    }

    /** Not-null value. */
    public String getIcon_url() {
        return icon_url;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    /** Not-null value. */
    public String getImage_path() {
        return image_path;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public byte[] getImage_raw() {
        return image_raw;
    }

    public void setImage_raw(byte[] image_raw) {
        this.image_raw = image_raw;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
