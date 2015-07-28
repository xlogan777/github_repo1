package jmtechsvcs.myweatherapp.utils;

/**
 * Created by jimmy on 7/28/2015.
 */
/*
    this class allows to setting up different types of
    query params to get the from the dao layer to allow for
    same mechanism of getting data and allows the constraint of the data
    type to dictate the way it is returned.
 */
public class BeanQueryParams
{
    public BeanQueryParams()
    {
    }

    public enum T_Query_Param_Type
    {
        E_CITY_INFO_TABLE_TYPE,
        E_CURR_CITY_WEATHER_TABLE_TYPE,
        E_IMG_ICON_TABLE_TYPE
    }

    private T_Query_Param_Type queryParamType;

    private long cityId;
    private String iconId;

    public T_Query_Param_Type getQueryParamType(){
        return queryParamType;
    }

    public void setQueryParamType(T_Query_Param_Type querryParamType){
        this.queryParamType = querryParamType;
    }

    public long getCityId(){
        return cityId;
    }

    public void setCityId(long cityId){
        this.cityId = cityId;
    }

    public String getIconId(){
        return iconId;
    }

    public void setIconId(String iconId){
        this.iconId = iconId;
    }
}
