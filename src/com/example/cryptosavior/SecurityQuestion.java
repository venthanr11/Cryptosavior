package com.example.cryptosavior;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecurityQuestion extends Activity {
	
	EditText ques;
	EditText ans;
	Button submit;
	
	SharedPreferences sqPrefs;
	SharedPreferences.Editor sqeditor;
	
	DatabaseAdapter dbAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_question);
        
        ques = (EditText) findViewById(R.id.questionTextBox);
    	ans = (EditText) findViewById(R.id.answerTextBox);
    	submit = (Button) findViewById(R.id.submitSQ);
    	
    	sqPrefs = getSharedPreferences("KEY", MODE_PRIVATE);
    	sqeditor = sqPrefs.edit();
    	
    	dbAdapter = new DatabaseAdapter(getApplicationContext());
    	
    	submit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String question = ques.getText().toString();
				String answer = ans.getText().toString();
				
				if(question.length() != 0 && answer.length() != 0) {
				dbAdapter.open();
				long x = dbAdapter.putQA(question, answer);
				
				if(x>0) {
					Toast.makeText(getApplicationContext(), "User Successfully Added", Toast.LENGTH_LONG).show();
					Intent i = new Intent();
					i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.PreHome");
					startActivity(i);
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), "User Addition Unsuccessful", Toast.LENGTH_LONG).show();
					dbAdapter.deleteKey();
					sqeditor.clear();
					sqeditor.commit();
					Intent i = new Intent();
					i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.SignUp");
					startActivity(i);
					finish();
				}
				dbAdapter.close();
				}
				else
					Toast.makeText(getApplicationContext(), "Invalid Data Entered", Toast.LENGTH_LONG).show();


			}
		});
	}
	
	@Override
	public void onBackPressed() {
	    	//super.onBackPressed();
		Toast.makeText(getApplicationContext(), "Please Enter a Security Question", Toast.LENGTH_LONG).show();
		/*sqeditor.clear();
		sqeditor.commit();
		Intent i = new Intent();
		i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.MainActivity");
		startActivity(i);  */	
	}	

}
