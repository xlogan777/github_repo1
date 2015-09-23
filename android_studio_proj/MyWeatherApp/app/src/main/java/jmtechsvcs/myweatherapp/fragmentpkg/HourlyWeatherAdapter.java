package jmtechsvcs.myweatherapp.fragmentpkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.HourlyWeatherInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.MathUtils;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/**
 * Created by Menaj on 9/23/2015.
 */
public class HourlyWeatherAdapter extends ArrayAdapter<HourlyWeatherInfoTable>
{
    private static final String LOGTAG = "HourlyWeatherAdapter";

    //these are used to setup the row view for
    //this adapter.
    private final Context context;
    private final List<HourlyWeatherInfoTable> values;

    public HourlyWeatherAdapter(Context context, List<HourlyWeatherInfoTable> values)
    {
        //call the super class to setup the xml layout with the
        //input values.
        super(context, R.layout.hourly_weather_row_layout, values);

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
        View rowView = inflater.inflate(R.layout.hourly_weather_row_layout, parent, false);

        //get the item row from the values list.
        HourlyWeatherInfoTable item_row = values.get(position);

        //any else is optional to display..so treat it that way.
        String result = WeatherAppUtils.getDefaultStringDisplayLong(item_row.getHourly_weather_date());

        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyDate_Val)).setText(
                    WeatherAppUtils.getUtcFromUtcSeconds(item_row.getHourly_weather_date()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyDate_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_cloud_pert());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyCloudsPerct_Val)).setText(
                    MathUtils.getPercentString((long) item_row.getHourly_cloud_pert()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyCloudsPerct_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_humidity());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyHumidity_Val)).setText(
                    MathUtils.getPercentString((long) item_row.getHourly_humidity()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyHumidity_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_max_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyMaxTemp_Val)).setText(
                    MathUtils.getTempString(item_row.getHourly_max_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyMaxTemp_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_min_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyMinTemp_Val)).setText(
                    MathUtils.getTempString(item_row.getHourly_min_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyMinTemp_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_temp());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyTemp_Val)).setText(
                    MathUtils.getTempString(item_row.getHourly_temp()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyTemp_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_pressure());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyPressure_Val)).setText(
                    MathUtils.getPressureString((long) item_row.getHourly_pressure()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyPressure_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_sea_level());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlySeaLevel_Val)).setText
                    (MathUtils.getPressureString((long)item_row.getHourly_sea_level())+"");
        else
            ((TextView)rowView.findViewById(R.id.HourlySeaLevel_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(
                item_row.getHourly_gnd_level()
        );
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyGndLevel_Val)).setText(MathUtils.getPressureString(
                    (long)item_row.getHourly_gnd_level()
            )+"");
        else
            ((TextView)rowView.findViewById(R.id.HourlyGndLevel_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_wind_deg());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyWindDeg_Val)).setText(
                    MathUtils.getDegreeString(item_row.getHourly_wind_deg()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyWindDeg_Val)).setText(result);

        result = WeatherAppUtils.getDefaultStringDisplayDouble(item_row.getHourly_wind_speed());
        if(result.length() == 0)
            ((TextView)rowView.findViewById(R.id.HourlyWindSpeed_Val)).setText(
                    MathUtils.getVelocityString(item_row.getHourly_wind_speed()) + "");
        else
            ((TextView)rowView.findViewById(R.id.HourlyWindSpeed_Val)).setText(result);

        //return the row view for this inflated item.
        return rowView;
    }
}

