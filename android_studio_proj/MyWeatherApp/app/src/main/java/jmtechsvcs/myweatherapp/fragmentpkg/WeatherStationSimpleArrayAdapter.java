package jmtechsvcs.myweatherapp.fragmentpkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import jmtechsvcs.myweatherapp.R;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.WeatherStationInfoTable;

/**
 * Created by jimmy on 8/22/2015.
 */
public class WeatherStationSimpleArrayAdapter extends ArrayAdapter<WeatherStationInfoTable>
{
    private static final String LOGTAG = "WeatherStationSimpleArrayAdapter";

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

        //TODO: add to the view the items from the data set for each item.

        //return the row view for this inflated item.
        return rowView;
    }
}
