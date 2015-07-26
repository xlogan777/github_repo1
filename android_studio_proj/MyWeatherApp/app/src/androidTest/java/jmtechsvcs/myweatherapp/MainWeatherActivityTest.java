package jmtechsvcs.myweatherapp;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * Created by jimmy on 7/25/2015.
 */
//NOTE: always start the android AVD to run this unit test..and pick the
//the green android robot to run ur test.
/*
    the steps for the instrumentation for the activity unit test is
    1. setup() --sets up this unit test for the activity.
    2. preconditions() --this is a "test method" to make sure u have all data before running test..
    3. testFoo()--test case 1.
    4. testFoo2()--test case 2
    5. testFoo3()..etc --as many test cases as needed.
    6. tearDown()--reset this instrumentation test back to start.

    at a minumum u just need
    1. setup() -- get android specific stuff. like activities.
    2. tearDown() -- reset ur unit test.s
 */
public class MainWeatherActivityTest extends ActivityInstrumentationTestCase2<MainWeatherActivity>
{
    private static final String LOGTAG = "MainWeatherActivityTest";

    //private members for this test.
    private MainWeatherActivity mActivity;
    private String resourceString;

    /*
        both of these constructors set the activity to using android specific
        features.
     */
    public MainWeatherActivityTest()
    {
        super(MainWeatherActivity.class);

    }

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

        mActivity = getActivity();//first time this is called, starts the activity. onCreate() is invoked.
        resourceString = mActivity.getString(R.string.hello_world);
    }

    //this is a junit test method, with "test" as the begining of the method name.
    //helper to check if we have the data we need before a test case is run.
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
