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

public class ChangeKeyOld extends Activity {
	
	EditText ckmk;
	Button ckone,cktwo,ckthree,ckfour,ckfive,cksix,ckseven,ckeight,cknine,ckzero,cklogin,ckdel;
	DatabaseAdapter dbAdapter;
	SharedPreferences ckprefs;
	SharedPreferences.Editor ckeditor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_key_old);
        
        ckmk =(EditText) findViewById(R.id.ckmk);
        cklogin =(Button) findViewById(R.id.cklogin);
        ckone =(Button) findViewById(R.id.ckone);
        cktwo =(Button) findViewById(R.id.cktwo);
        ckthree =(Button) findViewById(R.id.ckthree);
        ckfour =(Button) findViewById(R.id.ckfour);
        ckfive =(Button) findViewById(R.id.ckfive);
        cksix =(Button) findViewById(R.id.cksix);
        ckseven =(Button) findViewById(R.id.ckseven);
        ckeight =(Button) findViewById(R.id.ckeight);
        cknine =(Button) findViewById(R.id.cknine);
        ckzero =(Button) findViewById(R.id.ckzero);
        ckdel =(Button) findViewById(R.id.ckdel);
        dbAdapter = new DatabaseAdapter(getApplicationContext());
        
        ckprefs = getSharedPreferences("KEY", MODE_PRIVATE);
        ckeditor = ckprefs.edit();
        
        ckmk.setOnTouchListener(new OnTouchListener(){		
			
		    public boolean onTouch(View v, MotionEvent event) {

		        int inType = ckmk.getInputType(); // backup the input type
		        ckmk.setInputType(InputType.TYPE_NULL); // disable soft input
		        ckmk.onTouchEvent(event); // call native handler
		        ckmk.setInputType(inType); // restore input type
		        return true; // consume touch even
		    }
		});
		
		ckone.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(1);
				
			}
		});
        
        cktwo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(2);
				
			}
		});
        
        ckthree.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(3);
				
			}
		});
        
        ckfour.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(4);
				
			}
		});
        
        ckfive.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(5);
				
			}
		});
        
        cksix.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(6);
				
			}
		});
        
        ckseven.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(7);
				
			}
		});
        
        ckeight.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(8);
				
			}
		});
        
        cknine.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(9);
				
			}
		});
        
        ckzero.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ckupdate(0);
				
			}
		});
        
		ckdel.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int len = String.valueOf(ckmk.getText().toString()).length();
						
						if(len <= 1) {
							ckmk.setText(null);
						}
						else {
							String str = ckmk.getText().toString();
							String newStr = str.substring(0, len-1);
							ckmk.setText(newStr);
						}
						
					}
				});
		
		cklogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String key = ckmk.getText().toString();
				dbAdapter.open();
				if(dbAdapter.login(key)) {
					Intent i5 = new Intent();
					i5.setClassName("com.example.cryptosavior", "com.example.cryptosavior.ChangeKeyNew");
					ckmk.setText("");
					startActivity(i5);
				}
				else {
					Toast.makeText(getApplicationContext(), "Wrong MasterKey Entered", Toast.LENGTH_SHORT).show();
					ckmk.setText("");
				}
				dbAdapter.close();
			}
			
		});
	}    
		
	public void ckupdate(int val) {
	    	
			int len = String.valueOf(ckmk.getText().toString()).length();
			
			if(len == 0) {
				ckmk.setText(String.valueOf(val));
			}
			else {
				String s = ckmk.getText().toString();
				s = s + val;
				ckmk.setText(s);
			}
		
	}
	    
	@Override
	public void onStop() {
	   	super.onStop();
	    finish();
    }
	
	/*@Override
	public void onBackPressed() {
	    	super.onBackPressed();
		Intent i2 = new Intent();
		i2.setClassName("com.example.cryptosavior", "com.example.cryptosavior.PreHome");
		startActivity(i2);
	    finish();
	    	
	}*/

}
