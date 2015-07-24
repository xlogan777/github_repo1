package jmtechsvcs.myweatherapp.networklayer;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;

import jmtechsvcs.myweatherapp.GreenDaoSrcGen.DaoSession;
import jmtechsvcs.myweatherapp.MyWeatherApplication;
import jmtechsvcs.myweatherapp.utils.WeatherJsonToDbProcessing;
import jmtechsvcs.myweatherapp.utils.WeatherMapUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NetworkIntentSvc extends IntentService
{
    private static final String LOGTAG = "NetworkIntentSvc";

    //action to be used to allow for processing in the handler method.
    public static final String CURRENT_WEATHER_ACTION = "CURRENT_WEATHER_ACTION";

//    // TODO: Rename actions, choose action names that describe tasks that this
//    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
//    private static final String ACTION_FOO = "jmtechsvcs.myweatherapp.networklayer.action.FOO";
//    private static final String ACTION_BAZ = "jmtechsvcs.myweatherapp.networklayer.action.BAZ";
//
//    // TODO: Rename parameters
//    private static final String EXTRA_PARAM1 = "jmtechsvcs.myweatherapp.networklayer.extra.PARAM1";
//    private static final String EXTRA_PARAM2 = "jmtechsvcs.myweatherapp.networklayer.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
//    // TODO: Customize helper method
//    public static void startActionFoo(Context context, String param1, String param2){
//        Intent intent = new Intent(context, NetworkIntentSvc.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2){
//        Intent intent = new Intent(context, NetworkIntentSvc.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    public NetworkIntentSvc()
    {
        super("NetworkIntentSvc");
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

//            if(ACTION_FOO.equals(action))
//            {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            }
//            else if(ACTION_BAZ.equals(action))
//            {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
        }
    }

    private void handleCurrentWeatherAction(long cityId)
    {
        Log.d(LOGTAG,"city id = "+cityId);

        //create the weather url with city id
        String curr_weather_url = WeatherMapUrls.getCurrentWeatherByCityId(""+cityId);

        //get the input stream from the http get.
        String json_data = NetworkProcessing.httpGetProcessing(curr_weather_url);

        Log.d(LOGTAG,json_data);

        //get the application ctx for this app.
        MyWeatherApplication weather_app = (MyWeatherApplication)getApplicationContext();

        //get the dao session stored in the context.
        DaoSession dao_session = weather_app.getDaoSession();

        //save to the db using this json input.
        WeatherJsonToDbProcessing.updateCurrWeatherToDb(json_data, dao_session);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
//    private void handleActionFoo(String param1, String param2)
//    {
//        // TODO: Handle action Foo
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    /**
//     * Handle action Baz in the provided background thread with the provided
//     * parameters.
//     */
//    private void handleActionBaz(String param1, String param2)
//    {
//        // TODO: Handle action Baz
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
