package com.example.cryptosavior;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class History extends ListActivity {
	
	ArrayList<String> list1 = new ArrayList<String>();
	ArrayAdapter<String> adapter;	
	DatabaseAdapter dbAdapter;	
	Context context = this;
	
	TextView textView;
	TextView textView1;

	AlertDialog.Builder builder1 = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {	
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.history);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1){
 
	        @Override
	        public View getView(int position, View convertView,
	                ViewGroup parent) {
	            View view =super.getView(position, convertView, parent);
	
	            textView=(TextView) view.findViewById(android.R.id.text1);
	            //textView1=(TextView) view.findViewById(android.R.id.text2);
	            
	            textView.setHeight(30);
	            textView.setTypeface(Typeface.SERIF);
	            textView.setTextColor(Color.WHITE);
	            textView.setTextSize(15);
	
	            return view;
	        }     
        };

        dbAdapter = new DatabaseAdapter(context);        
        dbAdapter.open();
   
        int num = dbAdapter.historyFilesCount();
   
		for(int i = 1; i <= num; i++) {		
				String name = dbAdapter.getHistory(i);
				list1.add(name);
				
				//textView1.setText(dbAdapter.getTime(i));
				adapter.notifyDataSetChanged();
		}
        
        setListAdapter(adapter);               
        dbAdapter.close();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id1) {
    	super.onListItemClick(l, v, position, id1);
    	
    	final String name = list1.get(position);
    	dbAdapter.open();
		final String path = dbAdapter.getHistoryPath(position+1);
		final String type = dbAdapter.getHistoryType(position+1);
		final String time = dbAdapter.getHistoryTime(position+1);
		final String encName = dbAdapter.getHistoryEncFile(position+1);
		final String encPath = dbAdapter.getHistoryEncPath(position+1);
		final String size = dbAdapter.getHistorySize(position+1);
		
		final CharSequence[] items = {"Name - "+name,"Encrypted Time - "+time,"Path - "+path, 
							"Size - "+size+" Bytes","Mime Type - "+type,"Encrypted File name - "+encName,
							"Encrypted File path - "+encPath};
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(list1.get(position));
    	builder.setItems(items, new DialogInterface.OnClickListener() {
    		
    		public void onClick(DialogInterface dialog, int item) {

    		}

    	});
    	
    	AlertDialog alert = builder.create();
    	alert.show(); 
    	dbAdapter.close();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
    		
            case R.id.clear_history:
            	
            	dbAdapter.open();
            	dbAdapter.clearHistory();
            	dbAdapter.close();
            	finish();
            	break;
        }
        return true;
    }

}
