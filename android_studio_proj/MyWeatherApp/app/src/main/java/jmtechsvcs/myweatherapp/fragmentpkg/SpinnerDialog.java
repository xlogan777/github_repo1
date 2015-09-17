package jmtechsvcs.myweatherapp.fragmentpkg;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

public class SpinnerDialog extends DialogFragment {

    public SpinnerDialog() {
        // use empty constructors. If something is needed use onCreate's
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading");
        dialog.setMessage("wait for weather data loading..."); // set your messages if not inflated from XML

        return dialog;
    }
}
