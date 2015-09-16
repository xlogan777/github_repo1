package jmtechsvcs.myweatherapp.fragmentpkg;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import jmtechsvcs.myweatherapp.greendaosrcgenpkg.DailyWeatherInfoTable;

public class DailyWeatherFragment extends ListFragment
{
    private static String LOGTAG = "DailyWeatherFragment";
    private List<DailyWeatherInfoTable> dailyWeatherList;

    public DailyWeatherFragment() {
        // Required empty public constructor
    }

    public void setDailyWeatherList(List<DailyWeatherInfoTable> dailyWeatherList)
    {
        this.dailyWeatherList = dailyWeatherList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(LOGTAG, "size of daily weather  = " + dailyWeatherList.size());

        //create the weather adapter for daily weather
        DailyWeatherAdapter dailyWeatherAdapter =
                new DailyWeatherAdapter(getActivity(), dailyWeatherList);

        //set the custom adapter to the list adapter here for this frag list.
        setListAdapter(dailyWeatherAdapter);
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
