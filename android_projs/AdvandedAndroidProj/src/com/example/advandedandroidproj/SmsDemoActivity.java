package com.example.advandedandroidproj;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsDemoActivity extends Activity 
{
	//these are the vars that hold the views for fields and buttons.
	Button sendBtn;
	EditText txtphoneNo;
	EditText txtMessage;
	String number = "718-803-0886";
	
	//phone button
	Button phoneBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_demo);
		
		//this will get all view objs and save them to this class.
		sendBtn = (Button) findViewById(R.id.btnSendSMS);
		txtphoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
		txtMessage = (EditText) findViewById(R.id.editTextSMS);
		
		//phone button view obj
		phoneBtn = (Button) findViewById(R.id.phone_button);
		phoneBtn.setText(number);
		
		//setup anonymous class as a click event to call internal activity function.
		sendBtn.setOnClickListener
		(
			new View.OnClickListener() 
			{
				public void onClick(View view) 
				{
					//send the sms txt.
					sendSMSMessage();
				}
			}
		);
		
		//hook in a listener for this view obj.
		phoneBtn.setOnClickListener
		(
			new View.OnClickListener() 
			{
				public void onClick(View v) 
				{
					makePhoneCall();
				}
			}
		);
		
	}
	
	/*
	 * this will make a phone call using built in app via intent obj.
	 */
	protected void makePhoneCall()
	{
		//create intent obj with action to make a call with system phone.
		//ACTION_DIAL, type of action will allow to edit before dialing.
		//Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
		Intent phoneIntent = new Intent(Intent.ACTION_CALL);
		
		//setup phone call for this intent..
		phoneIntent.setData(Uri.parse("tel:"+number));
		
		try 
		{
			//invoke intent resolver with this intent. 
			startActivity(phoneIntent);
			
			//come back to main activity that laucnhed this intent once done.
			finish();
			
			Log.d("SmsDemoActivity", "Finished making a call...");
		} 
		catch (android.content.ActivityNotFoundException ex) 
		{
			Toast.makeText(getApplicationContext(),"Call faild, please try again later.", Toast.LENGTH_SHORT).show();
		}
	}
	
	/*
	 * this will do the processing for the sms feature using the sms mgr.
	 */
	protected void sendSMSMessage() 
	{
		Log.d("SmsDemoActivity", "send sms via the sms mgr.");
		
		//this will get the string from the view objs...phone number and msg.
		String phoneNo = txtphoneNo.getText().toString();
		String message = txtMessage.getText().toString();
				
		try 
		{
			//get the system mgr for sms.
			SmsManager smsManager = SmsManager.getDefault();
			
			//send the txt msg with the number and the msg
			smsManager.sendTextMessage(phoneNo, null, message, null, null);

			//show user msg sent.
			Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
			
			Log.d("SmsDemoActivity", "send sms via smsMgr using built in sms app.");
		} 
		
		catch (Exception e) 
		{
			Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();			
			e.printStackTrace();
		}
		
		
		//this will create an intent obj and use an app to send the txt msg.
		
		//create intent obj with action being for sms.
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		
		//set data to be sms
		smsIntent.setData(Uri.parse("smsto:"));
		
		//set data type to be sms.
		smsIntent.setType("vnd.android-dir/mms-sms");
		
		//add the phone number and the body of the txt here..
		smsIntent.putExtra("address" , new String ("7183122934"));
		smsIntent.putExtra("sms_body" , "test from intent based sms..JM");
		
		try 
		{
			//invoke the intent resolver to allow user to pick the app for this intent.
			startActivity(smsIntent);
			
			//return control back to activity that launched the intent obj/
			finish();
			
			Log.d("SmsDemoActivity", "send sms via the intent obj..using built in sms app.");
		}
		catch (android.content.ActivityNotFoundException ex) 
		{
			Toast.makeText(getApplicationContext(), "SMS faild, please try again later Intent base.", Toast.LENGTH_SHORT).show();
			ex.printStackTrace();
		}
	}
}
