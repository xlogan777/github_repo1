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
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;
import jmtechsvcs.myweatherapp.utilspkg.WeatherAppUtils;

/**
 * Created by jimmy on 8/20/2015.
 */
//this class is the custom array adapter for use in the city list fragment.
public class CityInfoSimpleArrayAdapter extends ArrayAdapter<CityInfoTable>
{
    private static final String LOGTAG = "CityInf[]Adapter";

    //these are used to setup the row view for
    //this adapter.
    private final Context context;
    private final List<CityInfoTable> values;

    public CityInfoSimpleArrayAdapter(Context context, List<CityInfoTable> values)
    {
        //call the super class to setup the xml layout with the
        //input values.
        super(context, R.layout.city_info_row_layout, values);

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
        View rowView = inflater.inflate(R.layout.city_info_row_layout, parent, false);

        //get the item row from the values list.
        CityInfoTable item_row = values.get(position);

        //get the city id from the item row for the text view.
        TextView textView = (TextView)rowView.findViewById(R.id.city_id_vals);
        textView.setText(item_row.getCity_id()+"");

        //get the city name and country code, and add it to the text view.
        textView = (TextView)rowView.findViewById(R.id.cn_cc_vals);
        textView.setText(item_row.getName()+", "+item_row.getCountry());

        //get the lat from item row and add it to the text view.
        textView = (TextView)rowView.findViewById(R.id.lat_vals);
        textView.setText(item_row.getLat()+"");

        //get the long from the item row and add it to the text view.
        textView = (TextView)rowView.findViewById(R.id.lon_vals);
        textView.setText(item_row.getLon()+"");

        //return the row view for this inflated item.
        return rowView;
    }
}
