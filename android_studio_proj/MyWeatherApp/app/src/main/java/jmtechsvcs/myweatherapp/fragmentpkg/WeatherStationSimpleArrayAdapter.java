package jmtechsvcs.myweatherapp.fragmentpkg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.MathUtils;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/**
 * Created by jimmy on 8/22/2015.
 */
public class WeatherStationSimpleArrayAdapter extends ArrayAdapter<WeatherStationInfoTable>
{
    private static final String LOGTAG = "WthrStaSimpArrayAdapter";

    //these are used to setup the row view for
    //this adapter.
    private final Context context;
    private final List<WeatherStationInfoTable> values;

    public WeatherStationSimpleArrayAdapter(Context context, List<WeatherStationInfoTable> values)
    {
        //call the super class to setup the xml layout with the
        //input values.
        super( context, R.layout.weather_station_info_row_layout, values);

        //save the ctx and data from the const params.
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //this will use the inflater service to be used to inflate the
        //row layout.
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);

        //inflate the row view here for the xml layout file and attach to the parent
        //view group.
        View rowView = inflater.inflate(R.layout.weather_station_info_row_layout, parent, false);

        Log.d(LOGTAG,"size of list = "+values.size()+", item provided = "+position);

        //get the row item from the list container to use to set the view accordingly.
        WeatherStationInfoTable item_row = values.get(position);

        //add the items to the view here for a row item.
        //station name, id and time are needed, if they dont work we have an issue.
        TextView textView = (TextView)rowView.findViewById(R.id.station_name_val);
        textView.setText(item_row.getStation_name());

        textView = (TextView)rowView.findViewById(R.id.station_id_val);
        textView.setText(item_row.getStation_id()+"");

        textView = (TextView)rowView.findViewById(R.id.station_time_val);
        textView.setText(WeatherAppUtils.getUtcFromUtcSeconds(item_row.getLast_update_time(),WeatherAppUtils.UTC_DATE_FORMAT_hms)+"");

        //any else is optional to display..so treat it that way.
        String result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getStation_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.temp_val)).setText(
                    MathUtils.getTempString(
                            MathUtils.convertKelvinToFarenheit(item_row.getStation_temp())
                    ) + ""
            );
        else
            ((TextView)rowView.findViewById(R.id.temp_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getStation_pressure());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.pressure_val)).setText(
                    MathUtils.getPressureString(item_row.getStation_pressure()) + ""
            );
        else
            ((TextView)rowView.findViewById(R.id.pressure_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getStation_humidity());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.humidity_val)).
                    setText(MathUtils.getPercentString(item_row.getStation_humidity()) + "");
        else
            ((TextView)rowView.findViewById(R.id.humidity_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getStation_wind_speed());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.wind_speed_val)).setText(
                    MathUtils.getVelocityString(
                            MathUtils.convertMpsToMph(item_row.getStation_wind_speed())
                    ) + ""
            );
        else
            ((TextView)rowView.findViewById(R.id.wind_speed_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getStation_wind_deg());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.wind_deg_val)).setText
                    (MathUtils.getDegreeString(item_row.getStation_wind_deg())+"");
        else
            ((TextView)rowView.findViewById(R.id.wind_deg_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getStation_wind_gust());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.wind_gust_val)).setText(
                    MathUtils.getVelocityString(
                            MathUtils.convertMpsToMph(item_row.getStation_wind_gust())
                    ) + ""
            );
        else
            ((TextView)rowView.findViewById(R.id.wind_gust_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getStation_visibility_dist());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.visibility_val)).
                    setText(
                            MathUtils.getDistanceString(
                                    MathUtils.convertMetersToMiles(item_row.getStation_visibility_dist()), 'M') + "");
        else
            ((TextView)rowView.findViewById(R.id.visibility_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getStation_calc_dewpt());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.dewpoint_val)).setText(
                    MathUtils.getTempString(
                            MathUtils.convertKelvinToFarenheit(item_row.getStation_calc_dewpt())
                    ) + ""
            );
        else
            ((TextView)rowView.findViewById(R.id.dewpoint_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getStation_calc_humidex());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.humidex_val)).setText(
                    MathUtils.getTempString(
                            MathUtils.convertKelvinToFarenheit(item_row.getStation_calc_humidex())
                    ) + ""
            );
        else
            ((TextView)rowView.findViewById(R.id.humidex_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayString(item_row.getStation_clouds_cond());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.cloud_cond_val)).setText(item_row.getStation_clouds_cond()+"");
        else
            ((TextView)rowView.findViewById(R.id.cloud_cond_val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getStation_rain_today());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.rain_today_val)).setText(
                    MathUtils.getDistanceString(MathUtils.convertMmToInches(item_row.getStation_rain_today()), 'I') + "");
        else
            ((TextView)rowView.findViewById(R.id.rain_today_val)).setText(result);

        //return the row view for this inflated item.
        return rowView;
    }
}
