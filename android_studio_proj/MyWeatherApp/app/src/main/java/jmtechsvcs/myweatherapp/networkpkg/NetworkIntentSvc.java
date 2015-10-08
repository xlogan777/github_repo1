package jmtechsvcs.myweatherapp.networkpkg;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Map;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityWeatherCurrCondTable;
import jmtechsvcs.myweatherapp.dbpkg.WeatherDbProcessing;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
public class NetworkIntentSvc extends IntentService
{
    private static final String LOGTAG = "NetworkIntentSvc";

    //action to be used to allow for processing in the handler method.
    private static final String CURRENT_WEATHER_ACTION = "CURRENT_WEATHER_ACTION";

    //action to be used for processing in the handler method.
    private static final String CURRENT_WEATHER_STATION_GEO_ACTION = "CURRENT_WEATHER_STATION_GEO_ACTION";

    //action to be used to allow processing in the handler method.
    private static final String WEATHER_DAILY_FORECAST_ACTION = "WEATHER_DAILY_FORECAST_ACTION";

    //action to be used to allow for hourly processing.
    private static final String CURRENT_HOURLY_FORECAST_ACTION = "CURRENT_HOURLY_FORECAST_ACTION";

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
    public static void startActionCurrentWeather(Context context, CityInfoTable data)
    {
        //create intent with this activity as the sending activity, and the calling service.
        Intent mServiceIntent = new Intent(context, NetworkIntentSvc.class);

        //set the action to this intent.
        mServiceIntent.setAction(CURRENT_WEATHER_ACTION);

        //create bundle for service call.
        bundleAndServiceCall(data, context, mServiceIntent);
    }

