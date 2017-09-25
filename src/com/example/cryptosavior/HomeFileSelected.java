package com.example.cryptosavior;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.Time;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeFileSelected extends Activity {
	
	public EditText fileName;
	public Button browse;
	public Button encrypt;
	
    SharedPreferences hprefs;
    SharedPreferences.Editor heditor;
    int flagBackPressed = 0;
    DatabaseAdapter dbAdapter;
    
    final String DIR = "/mnt/sdcard/.CryptoSavior";
    final String PREC = "/Enc";
    final String PACKAGE_NAME = "com.example.cryptosavior";
    final String ACTIVITY_FILE_SELECTED = "com.example.cryptosavior.FileSelect";
    final Context context = this;
	
	 @TargetApi(9)
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.home_file_selected);
	        
	        fileName = (EditText) findViewById(R.id.hfileName);
	    	browse = (Button) findViewById(R.id.hbrowse);
	    	encrypt = (Button) findViewById(R.id.hencrypt);
	    	hprefs = getSharedPreferences("KEY", MODE_PRIVATE);
	    	heditor = hprefs.edit();
	    	dbAdapter = new DatabaseAdapter(getApplicationContext());
	    	
	    	fileName.setOnTouchListener(new OnTouchListener(){
				
	    		public boolean onTouch(View v, MotionEvent event) {
								    	
				    int inType = fileName.getInputType(); 
				    fileName.setInputType(InputType.TYPE_NULL);
				    fileName.onTouchEvent(event);
				    fileName.setInputType(inType);
				    return true;
				    }
			}); 
	    	
	    	fileName.setTypeface(Typeface.SERIF);
  	
	    	Intent i1 = getIntent();
	    	Bundle b = i1.getExtras();
	    	
	    	final String FILE = b.getString("FileName");	    		    		    	
	    	final String PATH = b.getString("Path");	
	
	    	heditor.putString("PATH", PATH);
	    	heditor.putString("FILE", FILE);
	    	heditor.commit();
	    	
	    	fileName.setText(FILE);
	    	
	    	browse.setOnClickListener( new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.setClassName(PACKAGE_NAME, ACTIVITY_FILE_SELECTED);
					startActivity(i);
					finish();
				}
			});
	    	
	    	encrypt.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub	
				
					dbAdapter.open();
					
					if(new File(PATH).getParent().compareTo(DIR) != 0) {
			
						FileNameMap fileNameMap = URLConnection.getFileNameMap();
					    String mime = fileNameMap.getContentTypeFor(PATH);
						long size = new File(PATH).length();
						
						startService(new Intent(getBaseContext(), EncryptionService.class));

							String encPath = DIR+PREC+FILE;
							String encName = PREC+FILE;
							long encSize = new File(DIR+PREC+FILE).length();
							
							Time now = new Time();
							now.setToNow();
							
							String time = Integer.toString(now.year)+"-"+Integer.toString(now.month+1)+"-"+Integer.toString(now.monthDay)+" "
											+Integer.toString(now.hour)+":"+Integer.toString(now.minute)+":"+Integer.toString(now.second);
							
							dbAdapter.fileInsert(PATH, FILE, size, mime);
							dbAdapter.encFileInsert(encPath, encName, encSize);
							dbAdapter.historyFileInsert(PATH, FILE, size, mime, encPath, encName, encSize, time);
							
							dbAdapter.close();	

							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		    				alertDialogBuilder.setTitle("CryptoSavior");

		    				alertDialogBuilder
		    					.setMessage("The File is processed by the CryptoEngine. You will be notified when it is over.")
		    					.setCancelable(false)
		    					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		    						public void onClick(DialogInterface dialog,int id) {
		    							dialog.cancel();
		    							finish();
		    						}
		    					  });

		    					AlertDialog alertDialog = alertDialogBuilder.create();
		    					alertDialog.show();
					}
					else {
						Toast.makeText(getBaseContext(), "The File is Encrypted already", Toast.LENGTH_SHORT).show();
						finish();
					}
				}
			});	    	    	    	
	 }
	 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
        return true;
    }

}
