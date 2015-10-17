package jmtechsvcs.myweatherapp.activitypkg;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.dbpkg.BeanQueryParams;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.AnalyticsTracking;

public class WeatherStationMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private long cityId;
    private double lat;
    private double lon;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_station_maps);

        //send the tracking ofthe viewing of this screen.
        AnalyticsTracking.sendScreenViewEvents(WeatherStationMapsActivity.class.getSimpleName());

        //get intent and get city id from bundle obj.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cityId = bundle.getLong("city_id");
        lat = bundle.getDouble("lat");
        lon = bundle.getDouble("lon");
        cityName = bundle.getString("cn");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        BeanQueryParams qp = new BeanQueryParams();
        qp.setCityId(cityId);//city id is used for weather station data.

        qp.setQueryParamType(BeanQueryParams.T_Query_Param_Type.E_WEATHER_STATION_TABLE_LIST_TYPE);

        //get list of weather stations based on the query params.
        List<WeatherStationInfoTable> weather_station_list =
                WeatherDbProcessing.getBeanByQueryParamsList
                        (qp, getApplicationContext(), new WeatherStationInfoTable());

        //city location
        LatLng city_pos =
                new LatLng(lat, lon);

        //map the city id with lat/lon
        mMap.addMarker(new MarkerOptions()
                .position(city_pos)
                .title(cityName));

        int station_num = 1;
        for(WeatherStationInfoTable weather_station_obj : weather_station_list)
        {
            //create lat long obj with values from pojo.
            LatLng weather_station_lat_lon =
                    new LatLng(weather_station_obj.getStation_lat(), weather_station_obj.getStation_lon());

            mMap.addMarker(new MarkerOptions()
                    .position(weather_station_lat_lon)
                    .title("WeatherStation_"+station_num++));
        }

        // Move the camera instantly to city id with a zoom of 10.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city_pos, 7));

        // Zoom in, animating the camera.
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}
