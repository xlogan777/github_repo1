package jmtechsvcs.myweatherapp.fragmentpkg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import jmtechsvcs.myweatherapp.activitypkg.CurrentWeatherActivity;
import jmtechsvcs.myweatherapp.networkpkg.NetworkIntentSvc;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

public class WeatherOptionsFragment extends DialogFragment{

    private static final String LOGTAG = "WeatherOptionsFragment";
    private String data;

    public WeatherOptionsFragment()
    {

    }

    public void setData(String data)
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

                        //split the data string by commas
                        String [] items = data.split(",");

                        //get the cid from the data string to use as the city id.
                        String cid = items[0];
                        long city_id = Long.parseLong(WeatherAppUtils.getDataFromInput(cid));

                        String cn = items[1];//city name
                        cn = WeatherAppUtils.getDataFromInput(cn);

                        String cc = items[2];//country code
                        cc = WeatherAppUtils.getDataFromInput(cc);

                        String lat_s = items[3];//lat
                        double lat = Double.parseDouble(WeatherAppUtils.getDataFromInput(lat_s));

                        String lon_s = items[4];//lon
                        double lon = Double.parseDouble(WeatherAppUtils.getDataFromInput(lon_s));

                        //this handles which item was selected.
                        switch(which)
                        {
                            //current weather option.
                            case 0:

                                //start the bg service, with helper method to load params to bg processing.
                                NetworkIntentSvc.startActionCurrentWeather(getActivity(), city_id);

                                //add this to the android back stack for here to the next activity
                                //being activated.
                                Intent intent = new Intent(getActivity(), CurrentWeatherActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putLong("city_id",city_id);

                                //add the bundle to the intent.
                                intent.putExtras(bundle);

                                //start the activity with info on the city id to be used there.
                                startActivity(intent);

                                break;

                            case 1:

                                break;

                            case 2:

                                break;

                            //weather station information processing.
                            case 3:

                                //start the bg service, with helper method to load params to bg processing.
                                NetworkIntentSvc.startActionCurrentWeatherStationGeo(getActivity(),lat,lon,city_id);

                                //TODO: add the call to the weather station screens from here.

                                break;
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
