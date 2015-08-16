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

    public static String convertToRequestedTempUnit(double kelvin, char unit)
    {
        //load to default
        double rv = kelvin;
        String display_rv = rv+" K";

        switch(unit)
        {
            case 'F':

                rv =  (kelvin - 273.15) * 1.8000 + 32.00;
                display_rv = rv+" F";
                break;

            case 'C':

                rv =  (kelvin - 273.15);
                display_rv = rv+" C";
                break;

            default://this is kelvin

                Log.d(LOGTAG,"using default of kelvin");
                break;
        }

        return display_rv;
    }

    //default is m/s
    public static String convertToRequestedVelocityUnit(double speed, char unit)
    {
        String rv = speed+" m/s";

        switch(unit)
        {
            case 'M':

                rv = (speed * 2.236936)+" mph";
                break;

            default:

                Log.d(LOGTAG,"using m/s");
                break;
        }

        return rv;
    }

    public static String convertToRequestedDistanceUnit(long distance, char unit)
    {
        String rv = distance+" in";

        switch(unit)
        {
            //miles from meters.
            case 'M':
                rv = (distance * 0.00062137)+" miles";
                break;

            //mm to inches.
            case 'I':
                rv = (distance * 0.039370)+" in";
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
