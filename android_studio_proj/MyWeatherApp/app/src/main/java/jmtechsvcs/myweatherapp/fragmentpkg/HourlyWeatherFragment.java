package jmtechsvcs.myweatherapp.fragmentpkg;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.HourlyWeatherInfoTable;

public class HourlyWeatherFragment extends ListFragment
{
    private static String LOGTAG = "HourlyWeatherFragment";
    private List<HourlyWeatherInfoTable> hourlyWeatherList;

    public HourlyWeatherFragment() {
        // Required empty public constructor
    }

    public void setHourlyWeatherList(List<HourlyWeatherInfoTable> hourlyWeatherList)
    {
        this.hourlyWeatherList = hourlyWeatherList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(LOGTAG, "size of hourly weather  = " + hourlyWeatherList.size());

        //create the weather adapter for hourly weather
        HourlyWeatherAdapter hourlyWeatherAdapter =
                new HourlyWeatherAdapter(getActivity(), hourlyWeatherList);

        //set the custom adapter to the list adapter here for this frag list.
        setListAdapter(hourlyWeatherAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        //call super class.
        super.onActivityCreated(savedInstanceState);

        //allow for scrolling inside the list fragment container.
        this.getListView().setScrollContainer(true);
    }
}
