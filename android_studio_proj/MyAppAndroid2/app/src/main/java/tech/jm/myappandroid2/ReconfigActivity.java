package tech.jm.myappandroid2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;


public class ReconfigActivity extends ActionBarActivity implements OnClickListener {

    private LinearLayout linearLayout;
    private TextView reconfigurationsText;
    private TextView resumeText;
    private Button newActivityButton;

    private int numPauses = 0;
    private int numReconfigurations = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get data from the intent obj after u get the intent obj.
        Intent wifi_intent = this.getIntent();
        int wifi_state = wifi_intent.getExtras().getInt("wifi_state");

//        final TextView tv = (TextView)findViewById(R.id.textView2);
//        tv.setText("wifi state = "+wifi_state);

        linearLayout = new LinearLayout(this);
        reconfigurationsText = new TextView(this);
        resumeText = new TextView(this);
        newActivityButton = new Button(this);

        reconfigurationsText.setText("Reconfigurations: " + numReconfigurations +" "+"wifi state = "+wifi_state);
        resumeText.setText("Pauses/Resumes: " + numPauses);
        newActivityButton.setText("Start new activity");

        newActivityButton.setOnClickListener(this);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(reconfigurationsText);
        linearLayout.addView(resumeText);
        linearLayout.addView(newActivityButton);

        setContentView(linearLayout);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconfig);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reconfig, menu);
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
