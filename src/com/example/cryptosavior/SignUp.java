package com.example.cryptosavior;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Activity {
	
	EditText supmk;
	Button supone,suptwo,supthree,supfour,supfive,supsix,supseven,supeight,supnine,supzero,suplogin,supdel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        
        supmk =(EditText) findViewById(R.id.supmk);
        suplogin =(Button) findViewById(R.id.suplogin);
        supone =(Button) findViewById(R.id.supone);
        suptwo =(Button) findViewById(R.id.suptwo);
        supthree =(Button) findViewById(R.id.supthree);
        supfour =(Button) findViewById(R.id.supfour);
        supfive =(Button) findViewById(R.id.supfive);
        supsix =(Button) findViewById(R.id.supsix);
        supseven =(Button) findViewById(R.id.supseven);
        supeight =(Button) findViewById(R.id.supeight);
        supnine =(Button) findViewById(R.id.supnine);
        supzero =(Button) findViewById(R.id.supzero);
        supdel =(Button) findViewById(R.id.supdel);
        
		supmk.setOnTouchListener(new OnTouchListener(){
			
			
		    public boolean onTouch(View v, MotionEvent event) {

		        int inType = supmk.getInputType(); // backup the input type
		        supmk.setInputType(InputType.TYPE_NULL); // disable soft input
		        supmk.onTouchEvent(event); // call native handler
		        supmk.setInputType(inType); // restore input type
		        return true; // consume touch even
		    }
		});
		
		supone.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(1);
				
			}
		});
        
        suptwo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(2);
				
			}
		});
        
        supthree.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(3);
				
			}
		});
        
        supfour.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(4);
				
			}
		});
        
        supfive.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(5);
				
			}
		});
        
        supsix.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(6);
				
			}
		});
        
        supseven.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(7);
				
			}
		});
        
        supeight.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(8);
				
			}
		});
        
        supnine.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(9);
				
			}
		});
        
        supzero.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				supupdate(0);
				
			}
		});
        
		supdel.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int len = String.valueOf(supmk.getText().toString()).length();
						
						if(len <= 1) {
							supmk.setText(null);
						}
						else {
							String str = supmk.getText().toString();
							String newStr = str.substring(0, len-1);
							supmk.setText(newStr);
						}
						
					}
				});
		
		suplogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent();
				String key = supmk.getText().toString();
				i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.ConfirmSignUp");
				i.putExtra("key", key);
				supmk.setText("");
				startActivity(i);
			}
			
		});
	}
        
		
	    public void supupdate(int val) {
	    	
			int len = String.valueOf(supmk.getText().toString()).length();
			
			if(len == 0) {
				supmk.setText(String.valueOf(val));
			}
			else {
				String s = supmk.getText().toString();
				s = s + val;
				supmk.setText(s);
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
		supmk.setText("");
		startActivity(i2);
	}

}
