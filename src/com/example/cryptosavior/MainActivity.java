package com.example.cryptosavior;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.String;


public class MainActivity extends Activity {
	
	Button login = null;
	Button one = null;
	Button two = null;
	Button three = null;
	Button four = null;
	Button five = null;
	Button six = null;
	Button seven = null;
	Button eight = null;
	Button nine = null;
	Button zero = null;
	Button del = null;
	Button signup = null;
	EditText txt;
	DatabaseAdapter dbAdapter;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        login = (Button) findViewById(R.id.login);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        del = (Button) findViewById(R.id.del);
        signup = (Button) findViewById(R.id.signup);
        txt = (EditText) findViewById(R.id.mk);
        dbAdapter = new DatabaseAdapter(getApplicationContext());
        
        new File("/mnt/sdcard/.CryptoSavior").mkdir();
        
        prefs = getSharedPreferences("KEY", MODE_PRIVATE);
        editor = prefs.edit();
        
        editor.clear();
        editor.commit();
        
        /*editor.putInt("ENCVALUE", 0);
		editor.commit();*/

		txt.setOnTouchListener(new OnTouchListener(){
			
			
		    public boolean onTouch(View v, MotionEvent event) {
		    	
		    	EditText txt = (EditText) findViewById(R.id.mk);
		        int inType = txt.getInputType(); // backup the input type
		        txt.setInputType(InputType.TYPE_NULL); // disable soft input
		        txt.onTouchEvent(event); // call native handler
		        txt.setInputType(inType); // restore input type
		        return true; // consume touch even
		    }
		}); 
        
        
        one.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(1);
				
			}
		});
        
        two.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(2);
				
			}
		});
        
        three.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(3);
				
			}
		});
        
        four.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(4);
				
			}
		});
        
        five.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(5);
				
			}
		});
        
        six.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(6);
				
			}
		});
        
        seven.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(7);
				
			}
		});
        
        eight.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(8);
				
			}
		});
        
        nine.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(9);
				
			}
		});
        
        zero.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(0);
				
			}
		});
        
        del.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int len = String.valueOf(txt.getText().toString()).length();
				
				if(len <= 1) {
					txt.setText(null);
				}
				else {
					String str = txt.getText().toString();
					String newStr = str.substring(0, len-1);
					txt.setText(newStr);
				}
				
			}
		});
        
        signup.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbAdapter.open();
				if(dbAdapter.checkdb()) {
					Toast.makeText(getApplicationContext(),"MasterKey Already Exists", Toast.LENGTH_LONG).show();
				}
				else {
					Intent i = new Intent();
					i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.SignUp");
					txt.setText("");
					startActivity(i);
				}
				dbAdapter.close();
					
			}
		});
        
        login.setOnClickListener(new OnClickListener() {

    			public void onClick(View v) {
    				String key = txt.getText().toString();
    				dbAdapter.open();
					if(dbAdapter.checkdb()) {
    				try{
    					if(key.length() > 0)
    					{   
    						if(dbAdapter.login(key))
    						{
    							Toast.makeText(MainActivity.this,"User Successfully Authenticated", Toast.LENGTH_LONG).show();
    							editor.putString("key", key);
    							editor.commit();
    							Intent i2 = new Intent();
    							i2.setClassName("com.example.cryptosavior", "com.example.cryptosavior.PreHome");
    							txt.setText("");
    							startActivity(i2);
    						}else{
    							txt.setText("");
    							Toast.makeText(MainActivity.this,"Invalid MasterKey Provided\n Please Try Again.", Toast.LENGTH_LONG).show();
    						}
    						dbAdapter.close();
    					}
    					else
    						Toast.makeText(getApplicationContext(), "Please Enter a MasterKey and Try again", Toast.LENGTH_SHORT).show();
    					
    				}catch(Exception e)
    				{
    					Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
    				}
					}
					else {
						txt.setText("");
						Toast.makeText(getApplicationContext(), "MasterKey Not Set\n     Click Sign Up", Toast.LENGTH_LONG).show();
					}
    			}  			
    		});
    }
    
    public void update(int val) {
    	
		int len = String.valueOf(txt.getText().toString()).length();
		
		if(len==0) {
			txt.setText(String.valueOf(val));
		}
		else {
			String s = txt.getText().toString();
			s = s + val;
			txt.setText(s);
		}
    	
    }
    
	@Override
	public void onBackPressed() {
	    	//super.onBackPressed();
	    		editor.clear();
	    		editor.commit();
	    		finish();	    	
	}
    
    @Override
    public void onStop() {
    		super.onStop();
    	finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menuItem:  
            	
            	Context context = this;
            
            	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
     
    			alertDialogBuilder.setTitle("About");
     
    			alertDialogBuilder
    				.setMessage("Copyright © 2012-20XX CryptoSavior, All Rights Reserved.")
    				.setCancelable(true)
    				.setPositiveButton("OK",null);
    			
    				AlertDialog alertDialog = alertDialogBuilder.create();
    				alertDialog.show();
            
    				TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
    				messageView.setGravity(Gravity.CENTER);
    				break;
    		
            case R.id.forgot_masterKey:
            	
            	dbAdapter.open();
            	if(dbAdapter.checkdb())
            		startActivity(new Intent(this, ForgotMasterKey.class));
            	else
            		Toast.makeText(getApplicationContext(), "MasterKey Not Set\n     Click Sign Up", Toast.LENGTH_SHORT).show();
            	dbAdapter.close();
            	break;
         
        }
        return true;
    }
}
