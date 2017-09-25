package com.example.cryptosavior;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmSignUp extends Activity {
	
	EditText csupmk;
	Button csupone,csuptwo,csupthree,csupfour,csupfive,csupsix,csupseven,csupeight,csupnine,csupzero,csuplogin,csupdel;
	DatabaseAdapter dbAdapter;
	SharedPreferences csprefs;
	SharedPreferences.Editor cseditor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_sign_up);
        
        csupmk =(EditText) findViewById(R.id.csupmk);
        csuplogin =(Button) findViewById(R.id.csuplogin);
        csupone =(Button) findViewById(R.id.csupone);
        csuptwo =(Button) findViewById(R.id.csuptwo);
        csupthree =(Button) findViewById(R.id.csupthree);
        csupfour =(Button) findViewById(R.id.csupfour);
        csupfive =(Button) findViewById(R.id.csupfive);
        csupsix =(Button) findViewById(R.id.csupsix);
        csupseven =(Button) findViewById(R.id.csupseven);
        csupeight =(Button) findViewById(R.id.csupeight);
        csupnine =(Button) findViewById(R.id.csupnine);
        csupzero =(Button) findViewById(R.id.csupzero);
        csupdel =(Button) findViewById(R.id.csupdel);
        dbAdapter = new DatabaseAdapter(getApplicationContext());
        csprefs = getSharedPreferences("KEY", MODE_PRIVATE);
        cseditor = csprefs.edit();

        
		csupmk.setOnTouchListener(new OnTouchListener(){
			
			
		    public boolean onTouch(View v, MotionEvent event) {

		        int inType = csupmk.getInputType(); // backup the input type
		        csupmk.setInputType(InputType.TYPE_NULL); // disable soft input
		        csupmk.onTouchEvent(event); // call native handler
		        csupmk.setInputType(inType); // restore input type
		        return true; // consume touch even
		    }
		});
		
		csupone.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(1);
				
			}
		});
        
        csuptwo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(2);
				
			}
		});
        
        csupthree.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(3);
				
			}
		});
        
        csupfour.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(4);
				
			}
		});
        
        csupfive.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(5);
				
			}
		});
        
        csupsix.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(6);
				
			}
		});
        
        csupseven.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(7);
				
			}
		});
        
        csupeight.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(8);
				
			}
		});
        
        csupnine.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(9);
				
			}
		});
        
        csupzero.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(0);
				
			}
		});
        
		csupdel.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int len = String.valueOf(csupmk.getText().toString()).length();
						
						if(len <= 1) {
							csupmk.setText(null);
						}
						else {
							String str = csupmk.getText().toString();
							String newStr = str.substring(0, len-1);
							csupmk.setText(newStr);
						}
						
					}
				});
		
		csuplogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String key1 = csupmk.getText().toString();
				Intent i = getIntent();
				Bundle b = i.getExtras();
				
				String str = b.getString("key");
				if(key1.compareTo(str) == 0) {
				dbAdapter.open();
				long x = dbAdapter.insert(key1);
				if(x > 0) {
				    cseditor.putString("key", key1);
				    cseditor.commit();
				    if(dbAdapter.checkSQ()) {
				    	Intent i2 = new Intent();
				    	i2.setClassName("com.example.cryptosavior", "com.example.cryptosavior.SecurityQuestion");
				    	csupmk.setText("");
				    	startActivity(i2);
				    }
				    else {
				    	Intent i2 = new Intent();
				    	i2.setClassName("com.example.cryptosavior", "com.example.cryptosavior.PreHome");
				    	csupmk.setText("");
				    	startActivity(i2);
				    }
				}
				else {
					Toast.makeText(getApplicationContext(), "User not added", Toast.LENGTH_LONG).show();

				}
				dbAdapter.close();
				}
				else {
					Toast.makeText(getApplicationContext(), "MasterKey Mismatch. Please try again.", Toast.LENGTH_LONG).show();
					Intent i3 = new Intent();
					i3.setClassName("com.example.cryptosavior", "com.example.cryptosavior.SignUp");
					csupmk.setText("");
					startActivity(i3);
				}
			}
			
		});
	}
        
		
	    public void supupdate(int val) {
	    	
			int len = String.valueOf(csupmk.getText().toString()).length();
			
			if(len == 0) {
				csupmk.setText(String.valueOf(val));
			}
			else {
				String s = csupmk.getText().toString();
				s = s + val;
				csupmk.setText(s);
			}
		
	}
	    
	@Override
	public void onStop() {
	    super.onStop();
	    finish();
	}
	
	@Override
	public void onBackPressed() {
		
		Intent i2 = new Intent();
		i2.setClassName("com.example.cryptosavior", "com.example.cryptosavior.MainActivity");
		csupmk.setText("");
		startActivity(i2);
	}
}
