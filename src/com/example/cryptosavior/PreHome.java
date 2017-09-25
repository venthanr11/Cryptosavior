package com.example.cryptosavior;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PreHome extends Activity {
	
	ImageButton encrypt, decrypt, history, changeMK, about, help, exit;	
	/*int flagBackPressed = 0;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;*/
	
	@Override
    public void onCreate(Bundle savedInstanceState) {	
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.pre_home);
        
        encrypt = (ImageButton) findViewById(R.id.encryptBtn);
        decrypt = (ImageButton) findViewById(R.id.decryptBtn2);
        history = (ImageButton) findViewById(R.id.historyBtn);
        changeMK = (ImageButton) findViewById(R.id.change_masterkeyBtn);
        about = (ImageButton) findViewById(R.id.aboutBtn);
        help = (ImageButton) findViewById(R.id.helpBtn);
        exit = (ImageButton) findViewById(R.id.exitBtn);

        /*prefs = getSharedPreferences("KEY", MODE_PRIVATE);
    	editor = prefs.edit();*/
        
        final Context c = this;
        
        encrypt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.FileSelect"/*"com.example.cryptosavior.home"*/);
				startActivity(i);
			}
		});
        
        decrypt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.EncryptedFilesList");
				startActivity(i);
			}
		});

        history.setOnClickListener(new OnClickListener() {
	
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent i = new Intent();
				i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.History");
				startActivity(i);
        	}
        });

        changeMK.setOnClickListener(new OnClickListener() {
	
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent i = new Intent();
				i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.ChangeKeyOld");
				startActivity(i);
        	}
        });

        about.setOnClickListener(new OnClickListener() {
	
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Context context = c;
                
            	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
     
    			alertDialogBuilder.setTitle("About");
     
    			alertDialogBuilder
    				.setMessage("Copyright © 2012-20XX CryptoSavior, All Rights Reserved.")
    				.setCancelable(true)
    				.setPositiveButton("OK",null);
    			
    				// create alert dialog
    				AlertDialog alertDialog = alertDialogBuilder.create();
     
    				// show it
    				alertDialog.show();
            
    				TextView messageView = (TextView)alertDialog.findViewById(android.R.id.message);
    				messageView.setGravity(Gravity.CENTER);
        	}
        });
        
        help.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.Help");
				startActivity(i);
			}
		});
        
        exit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
	    	//super.onBackPressed();
	    
		Toast.makeText(getApplicationContext(), "Click Close button to exit", Toast.LENGTH_SHORT).show();
	    	
	} 

}
