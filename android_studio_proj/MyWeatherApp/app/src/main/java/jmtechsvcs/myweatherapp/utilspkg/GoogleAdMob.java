package jmtechsvcs.myweatherapp.utilspkg;

import android.util.Log;
import com.google.android.gms.ads.AdRequest;

//https://developers.google.com/admob/android/quick-start

//create new admob account for a new app to monetize
//https://support.google.com/admob/answer/2773509

//this is to add a new ad unit id, for an already existing
//app in the admob acc.
//https://support.google.com/admob/answer/3052638

/**
 * Created by jimmy on 10/18/2015.
 */
public class GoogleAdMob
{
    private static String LOGTAG = "GoogleAdMob";

    //city search add request
    private static AdRequest citySearchAdRequest;

    //get add request and store it.
    public static synchronized void initCitySearchAdRequest()
    {
        if(citySearchAdRequest == null)
        {
            //this is used for testing but use it with the ad unit id in the res/strings.xml
            //with the commented out section.
//            citySearchAdRequest = new AdRequest.Builder()
//                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
//                    .addTestDevice("CA21EF673EA1B4FDE2DBBC38FFA4DFE")  // LG G2 phone md5 hash id.
//                    .build();

            //request add from google ad mob
            citySearchAdRequest = new AdRequest.Builder().build();
            Log.d(LOGTAG,"loaded ad request now.");
        }
    }

    //return current ad request for city search.
    public static AdRequest getCitySearchAdRequest()
    {
        return citySearchAdRequest;
    }
}
