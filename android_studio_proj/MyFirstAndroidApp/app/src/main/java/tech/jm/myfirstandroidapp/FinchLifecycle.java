package tech.jm.myfirstandroidapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class FinchLifecycle extends ActionBarActivity {

    // Make strings for logging
    private final String TAG = this.getClass().getSimpleName();
    private final String RESTORE = ", can restore state";

    // The string "fortytwo" is used as an example of state
    private final String state = "fortytwo";

    private MySqliteDBTest my_sql_db;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_finch_lifecycle);
        String answer = null;
// savedState could be null
        if (null != savedState) {
            answer = savedState.getString("answer");
        }
        Log.i(TAG, "onCreate" + (null == savedState ? "" : (RESTORE + " " + answer)));

        //create db instance now.
        my_sql_db = new MySqliteDBTest(this,"name of db",null);

        //get the info from the text fields and provide it to the
        //DB to do  more processing

        final EditText et1 = (EditText)this.findViewById(R.id.editText);
        final EditText et2 = (EditText)this.findViewById(R.id.editText2);
        final EditText et3 = (EditText)this.findViewById(R.id.editText3);
        final EditText et4 = (EditText)this.findViewById(R.id.editText4);

        //add the callback for each of the items here.
        Button butt1 = (Button)this.findViewById(R.id.button);
        butt1.setOnClickListener
                (
                        new Button.OnClickListener()
                        {
                            @Override
                            public void onClick(View arg1)
                            {
                                String pid = et1.getText().toString();
                                int my_pid = Integer.parseInt(pid);
                                String fname = et2.getText().toString();
                                String lname = et3.getText().toString();
                                String age = et4.getText().toString();
                                int my_age = Integer.parseInt(age);

                                my_sql_db.addPerson(my_pid, fname, lname, my_age);
                            }
                        }
                );

        Button butt3 = (Button)this.findViewById(R.id.button3);
        butt3.setOnClickListener
                (
                        new Button.OnClickListener()
                        {
                            @Override
                            public void onClick(View arg1)
                            {
                                String pid = et1.getText().toString();
                                int my_pid = Integer.parseInt(pid);
                                String fname = et2.getText().toString();
                                String lname = et3.getText().toString();
                                String age = et4.getText().toString();
                                int my_age = Integer.parseInt(age);

                                my_sql_db.updatePerson(my_pid, fname, lname, my_age);
                           }
                        }
                );

        Button butt4 = (Button)this.findViewById(R.id.button4);
        butt4.setOnClickListener
                (
                        new Button.OnClickListener()
                        {
                            @Override
                            public void onClick(View arg1)
                            {
                                String pid = et1.getText().toString();
                                int my_pid = Integer.parseInt(pid);
                                my_sql_db.deletePerson(my_pid);
                            }
                        }
                );

        Button butt5 = (Button)this.findViewById(R.id.button5);
        butt5.setOnClickListener
                (
                        new Button.OnClickListener()
                        {
                            @Override
                            public void onClick(View arg1)
                            {
                                String pid = et1.getText().toString();
                                int my_pid = Integer.parseInt(pid);
                                HashMap<String,String> rv = my_sql_db.getPerson(my_pid);

                                et2.setText(rv.get("fname"));
                                et3.setText(rv.get("lname"));
                                et4.setText(rv.get("age"));

                                Log.d(TAG,"Person details with hashmap..");
                            }
                        }
                );

    }
    @Override
    protected void onRestart() {
        super.onRestart();
// Notification that the activity will be started
        Log.i(TAG, "onRestart");
    }
    @Override
    protected void onStart() {
        super.onStart();
// Notification that the activity is starting
        Log.i(TAG, "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
// Notification that the activity will interact with the user
        Log.i(TAG, "onResume");
    }
    protected void onPause() {
        super.onPause();
// Notification that the activity will stop interacting with the user
        Log.i(TAG, "onPause" + (isFinishing() ? " Finishing" : ""));
    }
    @Override
    protected void onStop() {
        super.onStop();
// Notification that the activity is no longer visible
        Log.i(TAG, "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
// Notification the activity will be destroyed
        Log.i(TAG,
                "onDestroy "
// Log which, if any, configuration changed
                        + Integer.toString(getChangingConfigurations(), 16));
    }
    // ////////////////////////////////////////////////////////////////////////////
// Called during the lifecycle, when instance state should be saved/restored
// ////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onSaveInstanceState(Bundle outState) {
// Save instance-specific state
        outState.putString("answer", state);
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    /*@Override
    public Object onRetainNonConfigurationInstance() {
        Log.i(TAG, "onRetainNonConfigurationInstance");
        return new Integer(getTaskId());
    }*/

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
// Restore state; we know savedState is not null
        String answer = null != savedState ? savedState.getString("answer") : "";
        Object oldTaskObject = getLastNonConfigurationInstance();
        if (null != oldTaskObject) {
            int oldtask = ((Integer) oldTaskObject).intValue();
            int currentTask = getTaskId();
// Task should not change across a configuration change
            assert oldtask == currentTask;
        }
        Log.i(TAG, "onRestoreInstanceState"
                + (null == savedState ? "" : RESTORE) + " " + answer);
    }
    // ////////////////////////////////////////////////////////////////////////////
// These are the minor lifecycle methods, you probably won't need these
// ////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onPostCreate(Bundle savedState) {
        super.onPostCreate(savedState);
        String answer = null;
// savedState could be null
        if (null != savedState) {
            answer = savedState.getString("answer");
        }
        Log.i(TAG, "onPostCreate"
                + (null == savedState ? "" : (RESTORE + " " + answer)));
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume");
    }
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.i(TAG, "onUserLeaveHint");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finch_lifecycle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
