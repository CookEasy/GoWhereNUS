package com.is3261.GoWhereNUS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.is3261.GoWhereNUS.R;
import com.is3261.Objects.User;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LauncherActivity extends ActionBarActivity {
	
	ProgressDialog dialog;
	LauncherActivity launcherActivity = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		System.out.println("Before initialize");
		Parse.initialize(this, "87oAquBWb0k4smKAWCDeXEC3RnT8g8XyeXDlDmL4", "OcxglNUQPt3Ry8movAGHGsyujf8lz0U0zKkq98Wp");
		System.out.println("Parse is initiliazed");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void btnPressed(View view){
		dialog =new ProgressDialog(this);
		dialog.setTitle("Progress");
		dialog.setIndeterminate(true);
		
		dialog.show();
		// username, password validation check
		EditText usr = (EditText) findViewById(R.id.username);
		EditText pwd = (EditText) findViewById(R.id.password);
		
		String username = usr.getEditableText().toString();
		String password = pwd.getEditableText().toString();
		
		ParseUser.logInInBackground(username, password, new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
					  dialog.dismiss();
			    if (user != null) {
			      // Hooray! The user is logged in.
			    	System.out.println("User is logged in");
			    	
			    	//set a user object
			    	User currentUser = new User();
			    	currentUser.setObjectId(user.getObjectId());
			    	currentUser.setUsername(user.getUsername());
			    	
			    	Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
			    	intent.putExtra("user", currentUser);
			    	
			    	startActivity(intent);
			    } else {
			    	System.out.println("User not logged in");
			    	Toast toast = Toast.makeText(getApplicationContext(), "User does not exist or wrong password", Toast.LENGTH_LONG);
			    	toast.show();
			      // Signup failed. Look at the ParseException to see what happened.
			    }
			  }
			});
		/*
		Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
		startActivity(intent);*/
	}
	

}
