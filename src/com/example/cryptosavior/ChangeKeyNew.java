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

import android.annotation.TargetApi;
import android.app.Activity;
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

public class ChangeKeyNew extends Activity {
	
	EditText cknmk;
	Button cknone,ckntwo,cknthree,cknfour,cknfive,cknsix,cknseven,ckneight,cknnine,cknzero,cknlogin,ckndel;
	DatabaseAdapter dbAdapter;
	SharedPreferences cknprefs;
	SharedPreferences.Editor ckneditor;
	String path = null;
	String fileName = null;	
	
	Cipher cipher = null;
	SecretKey skey = null;
    SecretKeySpec skeySpec = null;
    CipherInputStream cipherIn, cipherIn1 = null;
    FileInputStream fip, fip2 = null;
    FileOutputStream fos, fos1, fop, fop1, fop2 = null;
    SecretKeySpec key = null;
    MessageDigest sha = null;
    byte[] key1 = null;  
    
    Cipher cipher4 = null;
	SecretKey skey4 = null;
    SecretKeySpec skeySpec4 = null;
    CipherInputStream cipherIn4, cipherIn14 = null;
    FileInputStream fip4, fip24 = null;
    FileOutputStream fos4, fos14, fop4, fop14, fop24 = null;
    SecretKeySpec key4 = null;
    MessageDigest sha4 = null;
    byte[] key14 = null;
	
	@TargetApi(9)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_key_new);
        
        cknmk =(EditText) findViewById(R.id.cknmk);
        cknlogin =(Button) findViewById(R.id.cknlogin);
        cknone =(Button) findViewById(R.id.cknone);
        ckntwo =(Button) findViewById(R.id.ckntwo);
        cknthree =(Button) findViewById(R.id.cknthree);
        cknfour =(Button) findViewById(R.id.cknfour);
        cknfive =(Button) findViewById(R.id.cknfive);
        cknsix =(Button) findViewById(R.id.cknsix);
        cknseven =(Button) findViewById(R.id.cknseven);
        ckneight =(Button) findViewById(R.id.ckneight);
        cknnine =(Button) findViewById(R.id.cknnine);
        cknzero =(Button) findViewById(R.id.cknzero);
        ckndel =(Button) findViewById(R.id.ckndel);
        cknprefs = getSharedPreferences("KEY", MODE_PRIVATE);
        ckneditor = cknprefs.edit();
        dbAdapter = new DatabaseAdapter(getApplicationContext());
       
        cknmk.setOnTouchListener(new OnTouchListener(){	
			
		    public boolean onTouch(View v, MotionEvent event) {

		        int inType = cknmk.getInputType(); // backnup the input type
		        cknmk.setInputType(InputType.TYPE_NULL); // disable soft input
		        cknmk.onTouchEvent(event); // call native handler
		        cknmk.setInputType(inType); // restore input type
		        return true; // consume touch even
		    }
		});
		
		cknone.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(1);
				
			}
		});
        
        ckntwo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(2);
				
			}
		});
        
        cknthree.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(3);
				
			}
		});
        
        cknfour.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(4);
				
			}
		});
        
        cknfive.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(5);
				
			}
		});
        
        cknsix.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(6);
				
			}
		});
        
        cknseven.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(7);
				
			}
		});
        
        ckneight.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(8);
				
			}
		});
        
        cknnine.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(9);
				
			}
		});
        
        cknzero.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cknupdate(0);
				
			}
		});
        
		ckndel.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int len = String.valueOf(cknmk.getText().toString()).length();
						
						if(len <= 1) {
							cknmk.setText(null);
						}
						else {
							String str = cknmk.getText().toString();
							String newStr = str.substring(0, len-1);
							cknmk.setText(newStr);
						}
						
					}
				});
		
		cknlogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String key3 = cknmk.getText().toString();
				dbAdapter.open();
				dbAdapter.deleteKey();
				
				int num = dbAdapter.filesCount();
				for(int j = 1; j <= num; j++) {
					path = dbAdapter.getEncPath(j);
					fileName = dbAdapter.getFile(j);
					
					decrypt(path, fileName);

				}
				ckneditor.clear();
	    		ckneditor.commit();
															
				long x = dbAdapter.insert(key3);
				ckneditor.putString("key", key3);
			    ckneditor.commit();
				if(x > 0) {
				    
				    for(int k = 1; k <= num; k++) {
						path = dbAdapter.getEncPath(k);
						fileName = dbAdapter.getFile(k);
					
						encrypt(path, fileName);
						
					}
				    Toast.makeText(getApplicationContext(), "MasterKey Successfully Changed", Toast.LENGTH_LONG).show();
				    finish();
				}
				else 
					Toast.makeText(getApplicationContext(), "MasterKey Change Unsuccessful", Toast.LENGTH_LONG).show();
				dbAdapter.close();
			}
			
		});
	}
        
		
	public void cknupdate(int val) {
	    	
		int len = String.valueOf(cknmk.getText().toString()).length();		
		if(len == 0) {
			cknmk.setText(String.valueOf(val));
		}
		else {
			String s = cknmk.getText().toString();
			s = s + val;
			cknmk.setText(s);
		}
	}
	
	@TargetApi(9)
	public void decrypt (String path, String file) {
	
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
			String masterKey = cknprefs.getString("key", "");
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
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		try {
			cipherIn1 = new CipherInputStream(new FileInputStream(new File(path)), cipher);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {		
			fos1 = new FileOutputStream(new File("/mnt/sdcard/.CryptoSavior/temp"+file));
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
																
		File dec = new File(path);
		dec.delete();
		//Toast.makeText(getApplicationContext(), "File Decrypted Successfully", Toast.LENGTH_SHORT).show();		
	}
	
	@TargetApi(9)
	public void encrypt (String path, String file) {		

	    try {
			cipher4 = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    	
		try {
			String masterKey = cknprefs.getString("key", "");
			key14 = masterKey.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
		try {
			sha4 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	key14 = sha.digest(key14);
    	key14 = Arrays.copyOf(key14, 16); // use only first 128 bit
    	key4 = new SecretKeySpec(key14, "AES");

    	String tempPath = "/mnt/sdcard/.CryptoSavior/temp" + file;
    	
    	try {
			cipher4.init(Cipher.ENCRYPT_MODE, key4);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			cipherIn4 = new CipherInputStream(new FileInputStream(new File(tempPath)), cipher4);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fos4 = new FileOutputStream(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int i;
		try {
			while((i=cipherIn4.read())!=-1){
			fos4.write(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipherIn4.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos4.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		File enc = new File(tempPath);
		enc.delete();			
		//Toast.makeText(getApplicationContext(), "File Encrypted Successfully", Toast.LENGTH_SHORT).show();								
	}
	    
	@Override
    public void onStop() {
		super.onStop();
	   	finish();
	}

}
