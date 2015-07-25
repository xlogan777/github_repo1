package jmtechsvcs.myweatherapp;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * Created by jimmy on 7/25/2015.
 */
public class MainWeatherActivityTest extends ActivityInstrumentationTestCase2<MainWeatherActivity>
{
    private static final String LOGTAG = "MainWeatherActivityTest"
            ;
    //private members for this test.
    private MainWeatherActivity mActivity;
    private String resourceString;

    /*
        both of these constructors set the activity to using android specific
        features.
     */
//    public MainWeatherActivityTest()
//    {
//        super(MainWeatherActivity.class);
//
//    }

    public MainWeatherActivityTest(Class<MainWeatherActivity> activityClass)
    {
        super(activityClass);
    }

    //here we setup the class members for the UT
    @Override
    public void setUp() throws Exception
    {
        //setup the ut class.
        super.setUp();

        mActivity = getActivity();
        resourceString = mActivity.getString(R.string.hello_world);
    }

    //areas to test the valid activity is available. similar to junit.
    public void testPreconditions() throws Exception
    {
        //if this fails the we have an null activity.
        assertNotNull(mActivity);
        Log.d(LOGTAG, "the activity retrieved is not null");
    }

    //actual test method to be run..similar to junit.
    public void testText() throws Exception
    {
        assertEquals(resourceString,"Hello world!");
        Log.d(LOGTAG, "tested the hello world and it was ok..");
    }

    //here we close the obj with stuff that sets the test case back to start
    //and release any resources obtained during the test.
    @Override
    public void tearDown() throws Exception
    {
        //shutdown the ut class.
        super.tearDown();

        //reset all resources back to known state.
        mActivity = null;
        resourceString = null;
    }
}
