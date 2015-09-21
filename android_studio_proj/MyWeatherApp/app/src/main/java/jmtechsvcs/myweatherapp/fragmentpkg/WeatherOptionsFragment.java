package jmtechsvcs.myweatherapp.fragmentpkg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import jmtechsvcs.myweatherapp.activitypkg.CitySearchActivity;
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

                        Log.d(LOGTAG, "picked this choice = " + weather_opts[which]);

                        //get the parent activity
                        CitySearchActivity citySearchActivity = (CitySearchActivity)getActivity();

                        //load the spinner here.
                        citySearchActivity.showLoadingDialog();

                        //this handles which item was selected.
                        switch(which)
                        {
                            case 0:
                                //current weather option.
                                NetworkIntentSvc.startActionCurrentWeather(getActivity(), data);
                                break;

                            case 1:

                                //daily forecast.
                                NetworkIntentSvc.startActionDailyCityWeatherForecast(getActivity(), data);
                                break;

                            case 2:

                                //hourly forecast
                                NetworkIntentSvc.startActionCurrentHourlyForecast(getActivity(), data);
                                break;

                            case 3:

                                //weather station info.
                                NetworkIntentSvc.startActionCurrentWeatherStationGeo(getActivity(), data);
                                break;
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
