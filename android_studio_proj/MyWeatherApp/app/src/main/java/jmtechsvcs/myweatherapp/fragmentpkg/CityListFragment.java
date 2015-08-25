package jmtechsvcs.myweatherapp.fragmentpkg;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jmtechsvcs.myweatherapp.activitypkg.CitySearchActivity;
import jmtechsvcs.myweatherapp.greendaosrcgenpkg.CityInfoTable;

//https://www.airpair.com/android/list-fragment-android-studio
/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CityListFragment extends ListFragment
{
    private static String LOGTAG = "CityListFragment";

    //this allows to provide the activity who this fragment belongs to
    //to notify of an event occurring.
    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityListFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //get the activity and cast to the parent activity.
        CitySearchActivity act = (CitySearchActivity)getActivity();

        //get the city list from the act obj.
        List<CityInfoTable> listParam = act.getCityList();

        //check for null list and items in the list to do adapter code.
        if(listParam != null && listParam.size() > 0)
        {
            //add the city info list to this adapter.
            //create the simple array adapter
            CityInfoSimpleArrayAdapter simpleArrayAdapter =
                    new CityInfoSimpleArrayAdapter(getActivity(), listParam);

            //set the custom adapter to the list adapter here for this frag list.
            setListAdapter(simpleArrayAdapter);
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        //call super class.
        super.onActivityCreated(savedInstanceState);

        //allow for scrolling inside the list fragment container.
        this.getListView().setScrollContainer(true);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mListener = (OnFragmentInteractionListener)activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Log.d(LOGTAG,"pos = "+position+", id = "+id);

        //get the data from the selected item and cast to expected obj.
        CityInfoTable selectedFromList = (CityInfoTable)getListView().getItemAtPosition(position);

        if(mListener != null)
        {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(selectedFromList);//data of item clicked
        }
    }

   //iface to provide data back to activity of the selected item.
    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(CityInfoTable data);
    }
}