    /**
     * start this service with perform action CURRENT_WEATHER_STATION_GEO_ACTION with the given params.
     * If the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionCurrentWeatherStationGeo(Context context, CityInfoTable data)
    {
        //create intent with this activity as the sending activity, and the calling service.
        Intent mServiceIntent = new Intent(context, NetworkIntentSvc.class);

        //set the action to this intent.
        mServiceIntent.setAction(CURRENT_WEATHER_STATION_GEO_ACTION);

        //create bundle for service call.
        bundleAndServiceCall(data, context, mServiceIntent);
    }

    public static void startActionDailyCityWeatherForecast(Context context, CityInfoTable data)
    {
        //create intent with this activity as the sending activity, and the calling service.
        Intent mServiceIntent = new Intent(context, NetworkIntentSvc.class);

        //set the action to this intent.
        mServiceIntent.setAction(WEATHER_DAILY_FORECAST_ACTION);

        //create bundle for service call.
        bundleAndServiceCall(data, context, mServiceIntent);
    }

    public static void startActionCurrentHourlyForecast(Context context, CityInfoTable data)
    {
        //create intent with this activity as the sending activity, and the calling service.
        Intent mServiceIntent = new Intent(context, NetworkIntentSvc.class);

        //set the action to this intent.
        mServiceIntent.setAction(CURRENT_HOURLY_FORECAST_ACTION);

        //create bundle for service call.
        bundleAndServiceCall(data, context, mServiceIntent);
    }

    private static void bundleAndServiceCall(CityInfoTable data, Context context, Intent intent)
    {
        //create bundle to save data in it.
        Bundle bundle = new Bundle();

        //save data to the bundle.
        bundle.putLong("cityId", data.getCity_id());
        bundle.putString("cn", data.getName());
        bundle.putString("cc", data.getCountry());
        bundle.putDouble("lat", data.getLat());
        bundle.putDouble("lon",data.getLon());

        //save bundle to this intent.
        intent.putExtras(bundle);

        //start the service
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if(intent != null)
        {
            //get the action from the intent.
            String action = intent.getAction();

            //get the bundle from the intent
            Bundle bundle = intent.getExtras();

            //handle the current weather action based intent.
            if(CURRENT_WEATHER_ACTION.equals(action))
            {
                //call handler function for this action.
                handleCurrentWeatherAction(bundle);
            }
            //handle the weather station by lat/lon processing.
            else if(CURRENT_WEATHER_STATION_GEO_ACTION.equals(action))
            {
                //call handler for this action
                handleCurrentWeatherStationGeoAction(bundle);
            }
            else if(WEATHER_DAILY_FORECAST_ACTION.equals(action))
            {
                //call handler for this action
                handleDailyCityForecastAction(bundle);
            }
            else if(CURRENT_HOURLY_FORECAST_ACTION.equals(action))
            {
                //call handler for this action
                handleCurrentHourlyForecastAction(bundle);
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
    private void handleCurrentWeatherAction(Bundle bundle)
    {
        long cityId = bundle.getLong("cityId");

        Log.d(LOGTAG,"city id = "+cityId);

        //create the weather url with city id
        String curr_weather_url = WeatherMapUrls.getCurrentWeatherByCityId("" + cityId);

        //get more weather data with xml based url.
        String curr_weather_url_xml = WeatherMapUrls.getCurrentWeatherByCityIdXml("" + cityId);

        //get the payload from the http get for the current weather json data
        DataPayload payload = NetworkProcessing.httpGetProcessing(curr_weather_url, DataPayload.T_Payload_Type.E_JSON_PAYLOAD_TYPE);

        //get the payload from the http get for this current weather as a stream.
        DataPayload stream_payload =
                NetworkProcessing.httpGetProcessing(
                        curr_weather_url_xml, DataPayload.T_Payload_Type.E_BYTE_STREAM_PAYLOAD_TYPE
                );

        //print json string if not null and
        if(payload.getStringPayload() != null && stream_payload.getInputStreamPayload() != null)
        {
            Log.d(LOGTAG, payload.getStringPayload());

            //save to the db using this json input.
            CityWeatherCurrCondTable curr_cond =
                    WeatherDbProcessing.updateCurrWeatherToDb
                            (payload.getStringPayload(), stream_payload.getInputStreamPayload(), getApplicationContext());

            //confirm that we have a valid bean and its icon data is not null and > 0
            if(curr_cond != null &&
               curr_cond.getCurr_weather_icon() != null &&
               curr_cond.getCurr_weather_icon().length() > 0)
            {
                WeatherAppUtils.getAndSaveIconData(curr_cond.getCurr_weather_icon(), getApplicationContext());
            }
            else
            {
                Log.d(LOGTAG,"issue with the weather icon from the java bean returned back from the update current weather dao.");
            }
        }

        //send intents via android system.
        sendIntents(WeatherAppUtils.START_CURRENT_WEATHER_ACTIVITY_ACTION, bundle);
    }

    private void handleCurrentWeatherStationGeoAction(Bundle bundle)
    {
        double lat = bundle.getDouble("lat");
        double lon = bundle.getDouble("lon");
        long cityId = bundle.getLong("cityId");

        Log.d(LOGTAG,"lat = "+lat+", lon = "+lon);

        //create the weather station url with geo location.
        String curr_weather_station_geo_url = WeatherMapUrls.getWeatherStationInfoByGeo("" + lat, "" + lon, "4");//get 4 station entries.

        //get the payload from the http get for the current weather station json data
        DataPayload payload = NetworkProcessing.httpGetProcessing(curr_weather_station_geo_url, DataPayload.T_Payload_Type.E_JSON_PAYLOAD_TYPE);

        //check if payload is not null.
        if(payload.getStringPayload() != null)
        {
            Log.d(LOGTAG, payload.getStringPayload());

            //update the dao using the json string and providing the app context.
            WeatherDbProcessing.updateCurrentWeatherStationInfoGeo(payload.getStringPayload(), getApplicationContext(), cityId);
        }

        //send intents via android system.
        sendIntents(WeatherAppUtils.START_WEATHER_STATION_ACTIVITY_ACTION, bundle);
    }

    private void handleDailyCityForecastAction(Bundle bundle)
    {
        long cityId = bundle.getLong("cityId");

        String daily_forecast_end_pt = WeatherMapUrls.getDailyWeatherForecastEndPt(cityId + "");

        //get the payload from the http get for this current weather as a stream.
        DataPayload stream_payload =
                NetworkProcessing.httpGetProcessing(
                        daily_forecast_end_pt, DataPayload.T_Payload_Type.E_BYTE_STREAM_PAYLOAD_TYPE
                );

        if(stream_payload.getInputStreamPayload() != null)
        {
            //update the weather data with the data stream.
            Map<String,String> icon_map =
                    WeatherDbProcessing.updateDailyCityWeatherForecast
                    (getApplicationContext(), stream_payload.getInputStreamPayload(), cityId);

            //loop over all the keys..and get and update the DB if needed.
            for(String data : icon_map.keySet())
            {
                WeatherAppUtils.getAndSaveIconData(data, getApplicationContext());
            }
        }

        //send intents via android system.
        sendIntents(WeatherAppUtils.START_DAILY_WEATHER_ACTIVITY_ACTION, bundle);
    }

    private void handleCurrentHourlyForecastAction(Bundle bundle)
    {
        long cityId = bundle.getLong("cityId");

        String hourly_forecast_end_pt = WeatherMapUrls.getHourlyWeatherForecastEndPt(cityId + "");

        //get the payload from the http get for this current weather as a stream.
        DataPayload stream_payload =
                NetworkProcessing.httpGetProcessing(
                        hourly_forecast_end_pt, DataPayload.T_Payload_Type.E_BYTE_STREAM_PAYLOAD_TYPE
                );

        if(stream_payload.getInputStreamPayload() != null)
        {
            //process these lists accordingly.
            Map<String,String> icon_map =
                    WeatherDbProcessing.updateyHourlyCityWeatherForecast
                            (getApplicationContext(), stream_payload.getInputStreamPayload(), cityId);

            //loop over all the keys..and get and update the DB if needed.
            for(String data : icon_map.keySet())
            {
                WeatherAppUtils.getAndSaveIconData(data, getApplicationContext());
            }
        }

        //send intents via android system.
        sendIntents(WeatherAppUtils.START_CURRENT_HOURLY_FORECAST_ACTIVITY_ACTION, bundle);
    }

    private void sendIntents(String activityAction, Bundle bundle)
    {
        //sleep for 5 secs
        WeatherAppUtils.localSleep(5);

        //stop the spinner via an intent with broadcast rcv
        Intent stop_spinner_intent = new Intent();
        stop_spinner_intent.setAction(WeatherAppUtils.STOP_SPINNER_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(stop_spinner_intent);

        //command to load a specific activity via an intent for the
        //broadcast rcv
        Intent command_intent = new Intent();
        command_intent.setAction(activityAction);

        //save bundle to this intent.
        command_intent.putExtras(bundle);

        //broadcast intent.
        LocalBroadcastManager.getInstance(this).sendBroadcast(command_intent);
    }
}
