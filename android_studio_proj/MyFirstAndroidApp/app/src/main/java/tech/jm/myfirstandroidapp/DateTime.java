package tech.jm.myfirstandroidapp;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DateTime extends Fragment {

    private String time;
    private static String TAG_DATE_TIME = "TAG_DATE_TIME";

    //static factory to return a DateTime obj fragment and initialize it
    //this will act as a constructor, given that fragment classes should not contain
    //any constructor based on Android Developer Documentation recommends
    // that fragment subclasses not have explicit constructors
    public static DateTime newInstance(Date time)
    {
        //create a bundle obj that will contain the initialization data for this
        //fragment class by adding the data passed in to this static factory to the bundle
        //obj created here.
        Bundle init = new Bundle();

        //save the date time data as a string in the bundle to get back later,
        init.putString(DateTime.TAG_DATE_TIME, getDateTimeString(time));

        //create the fragment obj here.
        DateTime frag = new DateTime();

        //give it the bundle obj which acts as the constructor to the fragment.
        frag.setArguments(init);

        //return the fragment from this static factory.
        return frag;
    }

    //private static function to create the data needed for the fragment.
    private static String getDateTimeString(Date time)
    {
        return new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(time);
    }

    /*
        here is where u can get the bundle that has ur saved state information
        by using the saved data from the onSaveInstanceState.
     */
    public void onCreate(Bundle state) {
        super.onCreate(state);

        //check to see if state is null, which is the bundle, if it is, then get it from
        //the get arguments method of this fragment.
        if (null == state)
        {
            //this is initializing the state bundle from the previously provided data
            //from the setArguments method when the static factory was invoked.
            state = getArguments();

            Log.d("DateTime","attempt to get the bundle from the getArguments method");
        }

        //check to see if we saved something in the bundle and the actual bundle is
        //not null.
        if (null != state)
        {
            //get the time from the bundle now with same key value when it was saved.
            time = state.getString(TAG_DATE_TIME);
            Log.d("DateTime","restore the time from before");
        }

        if (null == time)
        {
            time = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(new Date());
            Log.d("DateTime","create new time since we dont have anything to restore time = "+time);
        }
        else
        {
            Log.d("DateTime", "time restored = "+time);
        }

        //time = new SimpleDateFormat("d MMM yyyy HH:mm:ss").format(new Date());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle b)
    {

        /*
        On the other hand, the fragment framework owns and manages the view that is returned
        by the onCreateView method. The code in onCreateView must not attach the view hierarchy
        to its container, as it normally would during inflation. That third argument is the
        flag that tells the inflater that the fragment framework is in control and that it must not
        attach the view hierarchy to the container.
         */
        View view = inflater.inflate(
                R.layout.date_time,
                container,
                false); //!!! this is important, see the block comment for explanations of this bool flag.

        //get the item for this fragment layout and update the text with the time created in the
        //onCreate method.
        ((TextView) view.findViewById(R.id.text_frag1)).setText(time);

        return view;
    }

    /*
     * preserve the state of the time variable in this fragment.
     * this is called each time the fragment is destroyed by the android OS calling
     * destroy method. when reclaiming memory, not by u killing the app.
     */
    @Override
    public void onSaveInstanceState(Bundle state)
    {
        //save the bundle first by saving it to the parent.
        super.onSaveInstanceState(state);

        Log.d("DateTime","save the state of the fragment here...with onsaveInstance lifecycle method");

        //save ur stuff in the bundle below.
        //save the current time value with a string value.
        //the bundle is like a hashmap..to save state.
        state.putString(TAG_DATE_TIME, time);
    }
}
