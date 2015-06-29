package tech.jm.myappandroid2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity32Activity extends ActionBarActivity {

    private int check_status;
    private int check_priority;
    private String input_txt_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity32);

        Intent my_intent = this.getIntent();
        final String [] data_list = my_intent.getStringArrayExtra("display_list_items");

        final EditText et = (EditText)this.findViewById(R.id.editText);
        final RadioButton rb1 = (RadioButton)this.findViewById(R.id.radioButton);
        final RadioButton rb2 = (RadioButton)this.findViewById(R.id.radioButton2);
        final RadioButton rb3 = (RadioButton)this.findViewById(R.id.radioButton3);
        final RadioButton rb4 = (RadioButton)this.findViewById(R.id.radioButton4);
        final RadioButton rb5 = (RadioButton)this.findViewById(R.id.radioButton5);

        //cancel.
        final Button button = (Button)this.findViewById(R.id.button);
        button.setOnClickListener( new Button.OnClickListener()
            {
                public void onClick(View view)
                {
                    try
                    {
                        Intent my_intent = new Intent(MainActivity32Activity.this, MainActivity3Activity.class);
                        //my_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Log.d("","return to calling activity");
                        startActivity(my_intent);
                        finish();//dont add to backstack.
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
        );

        //reset
        final Button button2 = (Button)this.findViewById(R.id.button2);
        button2.setOnClickListener( new Button.OnClickListener()
           {
               public void onClick(View view)
               {
                   try
                   {
                       et.setText("");
                       if(rb1.isChecked())
                           rb1.setChecked(false);

                       if(rb2.isChecked())
                           rb2.setChecked(false);

                       if(rb3.isChecked())
                           rb3.setChecked(false);

                       if(rb4.isChecked())
                           rb4.setChecked(false);

                       if(rb5.isChecked())
                           rb5.setChecked(false);

                       Log.d("","cleared all entered data.");

                   }
                   catch(Exception e){e.printStackTrace();}
               }
           }
        );

        //submit
        final Button button3 = (Button)this.findViewById(R.id.button3);
        button3.setOnClickListener( new Button.OnClickListener()
            {
                public void onClick(View view)
                {
                    try
                    {
                        input_txt_data = et.getText().toString();
                        check_status = -1;
                        if(rb1.isChecked())
                        {
                            check_status = 1;
                        }
                        else if(rb2.isChecked())
                        {
                            check_status = 2;
                        }

                        check_priority = -2;
                        if(rb3.isChecked())
                        {
                            check_priority = 10;
                        }
                        else if(rb4.isChecked())
                        {
                            check_priority = 20;
                        }
                        else if(rb5.isChecked())
                        {
                            check_priority = 30;
                        }

                        Intent my_intent = new Intent(MainActivity32Activity.this, MainActivity3Activity.class);
                        List<String> latest_list = new ArrayList<String>();
                        for(String data : data_list)
                                latest_list.add(0,data);
//                        my_intent.putExtra("task_name",input_txt_data);
//                        my_intent.putExtra("check_status",check_status);
//                        my_intent.putExtra("check_priority",check_priority);

                        String new_item = input_txt_data+" status = "+check_status+" pri : "+check_priority;
                        latest_list.add(0, new_item);
                        String [] send_data = new String[latest_list.size()];
                        latest_list.toArray(send_data);
                        my_intent.putExtra("display_list_items",send_data);
                        //my_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        Log.d("","sent data via intent to todo mgr");
                        startActivity(my_intent);
                        finish();//dont add to backstack.
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity32, menu);
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
