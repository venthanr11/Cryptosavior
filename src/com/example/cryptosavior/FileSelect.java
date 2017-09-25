package com.example.cryptosavior;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class FileSelect extends ListActivity {
	
	private File currentDir;
	private FileArrayAdapter adapter;

  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      currentDir = Environment.getExternalStorageDirectory();
      fill(currentDir);
  }
  
  @Override
  public void onBackPressed() {
  		super.onBackPressed();
  	Intent intent = new Intent();
  	intent.setClassName("com.example.cryptosavior", "com.example.cryptosavior.home");
  	startActivity(intent);
  }
  
  @Override
  public void onStop() {
  		super.onStop();
  	finish();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_main, menu);
      return true;
  }
  private void fill(File f)
  {
  	File[]dirs = f.listFiles();
  	
  	         this.setTitle("Current Dir: "+f.getName());
  	         List<Option>dir = new ArrayList<Option>();
               List<Option>fls = new ArrayList<Option>();
  	
  	         try{
  	             for(File ff: dirs)
  	             {
  	                if(ff.isDirectory())
  	
  	                    dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
  	                else           	
  	                {
  	
          fls.add(new Option(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
  	                }
  	             }
  	
  	         }catch(Exception e)
  	
  	         {
  	         }
  	
  	         Collections.sort(dir);
  	
  	         Collections.sort(fls);
      	         dir.addAll(fls);
  	
  	         if(!f.getName().equalsIgnoreCase("sdcard"))
  	
  	             dir.add(0,new Option("..","Parent Directory",f.getParent()));
  	         adapter = new FileArrayAdapter(FileSelect.this,R.layout.file_view,dir);
  	         this.setListAdapter(adapter);

  	 }
  
  
  
  @Override
          protected void onListItemClick(ListView l, View v, int position, long id) {
              // TODO Auto-generated method stub
              super.onListItemClick(l, v, position, id);
              Option o = adapter.getItem(position);
              if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
                      currentDir = new File(o.getPath());
                      fill(currentDir);
              }
              else
              {
                  onFileClick(o);
              }
          }
          private void onFileClick(Option o)
          {
        	  Intent i3 = new Intent();
        	  i3.setClassName("com.example.cryptosavior", "com.example.cryptosavior.HomeFileSelected");
        	  i3.putExtra("FileName", o.getName());
        	  i3.putExtra("Path", o.getPath());
        	  startActivity(i3);
             //Toast.makeText(this, "File Clicked: "+o.getName(), Toast.LENGTH_SHORT).show();
  
      }

}
