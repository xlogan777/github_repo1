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

    //standard conversion for units.
    public static double convertKelvinToFarenheit(double kelvin)
    {
        double rv = (kelvin - 273.15) * 1.8000 + 32.00;
        return rv;
    }

    public static double convertMpsToMph(double mps)
    {
        double rv = mps * 2.236936;
        return rv;
    }

    public static double convertMetersToMiles(long meters)
    {
        double rv = meters * 0.00062137;
        return rv;
    }

    public static double convertMmToInches(double mm)
    {
        double rv = mm * 0.039370;
        return rv;
    }
}
