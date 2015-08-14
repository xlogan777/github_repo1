package jmtechsvcs.myweatherapp.networkpkg;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
public class NetworkIntentSvc extends IntentService
{
    private static final String LOGTAG = "NetworkIntentSvc";

    //action to be used to allow for processing in the handler method.
    public static final String CURRENT_WEATHER_ACTION = "CURRENT_WEATHER_ACTION";

    public NetworkIntentSvc()
    {
        super("NetworkIntentSvc");
    }

    /**
     * Starts this service to perform action CURRENT_WEATHER_ACTION with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionCurrentWeather(Context context, long cityId)
    {
        //create intent with this activity as the sending activity, and the calling service.
        Intent mServiceIntent = new Intent(context, NetworkIntentSvc.class);

        //set the action to this intent.
        mServiceIntent.setAction(CURRENT_WEATHER_ACTION);

        //create bundle to save data in it.
        Bundle bundle = new Bundle();
        bundle.putLong("cityId", cityId);

        //save bundle to this intent.
        mServiceIntent.putExtras(bundle);

        //start the service
        context.startService(mServiceIntent);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null)
        {
            final String action = intent.getAction();

            //handle the current weather action based intent.
            if(CURRENT_WEATHER_ACTION.equals(action))
            {
                //get the bundle from the intent
                Bundle bundle = intent.getExtras();

                //get the data from the bundle.
                long cityId = bundle.getLong("cityId");

                //call handler fuction for this action.
                handleCurrentWeatherAction(cityId);
            }
            else
            {
                Log.d(LOGTAG,"got this action, not ready for = "+action);
            }
        }
    }

    /*
        internal method to handler the processing.
     */
    private void handleCurrentWeatherAction(long cityId)
    {
        Log.d(LOGTAG,"city id = "+cityId);

        //create the weather url with city id
        String curr_weather_url = WeatherMapUrls.getCurrentWeatherByCityId("" + cityId);

        //get the payload from the http get for the current weather json data
        DataPayload payload = NetworkProcessing.httpGetProcessing(curr_weather_url, DataPayload.T_Payload_Type.E_JSON_PAYLOAD_TYPE);

        //print json string if not null.
        if(payload.getStringPayload() != null)
            Log.d(LOGTAG,payload.getStringPayload());

        //save to the db using this json input.
        CityWeatherCurrCondTable curr_cond =
                WeatherDbProcessing.updateCurrWeatherToDb(payload.getStringPayload(), getApplicationContext());

        //confirm that we have a valid bean and its icon data is not null and > 0
        if(curr_cond != null &&
           curr_cond.getCurr_weather_icon() != null &&
           curr_cond.getCurr_weather_icon().length() > 0)
        {
            //create the weather icon url.
            String weather_icon_url = WeatherMapUrls.getWeatherIconByIconId(curr_cond.getCurr_weather_icon());

            //get the payload from the http get for the weather icon.
            payload = NetworkProcessing.httpGetProcessing(weather_icon_url, DataPayload.T_Payload_Type.E_BYTE_ARRAY_PAYLOAD_TYPE);

            //save the icon data into the DB.
            WeatherDbProcessing.updateWeatherIcon
                    (curr_cond.getCurr_weather_icon(), weather_icon_url, payload.getBytePayload(), getApplicationContext());
        }
    }
}
