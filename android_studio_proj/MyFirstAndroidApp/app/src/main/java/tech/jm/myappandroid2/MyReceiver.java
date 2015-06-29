package tech.jm.myappandroid2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        int x;
        if(intent.getAction().equals("tech.jm.BR_ACTION_1"))
            Log.d("MyReceiver","here i am with broadcast rcv "+intent.getStringExtra("data_to_consume"));
        else if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
            Log.d("MyReceiver","got an sms txt msg from someone....");

    }
}
