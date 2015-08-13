package jmtechsvcs.myweatherapp.fragmentpkg;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import jmtechsvcs.myweatherapp.activitypkg.CitySearchActivity;
import jmtechsvcs.myweatherapp.greendaosrcgen.CityInfoTable;

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

        if(listParam != null)
        {
            List<String> display_list = new ArrayList<String>();

            for(CityInfoTable my_items : listParam)
            {
                String cn = my_items.getName();
                String cc = my_items.getCountry();

                display_list.add("CityName = "+cn+", CC = "+cc);
            }

            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_2, android.R.id.text1, display_list));
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
    public void onListItemClick (ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);

        Log.d(LOGTAG,"pos = "+position+", id = "+id);

        //using the item clicked from the list
        //provide a popup to the user to select if they want to see
        //current weather , 5 day forecast..etc...options here.

        if(mListener != null)
        {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(position+"");//position of item clicked.
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(String id);
    }
}
