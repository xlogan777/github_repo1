package com.example.helloworldandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

public class UserIfaceLayoutActivity extends ActionBarActivity 
{
	private static String MyTag = "UserIfaceLayoutActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//this will get the bundle object from the intent with all the extras. 
		Bundle bundle = this.getIntent().getExtras();
		String inputVal = bundle.getString("editField");
		
		//this line below is another way of getting the data from intent obj w/o getting a bundle.
		//this.getIntent().getStringExtra("editField");this will directly get extra data from intent obj		
		setContentView(R.layout.activity_user_iface_layout);
		
		Log.d(MyTag,"JM..doing the UI sample demo..value for input text = "+inputVal);
	}	
}
