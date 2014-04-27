package com.example.advandedandroidproj;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
 * this class will deal with creating the facebook sdk usage
 */
public class FacebookIntActivity extends Activity 
{
	 //vars used for facebook.
	 private TextView txtUserName;
     private Button btnLogin;
     
 	public static final String TAG = "FacebookIntActivity";
    private Facebook mFacebook;
    public static final String APP_ID = "620626734686580";
    private AsyncFacebookRunner mAsyncRunner;
    private static final String[] PERMS = new String[] { "read_stream" ,"email"};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_int);
		
		//get the view objs as needed
		txtUserName = (TextView) findViewById(R.id.textFacebook);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        
        //setup call back for login.
        btnLogin.setOnClickListener
        (	new OnClickListener() 
        	{
                @Override
                public void onClick(View view) 
                {
                	mFacebook = new Facebook(APP_ID);        
                    mAsyncRunner = new AsyncFacebookRunner(mFacebook);
	                mFacebook.authorize(FacebookIntActivity.this, PERMS, new LoginDialogListener());
                }
        	}
        );

	}
	
	protected void onDestroy()
	{
		super.onDestroy();
		
		try
		{
			Toast.makeText(getApplicationContext(),"logging out of FB!!!", Toast.LENGTH_SHORT).show();
			
			//logout
			mFacebook.logout(this.getApplicationContext());
			
		}
		catch(Exception e){e.printStackTrace();}
	}

    private class LoginDialogListener implements DialogListener 
    {
            @Override
            public void onComplete(Bundle values) 
            {
                    Log.d(TAG, "LoginONComplete");
                    String token = mFacebook.getAccessToken();
                    long token_expires = mFacebook.getAccessExpires();
                    
                    Log.d(TAG, "AccessToken: " + token);
                    Log.d(TAG, "AccessExpires: " + token_expires);
                    
                    mAsyncRunner.request("me", new IDRequestListener());
            }

            @Override
            public void onFacebookError(FacebookError e) 
            {
                    Log.d(TAG, "FacebookError: " + e.getMessage());
            }

            @Override
            public void onError(DialogError e) 
            {
                    Log.d(TAG, "Error: " + e.getMessage());
            }

            @Override
            public void onCancel() 
            {
                    Log.d(TAG, "OnCancel");
            }
    }//LoginDialogListener

    private class IDRequestListener implements RequestListener
    { 
    	@Override
        public void onComplete(String response, Object state) 
    	{
    		try 
    		{
                Log.d(TAG, "IDRequestONComplete");
                Log.d(TAG, "Response: " + response.toString());
                
                final String myresponse = response;
                
                FacebookIntActivity.this.runOnUiThread
                (new Runnable() 
                	{
                		JSONObject json = Util.parseJson(myresponse);
                		private TextView username = txtUserName;
                        private String id = json.getString("id");
                        private String name = json.getString("name");
                		
                        public void run() 
                        {
                        	username.setText("Welcome: " + name+"\n ID: "+id);
                        }
                	}
                );
                
	        }
    		
	    	catch (JSONException e) 
	        {
	                Log.d(TAG, "JSONException: " + e.getMessage());
	        }
	    	catch (FacebookError e) 
	    	{
	                Log.d(TAG, "FacebookError: " + e.getMessage());
	        }
    	}
    
		@Override
		public void onIOException(IOException e, Object state) 
		{
		        Log.d(TAG, "IOException: " + e.getMessage());
		}
		
		@Override
		public void onFileNotFoundException(FileNotFoundException e, Object state) 
		{
		        Log.d(TAG, "FileNotFoundException: " + e.getMessage());
		}
		
		@Override
		public void onMalformedURLException(MalformedURLException e, Object state) 
		{
		        Log.d(TAG, "MalformedURLException: " + e.getMessage());
		}
		
		@Override
		public void onFacebookError(FacebookError e, Object state) 
		{
		        Log.d(TAG, "FacebookError: " + e.getMessage());
		}
    }//IDRequestListener
	
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		Log.d(TAG, "entered the onActivityResult");
		mFacebook.authorizeCallback(requestCode, resultCode, data);			
	}
}
