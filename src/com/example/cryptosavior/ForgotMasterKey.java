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

public class ForgotMasterKey extends Activity {
	
	EditText ques;
	EditText ans;
	Button submit;
	
	SharedPreferences fmkPrefs;
	SharedPreferences.Editor fmkeditor;
	
	DatabaseAdapter dbAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_masterkey);
        
        ques = (EditText) findViewById(R.id.fmkquestionTextBox);
    	ans = (EditText) findViewById(R.id.fmkanswerTextBox);
    	submit = (Button) findViewById(R.id.fmksubmitSQ);
    	
    	fmkPrefs = getSharedPreferences("KEY", MODE_PRIVATE);
    	fmkeditor = fmkPrefs.edit();
    	
    	dbAdapter = new DatabaseAdapter(getApplicationContext());
    	
    	submit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String question = ques.getText().toString();
				String answer = ans.getText().toString();
				
				question = question.toLowerCase();
				question = question.replace(" ", "");
				
				answer = answer.toLowerCase();
				answer = answer.replace(" ", "");
				
				dbAdapter.open();
				String dbQuestion = dbAdapter.getQues();
				dbQuestion = dbQuestion.toLowerCase();
				dbQuestion = dbQuestion.replace(" ", "");
				
				String dbAnswer = dbAdapter.getAns();
				dbAnswer = dbAnswer.toLowerCase();
				dbAnswer = dbAnswer.replace(" ", "");
				
				if(question.compareTo(dbQuestion) == 0 && answer.compareTo(dbAnswer) == 0)
				{
					dbAdapter.deleteKey();
					Intent i = new Intent();
					i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.SignUp");
					startActivity(i);
				}
				else {
					Toast.makeText(getApplicationContext(), "Security Question Mismatch", Toast.LENGTH_LONG).show();
					Intent i = new Intent();
					i.setClassName("com.example.cryptosavior", "com.example.cryptosavior.MainActivity");
					startActivity(i);
				}
				dbAdapter.close();
						
			}
		});
	}
	
	@Override
    public void onStop() {
		super.onStop();
	   	finish();
	}
	
	@Override
	public void onBackPressed() {
	    	//super.onBackPressed();
		Intent i2 = new Intent();
		i2.setClassName("com.example.cryptosavior", "com.example.cryptosavior.MainActivity");
		startActivity(i2);
	    finish();
	    	
	}

}
