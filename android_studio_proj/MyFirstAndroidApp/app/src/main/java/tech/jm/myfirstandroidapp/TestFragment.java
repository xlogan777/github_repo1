package tech.jm.myfirstandroidapp;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {
    // get a label for our log entries
    private final String TAG = this.getClass().getSimpleName();
    public TestFragment() {
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "fragment onAttach");
    }
    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        if (null != saved) {
// Restore state here
        }
        Log.i(TAG, "fragment onCreate");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saved) {
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        Log.i(TAG, " fragment onCreateView");
        return v;
    }
    @Override
    public void onActivityCreated(Bundle saved) {
        super.onActivityCreated(saved);
        Log.i(TAG, "fragment onActivityCreated");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "fragment onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "fragment onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "fragment onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "fragment onStop");
    }
    // ////////////////////////////////////////////////////////////////////////////
// Called during the lifecycle, when instance state should be saved/restored
// ////////////////////////////////////////////////////////////////////////////
    @Override
    public void onSaveInstanceState(Bundle toSave) {
        super.onSaveInstanceState(toSave);
        Log.i(TAG, "fragment onSaveinstanceState");
    }
}
