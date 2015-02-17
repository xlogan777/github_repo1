package tech.jm.myappandroid2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity3Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);

        Intent my_intent = this.getIntent();

        String [] list_items = my_intent.getStringArrayExtra("display_list_items");
//        String input_txt = my_intent.getStringExtra("task_name");
//        int check_status = my_intent.getIntExtra("check_status", 0);
//        int check_priority = my_intent.getIntExtra("check_priority", 0);
//
//        String new_item = input_txt+" status = "+check_status+" pri : "+check_priority;

        //create the data to add to the list view.
        // Get ListView object from xml
        final ListView listView = (ListView) findViewById(R.id.my_listView);

        // Defined Array values to show in ListView
        List<String> my_list_data = new ArrayList<String>();

        //never had list
        if(list_items == null)
        {

            //adds to the front of the list, all new items will be added after this one item.
            my_list_data.add(0, "Add New Todo Item");
        }
        else
        {
            for(String data : list_items)
            {
                my_list_data.add(0, data);
            }
        }

        final String[] values = new String[my_list_data.size()];
        my_list_data.toArray(values);

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
//                Toast.makeText
//                (getApplicationContext(),"Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();

                if(itemValue.equalsIgnoreCase("Add New Todo Item"))
                {
                    Intent todo_item = new Intent(MainActivity3Activity.this, MainActivity32Activity.class);
                    //todo_item.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    todo_item.putExtra("display_list_items",values);
                    startActivity(todo_item);
                    finish();//dont add to backstack.
                    Log.d("","get more data for the todo mgr.");
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
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
