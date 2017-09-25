package com.example.cryptosavior;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class DecryptionService extends Service {
	
	Cipher cipher = null;
    CipherInputStream cipherIn1 = null;
    FileOutputStream fos1 = null;
    SecretKeySpec key = null;
    MessageDigest sha = null;
    byte[] key1 = null;
    SharedPreferences hprefs; 
    SharedPreferences.Editor heditor;
    
    final Context context = this;
    

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		hprefs = getSharedPreferences("KEY", MODE_PRIVATE);
		final String path = hprefs.getString("PATH_DEC", "-1");
		final String encPath = hprefs.getString("ENCPATH", "-1");
		
		try { 
    		new DoBackgroudTask().execute(path, encPath);
    	}
    	catch(Exception e) {
    	}
	
		return START_STICKY;
	}
	
	private class DoBackgroudTask extends AsyncTask<String, Integer, Long> {

		protected Long doInBackground(String... str) {
			// TODO Auto-generated method stub
			
			final String path = str[0];
			final String encPath = str[1];
			
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
			
			try {
				cipher.init(Cipher.DECRYPT_MODE, key);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
			
			try {						
				cipherIn1 = new CipherInputStream(new FileInputStream(new File(encPath)), cipher);					
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				
				fos1 = new FileOutputStream(new File(path));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			int i1;
			try {
				while((i1=cipherIn1.read())!=-1){
				fos1.write(i1);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				cipherIn1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			new File(encPath).delete();

			return null;
		}
		
		protected void onProgressUpdate(Integer...progress) {
			
		}
		protected void onPostExecute(Long result) {
			Toast.makeText(getBaseContext(), "Decryption Service over", Toast.LENGTH_LONG).show();
			new NotifyService().showNotification(context, 0);
			stopSelf();
		}	
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
