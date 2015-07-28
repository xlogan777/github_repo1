package jmtechsvcs.myweatherapp.greendaosrcgen;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table CITY_INFO_TABLE.
 */
public class CityInfoTable {

    private long city_id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String country;
    private double lon;
    private double lat;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public CityInfoTable() {
    }

    public CityInfoTable(long city_id) {
        this.city_id = city_id;
    }

    public CityInfoTable(long city_id, String name, String country, double lon, double lat) {
        this.city_id = city_id;
        this.name = name;
        this.country = country;
        this.lon = lon;
        this.lat = lat;
    }

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getCountry() {
        return country;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCountry(String country) {
        this.country = country;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}