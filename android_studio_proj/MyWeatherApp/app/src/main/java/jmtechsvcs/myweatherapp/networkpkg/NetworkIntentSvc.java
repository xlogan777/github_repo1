package jmtechsvcs.myweatherapp.networkpkg;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.util.ArrayList;
import java.util.List;

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

    public static void startActionCityWeatherForecast(Context context, CityInfoTable data)
    {
        //create intent with this activity as the sending activity, and the calling service.
        Intent mServiceIntent = new Intent(context, NetworkIntentSvc.class);

        //set the action to this intent.
        mServiceIntent.setAction(WEATHER_DAILY_FORECAST_ACTION);

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
            final String action = intent.getAction();

            //handle the current weather action based intent.
            if(CURRENT_WEATHER_ACTION.equals(action))
            {
                //get the bundle from the intent
                Bundle bundle = intent.getExtras();

                //call handler function for this action.
                handleCurrentWeatherAction(bundle);
            }
            //handle the weather station by lat/lon processing.
            else if(CURRENT_WEATHER_STATION_GEO_ACTION.equals(action))
            {
                Bundle bundle = intent.getExtras();

                //call handler for this action
                handleCurrentWeatherStationGeoAction(bundle);
            }
            else if(WEATHER_DAILY_FORECAST_ACTION.equals(action))
            {
                //get the bundle from the intent
                Bundle bundle = intent.getExtras();

                //call handler for this action
                handleDailyCityForecastAction(bundle);
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
                //create the weather icon url.
                String weather_icon_url = WeatherMapUrls.getWeatherIconByIconId(curr_cond.getCurr_weather_icon());

                //get the payload from the http get for the weather icon.
                payload = NetworkProcessing.httpGetProcessing(weather_icon_url, DataPayload.T_Payload_Type.E_BYTE_ARRAY_PAYLOAD_TYPE);

                //save the icon data into the DB.
                WeatherDbProcessing.updateWeatherIcon
                        (curr_cond.getCurr_weather_icon(), weather_icon_url, payload.getBytePayload(), getApplicationContext());
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

        try {
            // declaring object of "OpenWeatherMap" class
            //setup with api key and the units needed which is imperial.
            OpenWeatherMap owm = new
                    OpenWeatherMap(OpenWeatherMap.Units.IMPERIAL, WeatherMapUrls.API_KEY);

            //            //list of hourly forecast objs.
            //            List<HourlyForecast.Forecast> hourly_list = new ArrayList<HourlyForecast.Forecast>();
            //
            //            //get the hourly forecast by city id.
            //            HourlyForecast hourlyForecast_obj = owm.hourlyForecastByCityCode(cityId);
            //
            //            //checking data retrieval was successful or not
            //            if(hourlyForecast_obj.isValid())
            //            {
            //                //get the count from the hourly obj if it has one.
            //                int hourly_cnt =
            //                        hourlyForecast_obj.hasForecastCount() ?
            //                                hourlyForecast_obj.getForecastCount() : 0;
            //
            //                Log.d(LOGTAG,"hourly count = "+hourly_cnt);
            //
            //                for(int i = 0; i < hourly_cnt; i++)
            //                {
            //                    //get the hourly forecast obj from the main forecast obj.
            //                    HourlyForecast.Forecast  hr_forecast =
            //                            hourlyForecast_obj.getForecastInstance(i);
            //
            //                    //add this item to this hourly list.
            //                    //this list is for a city id.
            //                    hourly_list.add(hr_forecast);
            //                }
            //            }

            //list of daily objs.
            List<DailyForecast.Forecast> daily_list = new ArrayList<DailyForecast.Forecast>();

            //get the daily forecast by city id.
            byte cnt = 5;//only request 5 days.
            DailyForecast dailyForecast = owm.dailyForecastByCityCode(cityId, cnt);

            //check if daily forecast is valid.
            if (dailyForecast.isValid()) {
                //get the cnt if it exists.
                int daily_cnt = dailyForecast.hasForecastCount() ?
                        dailyForecast.getForecastCount() : 0;

                Log.d(LOGTAG, "daily count = " + daily_cnt);

                //pull all items for this city id for daily data.
                for (int i = 0; i < daily_cnt; i++) {
                    //get daily info
                    DailyForecast.Forecast day_forecast = dailyForecast.getForecastInstance(i);

                    //add item to this list to allow for processing based on city id.
                    daily_list.add(day_forecast);
                }
            }

            //process these lists accordingly.
            WeatherDbProcessing.updateDailyHourlyCityWeatherForecast
                    (getApplicationContext(), daily_list, cityId);
        }
        catch (Exception e)
        {
            Log.d(LOGTAG, WeatherAppUtils.getStackTrace(e));
        }

        //send intents via android system.
        sendIntents(WeatherAppUtils.START_DAILY_WEATHER_ACTIVITY_ACTION, bundle);
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
