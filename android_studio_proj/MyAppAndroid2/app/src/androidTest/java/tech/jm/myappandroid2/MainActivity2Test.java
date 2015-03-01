package tech.jm.myappandroid2;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by jimmy on 2/2/2015.
 */

//for every test being executed this setup, testXXX, tearDown is always performed
//meaning setup, test1, tearDown | setup, test2, tearDown |...etc
//this ActivityInstrumentationTestCase2 base class is for using android specific
//features with ur activity..there is a "ApplicationTestCase" base class the needs to be used
//with the single activity as a pure unit test class.
public class MainActivity2Test extends ActivityInstrumentationTestCase2<MainActivity2>
{
    private MainActivity2 mActivity;
    //private TextView mView;
    private String resourceString;

    public MainActivity2Test()
    {
        super(MainActivity2.class);

    }

    //setup object with activity to test with system usage and android specific stuff.
    public MainActivity2Test(Class<MainActivity2> activityClass)
    {
        super(activityClass);
    }

    //here we setup the class members for the UT
    @Override
    public void setUp() throws Exception
    {
        super.setUp();

        mActivity = this.getActivity();

        //mView = (TextView)mActivity.findViewById(R.id.hide_show_tiles);
        resourceString = mActivity.getString(R.string.hello_world);
    }

    //areas to test the activity via test methods, similar to JUnit.
    public void testPreconditions() throws Exception
    {
        this.assertNotNull(mActivity);
        Log.d("MainActivity2Test","the activity retrieved is not null");
    }

    public void testText() throws Exception
    {
        assertEquals(resourceString,"Hello world!");
        Log.d("MainActivity2Test","tested the hello world and it was ok..");
    }

    //here we close the obj with stuff that sets the test case back to start
    //and release any resources obtained during the test.
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();

        mActivity = null;
        resourceString = null;
    }
}
