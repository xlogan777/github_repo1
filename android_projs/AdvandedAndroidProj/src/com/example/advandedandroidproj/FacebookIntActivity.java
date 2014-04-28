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
	 //vars used for facebook UI
	 private TextView txtUserName;
     private Button btnLogin;
         
 	public static final String TAG = "FacebookIntActivity";
 	
 	//facebook object to manage the session
    private Facebook mFacebook;
    
    //app id that i got from FB, when i created the app to chat with..
    //had to do this with facebook account.
    public static final String APP_ID = "620626734686580";
    
    //performs the request to facebook via url with my app id, via the facebook obj.
    private AsyncFacebookRunner mAsyncRunner;
    
    //this is wat i allow to view, the read stream and email.
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
                	//setup fb obj with app id
                	mFacebook = new Facebook(APP_ID);
                	
                	//provide obj with with fb obj to do asynch processing.
                    mAsyncRunner = new AsyncFacebookRunner(mFacebook);
                    
                    //attempt to the authorization of my app id, with the
                    //areas i want to access which is the read stream and email.
                    //use this activity as the one to do the authorization.
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
			
			//logout from facebook session. using the application context.
			mFacebook.logout(this.getApplicationContext());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//private class that will check to see if the access was done correctly 
    private class LoginDialogListener implements DialogListener 
    {
            @Override
            //this is the function to override that allows to be notified if the
            //connection was done for login
            public void onComplete(Bundle values) 
            {
                    Log.d(TAG, "LoginONComplete");
                    
                    //get connection info
                    String token = mFacebook.getAccessToken();
                    long token_expires = mFacebook.getAccessExpires();
                    
                    Log.d(TAG, "AccessToken: " + token);
                    Log.d(TAG, "AccessExpires: " + token_expires);
                    
                    //peform the request on "me" which is my app id, with an obj that
                    //will read the data from the json obj .
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

    //this class will do the listening for the data response back
    //from the ansynch request done by the [mAsyncRunner.request("me", new IDRequestListener())]    
    private class IDRequestListener implements RequestListener
    { 
    	@Override
    	//this will be called when we get a response back from te asynch.request function call
        public void onComplete(String response, Object state) 
    	{
    		try 
    		{
                Log.d(TAG, "IDRequestONComplete");
                Log.d(TAG, "Response: " + response.toString());
                
                //get the json response from the function params.
                //param needs to be "final" since it is being accessed by anonymous class.
                final String myresponse = response;
                
                //invoke the UI features owned by the UI thread via this activity but run as a seperate
                //thread
                FacebookIntActivity.this.runOnUiThread
                (new Runnable() 
                	{
                		//parse the json and get the info from the json obj.
                		JSONObject json = Util.parseJson(myresponse);
                		private TextView username = txtUserName;
                		
                        private String id = json.getString("id");
                        private String name = json.getString("name");
                		
                        public void run() 
                        {
                        	//update the ui thread with the infor from the json string.
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
