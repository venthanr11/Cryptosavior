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
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class EncryptionService extends Service {
	
	Cipher cipher = null;
	SecretKey skey = null;
    CipherInputStream cipherIn, cipherIn1 = null;
    FileInputStream fip, fip2 = null;
    FileOutputStream fos, fos1, fop, fop1, fop2, garb = null;
    MessageDigest sha = null;
    byte[] key1 = null;
    SharedPreferences hprefs;
    SharedPreferences.Editor heditor;
    byte[] contentInBytes = null;
    final String DIR = "/mnt/sdcard/.CryptoSavior";
    final String PREC = "/Enc";
    
    final Context context = this;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
   
	    hprefs = getSharedPreferences("KEY", MODE_PRIVATE);
    	heditor = hprefs.edit();
    	final String PATH = hprefs.getString("PATH", "-1");
    	final String FILE = hprefs.getString("FILE", "-1");
  	
    	try { 
    		new DoBackgroudTask().execute(PATH, FILE);
    	}
    	catch(Exception e) {
    	}
	
		return START_STICKY;
	}
	
	private class DoBackgroudTask extends AsyncTask<String, Integer, Long> {

		protected Long doInBackground(String...str) {
			// TODO Auto-generated method stub
			final String PATH = str[0];
			final String FILE = str[1];
			
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
	    	final SecretKeySpec key = new SecretKeySpec(key1, "AES");
			
			try {
				cipher.init(Cipher.ENCRYPT_MODE, key);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				cipherIn = new CipherInputStream(new FileInputStream(new File(PATH)), cipher);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fos = new FileOutputStream(new File(DIR + PREC + FILE));
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
					
			File org = new File(PATH);
			
			try {
				garb = new FileOutputStream(new File(PATH));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String g = "** This is some garbage value to replace existing content **";
			contentInBytes = g.getBytes();

			try {
				garb.write(contentInBytes);
				garb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			org.delete();
		
			long x = 1;
			return x;
		}
		protected void onProgressUpdate(Integer...progress) {
			
		}
		protected void onPostExecute(Long result) {
			Toast.makeText(getBaseContext(), "Encryption Service over", Toast.LENGTH_LONG).show();
			new NotifyService().showNotification(context, 1);
			stopSelf();
		}	
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
