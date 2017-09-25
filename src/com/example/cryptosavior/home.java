package com.example.cryptosavior;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

public class home extends Activity {
	
	public Button browse;
	EditText et;
	int flagBackPressed = 0;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.home);
	        
	        
	    	browse = (Button) findViewById(R.id.browse);
	    	et = (EditText) findViewById(R.id.fileName);

	    	prefs = getSharedPreferences("KEY", MODE_PRIVATE);
	    	editor = prefs.edit();
	    	
	    	et.setOnTouchListener(new OnTouchListener(){
							
			public boolean onTouch(View v, MotionEvent event) {
							    	
			    int inType = et.getInputType(); // backup the input type
			    et.setInputType(InputType.TYPE_NULL); // disable soft input
			    et.onTouchEvent(event); // call native handler
			    et.setInputType(inType); // restore input type
			    return true; // consume touch even
			    }
			}); 

	    	browse.setOnClickListener( new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.FileSelect");
					startActivity(i);
					finish();
				}
			});
	    	
	 }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
        return true;
    }

}
