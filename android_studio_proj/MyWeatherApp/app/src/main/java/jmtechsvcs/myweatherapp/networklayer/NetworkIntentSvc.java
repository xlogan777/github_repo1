package jmtechsvcs.myweatherapp.networklayer;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import jmtechsvcs.myweatherapp.greendaosrcgen.DaoSession;
import jmtechsvcs.myweatherapp.MyWeatherApplication;
import jmtechsvcs.myweatherapp.utils.WeatherJsonToDbProcessing;

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

        //get the input stream from the http get.
        DataPayload payload = NetworkProcessing.httpGetProcessing(curr_weather_url, DataPayload.T_Payload_Type.E_JSON_PAYLOAD_TYPE);

        Log.d(LOGTAG,payload.getStringPayload());

        //get the application ctx for this app.
        MyWeatherApplication weather_app = (MyWeatherApplication)getApplicationContext();

        //get the dao session stored in the context.
        DaoSession dao_session = weather_app.getDaoSession();

        //save to the db using this json input.
        WeatherJsonToDbProcessing.updateCurrWeatherToDb(payload.getStringPayload(), dao_session);
    }
}
