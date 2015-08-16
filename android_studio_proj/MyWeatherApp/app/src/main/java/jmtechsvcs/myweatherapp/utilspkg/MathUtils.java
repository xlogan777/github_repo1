package jmtechsvcs.myweatherapp.utilspkg;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Menaj on 8/14/2015.
 */
//this class handles all math related processing.
public class MathUtils
{
    private static final String LOGTAG = "MathUtils";

    public static String getTempString(double data)
    {
        return data+" F";
    }

    public static String getVelocityString(double speed)
    {
        return speed+" mph";
    }

    public static String getDistanceString(long distance, char unit)
    {
        String rv = distance+" in";

        switch(unit)
        {
            //miles from meters.
            case 'M':
                rv = distance+" miles";
                break;

            //mm to inches.
            case 'I':
                rv = distance+" in";
                break;

            default:

                Log.d(LOGTAG,"default to inches");
                break;
        }

        return rv;
    }

    public static String getPressureString(long pressure)
    {
        return pressure+" hPa";
    }

    public static String getPercentString(long percentage)
    {
        return percentage+"%";
    }

    public static String getPercentWhole(double percent)
    {
        long val = (long)(percent * 100.0);
        String rv = getPercentString(val);
        return rv;
    }

    public static String getDegreeString(double degree)
    {
        return degree+" degs";
    }
}
