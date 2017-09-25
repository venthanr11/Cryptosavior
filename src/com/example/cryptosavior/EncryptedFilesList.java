package com.example.cryptosavior;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EncryptedFilesList extends ListActivity {	
	
	ArrayList<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter;	
	DatabaseAdapter dbAdapter;	
	Context context = this;
	
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	
	AlertDialog.Builder builder1 = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {	
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.list_enc_files);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list){
 
	        @Override
	        public View getView(int position, View convertView,
	                ViewGroup parent) {
	            View view =super.getView(position, convertView, parent);
	
	            TextView textView=(TextView) view.findViewById(android.R.id.text1);
	            
	            textView.setHeight(30);
	            textView.setTypeface(Typeface.SERIF);
	            textView.setTextColor(Color.WHITE);
	            textView.setTextSize(15);
	
	            return view;
	        }     
        };

        dbAdapter = new DatabaseAdapter(context);        
        dbAdapter.open();
        
        prefs = getSharedPreferences("KEY", MODE_PRIVATE);
        editor = prefs.edit();
   
        int num = dbAdapter.filesCount();
   
		for(int i = 1; i <= num; i++) {		
				String name = dbAdapter.getFile(i);
				list.add(name);				
				adapter.notifyDataSetChanged();
		}
        
        setListAdapter(adapter);               
        dbAdapter.close();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id1) {
    	super.onListItemClick(l, v, position, id1);
    	
    	final String name = list.get(position);
    	
    	dbAdapter.open();
		final int id = dbAdapter.getId(name);
		final String path = dbAdapter.getPath(id);
		final int size = dbAdapter.getSize(id);
		final String type = dbAdapter.getType(id);
    
    	final CharSequence[] items = {"Open", "Details", "Decrypt"};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(list.get(position));
    	builder.setItems(items, new DialogInterface.OnClickListener() {
    	
		public void onClick(DialogInterface dialog, int item) {

    		if(items[item].toString().equals("Open")) {
    			
    			decrypt(id, path);
    			
    			Intent intent = new Intent();
    			intent.setAction(Intent.ACTION_VIEW);
    			
    			editor.putInt("ENCVALUE", 1);
				editor.commit();
    			
    			intent.setDataAndType(Uri.fromFile(new File(path)), type);
    			startActivity(intent);
    			
    			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    					context);

    				alertDialogBuilder.setTitle(name);

    				alertDialogBuilder
    					.setMessage(name + " is Decrypted. Do you want to encrypt the File again?")
    					.setCancelable(false)
    					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog,int id) {
    							encrypt(path);
    						}
    					  })
    					.setNegativeButton("No",new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog,int id) {
    							dialog.cancel();
    							finish();
    						}
    					});

    					AlertDialog alertDialog = alertDialogBuilder.create();
    					alertDialog.show();
    		}
    		else if(items[item].toString().equalsIgnoreCase("Details")) {
    			
    			final CharSequence[] items = {"Name - "+name, "Path - "+path, "Size - "+size+" bytes","Type - "+type};
    	    	builder1 = new AlertDialog.Builder(context);
    	    	builder1.setTitle("Details");
    	    	builder1.setItems(items, new DialogInterface.OnClickListener() {
    	    		
    	    		public void onClick(DialogInterface dialog, int item) {

    	    		}

    	    	});
    	    	AlertDialog alert1 = builder1.create();
    	    	alert1.show();
    				
    		}
    		else if(items[item].toString().equals("Decrypt")) {
    			
    			decrypt(id, path);			
    		}
    	}
    	});

    	AlertDialog alert = builder.create();
    	alert.show();   	  	
    }
    
	public void decrypt(int id, String path) {
	    
	    SharedPreferences hprefs; 		    
	    SharedPreferences.Editor heditor;
	    hprefs = getSharedPreferences("KEY", MODE_PRIVATE);
	    heditor = hprefs.edit();
	    
    	final String encPath = dbAdapter.getEncPath(id);   	
    	
    	heditor.putString("ENCPATH", encPath);
    	heditor.putString("PATH_DEC", path);
    	heditor.commit();

    	startService(new Intent(getBaseContext(), DecryptionService.class));
    	
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		alertDialogBuilder.setTitle("CryptoSavior");

		alertDialogBuilder
			.setMessage("The File is processed by the Decryption Engine. You will be notified when it is over.")
			.setCancelable(false)
			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
					finish();
				}
			  });

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
																			
		dbAdapter.fileDelete(path);
		dbAdapter.encFileDelete(encPath);
		
		dbAdapter.reArrangeFiles();
		dbAdapter.reArrangeEncFiles();  	
    }
    
	public void encrypt(String path) {
    	
    	Cipher cipher = null;
        CipherInputStream cipherIn = null;
        FileOutputStream fos = null;
        FileOutputStream garb = null;
        SecretKeySpec key = null;
        MessageDigest sha = null;
        byte[] key1 = null;
        
        SharedPreferences hprefs;      
        hprefs = getSharedPreferences("KEY", MODE_PRIVATE);
        
        byte[] contentInBytes = null;
        final String dir = "/mnt/sdcard/.CryptoSavior";
        final String file = new File(path).getName();
        
        try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    	
		try {
			String masterKey = hprefs.getString("key", "");
			key1 = masterKey.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	key1 = sha.digest(key1);
    	key1 = Arrays.copyOf(key1, 16); // use only first 128 bit
    	key = new SecretKeySpec(key1, "AES"); 
    	
    	
    	FileNameMap fileNameMap = URLConnection.getFileNameMap();
	    String mime = fileNameMap.getContentTypeFor(path);
		long size = new File(path).length();
		dbAdapter.fileInsert(path, file, size, mime);

		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			cipherIn = new CipherInputStream(new FileInputStream(new File(path)), cipher);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		try {
			fos = new FileOutputStream(new File(dir + "/Enc" + file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		int i;
		try {
			while((i=cipherIn.read())!=-1){
			fos.write(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipherIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		File org = new File(path);
		
		try {
			garb = new FileOutputStream(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String g = "Garbage Value";
		contentInBytes = g.getBytes();
	
		try {
			garb.write(contentInBytes);
			garb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		org.delete();
		
		String encPath = dir+"/Enc"+file;
		String encName = "Enc"+file;
		long encSize = new File(dir+"/Enc"+file).length();
		dbAdapter.encFileInsert(encPath, encName, encSize);
		
		dbAdapter.close();
		Toast.makeText(getApplicationContext(), "File Encrypted Successfully", Toast.LENGTH_SHORT).show();								

    }
}
