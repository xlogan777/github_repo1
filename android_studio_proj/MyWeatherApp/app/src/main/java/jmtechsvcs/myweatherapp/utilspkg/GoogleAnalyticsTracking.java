package jmtechsvcs.myweatherapp.utilspkg;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import jmtechsvcs.myweatherapp.MyWeatherApplication;
import jmtechsvcs.myweatherapp.R;

//google analytics
//https://developers.google.com/analytics/devguides/collection/android/v4/

 /*
    All activity recorded on the shared tracker sends the most recent screen
    name until replaced or cleared (set to null).

    info on using google analytics.
    https://developers.google.com/analytics/devguides/collection/android/v4/
    https://developers.google.com/android/reference/com/google/android/gms/analytics/GoogleAnalytics
  */


/**
 * Created by jimmy on 10/17/2015.
 */
public class GoogleAnalyticsTracking
{
    private static final String LOGTAG = "GoogleAnalyticsTracking";

    //google analytics tracker. keep 1 tracker for now for all types of events.
    private static Tracker mTracker;

    //init the tracker here.
    public static synchronized void intializeTracker(Context context)
    {
        //create default tracker if it hasnt been created.
        if(mTracker == null)
        {
            //get google analytics obj using context.
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);

            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);

            Log.d(LOGTAG,"initialized the analytics tracker settings...");
            Log.d(LOGTAG,mTracker.toString());
        }
    }

    //enhancement is to have different trackers for each screen.
    public static synchronized void sendScreenViewEvents(String screenName)
    {
        if(mTracker != null) {
            //set the name of the tracker here for this activity.
            mTracker.setScreenName(screenName);

            //set the analytics hit here. for screens.
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            //clear the screen name.
            mTracker.setScreenName(null);

            Log.d(LOGTAG, "sent screen view for analytics = " + screenName);
        }
    }

    public static synchronized void sendAnalyticsEvent(String categoryId, String actionId, String labelId)
    {
        if(mTracker != null) {

            // Build and send an Event from user params.
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(categoryId)
                    .setAction(actionId)
                    .setLabel(labelId)
                    .build());

            Log.d(LOGTAG, "analytics event category_id = " + categoryId +
                    ", action_id = " + actionId + ", label_id = " + labelId);
        }
    }
}
