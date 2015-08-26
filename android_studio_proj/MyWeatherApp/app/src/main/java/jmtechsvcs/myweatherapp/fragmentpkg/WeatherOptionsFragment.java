package jmtechsvcs.myweatherapp.fragmentpkg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import jmtechsvcs.myweatherapp.activitypkg.CurrentWeatherActivity;
import jmtechsvcs.myweatherapp.activitypkg.WeatherStationDisplayActivity;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.networkpkg.NetworkIntentSvc;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

public class WeatherOptionsFragment extends DialogFragment{

    private static final String LOGTAG = "WeatherOptionsFragment";
    private CityInfoTable data;

    public WeatherOptionsFragment()
    {

    }

    public void setData(CityInfoTable data)
    {
        this.data = data;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //get the dialog list
        final String [] weather_opts = WeatherAppUtils.getDialogList();

        builder.setTitle("WeatherChoices")
                .setItems(weather_opts, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // The 'which' argument contains the index position
                        // of the selected item

                        Log.d(LOGTAG,"picked this choice = "+weather_opts[which]);

                        //get the city id from the data item.
                        long city_id = data.getCity_id();

                        //get the city name
                        String cn = data.getName();//city name

                        //get the country code
                        String cc = data.getCountry();//country code

                        //get the lat
                        double lat = data.getLat();

                        //get the long
                        double lon = data.getLon();

                        //this handles which item was selected.
                        switch(which)
                        {
                            //current weather option.
                            case 0:

                                //start the bg service, with helper method to load params to bg processing.
                                NetworkIntentSvc.startActionCurrentWeather(getActivity(), city_id);

                                //add this to the android back stack for here to the next activity
                                //being activated.
                                Intent curr_weather_intent = new Intent(getActivity(), CurrentWeatherActivity.class);
                                Bundle curr_bundle = new Bundle();
                                curr_bundle.putLong("city_id",city_id);

                                //add the bundle to the intent.
                                curr_weather_intent.putExtras(curr_bundle);

                                //start the activity with info on the city id to be used there.
                                startActivity(curr_weather_intent);

                                break;

                            case 1:

                                //start ng svc
                                NetworkIntentSvc.startActionCityWeatherForecast(getActivity(), city_id);

                                //TODO: add the call to the activity here for weather forecast.
                                break;


                            //weather station information processing.
                            case 2:

                                //start the bg service, with helper method to load params to bg processing.
                                NetworkIntentSvc.startActionCurrentWeatherStationGeo
                                        (getActivity(), lat, lon, city_id);

                                //add this to the android back stack for here to the next activity
                                //being activated.
                                Intent station_intent = new Intent(getActivity(), WeatherStationDisplayActivity.class);
                                Bundle station_bundle = new Bundle();
                                station_bundle.putLong("city_id",city_id);

                                //add the bundle to the intent.
                                station_intent.putExtras(station_bundle);

                                //start the activity with weather station info.
                                startActivity(station_intent);

                                break;
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
