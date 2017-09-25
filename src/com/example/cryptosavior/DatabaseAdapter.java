package com.example.cryptosavior;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
	
	SQLiteDatabase database;
	DatabaseOpenHelper dbHelper;
	String str;
	String str2;
	MessageDigest digest;
	MessageDigest digest1;
	MessageDigest digest2;
	
	public DatabaseAdapter(Context context) {
		
		dbHelper = new DatabaseOpenHelper(context);
		
	}
	
	public void open() {
		
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		
		database.close();
	}
	
	public long insert(String key) {
		
		try {	
		digest = MessageDigest.getInstance("SHA-512");
		digest.reset();
		String salt = "e33ptcbnto8wo8c4o48kwws0g8ksck0";
		String mk = key + salt;
		digest.update(mk.getBytes());
		byte[] a = digest.digest();
		
		int len = a.length;
		StringBuilder sb = new StringBuilder(len << 1);
		for (int i = 0; i < len; i++) {
		sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
		sb.append(Character.forDigit(a[i] & 0x0f, 16));				
			}
		str = sb.toString();
		}
		catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
			}
		
		ContentValues values = new ContentValues();
		values.put("mykey", str);
		return database.insert("keystore", "", values);
	}
	
	public Boolean login(String key) {
			
		try {	
		digest1 = MessageDigest.getInstance("SHA-512");
		digest1.reset();
		String salt = "e33ptcbnto8wo8c4o48kwws0g8ksck0";
		String mk = key + salt;
		digest1.update(mk.getBytes());
		byte[] a = digest1.digest();
		
		int len = a.length;
		StringBuilder sb1 = new StringBuilder(len << 1);
		for (int i = 0; i < len; i++) {
		sb1.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
		sb1.append(Character.forDigit(a[i] & 0x0f, 16));				
			}
		str = sb1.toString();
		Cursor c = database.rawQuery("select * from keystore", null);
		if(c.getCount() != 0)
			if(c.moveToFirst())
				str2 = c.getString(c.getColumnIndex("mykey"));		
		}
		catch (NoSuchAlgorithmException e) { 
			e.printStackTrace(); 
			}
		if(str.compareTo(str2) == 0)
			return true;
		else
			return false;
	}
	
	public boolean checkdb() {
		
		Cursor c = database.rawQuery("SELECT * FROM keystore" , null);
	    if (!c.moveToFirst())
	    {
	        return false;
	    }
	    else {
	    return true;
	    }
		
	}
	
	public boolean checkFile(String name) {
		
		Cursor c1 = database.rawQuery("SELECT * FROM Files where name =\""+name+"\"", null);
		if(c1.getCount() == 0) 
			return true;
		else 
			return false;
	}
	
	public long fileInsert(String path, String name, long size, String mime) {
		
			ContentValues cv = new ContentValues();
			cv.put("path", path);
			cv.put("name", name);
			cv.put("size", size);
			cv.put("mime", mime);
			return database.insert("Files", "", cv);
	}

	public long encFileInsert(String path, String name, long size) {
		
		ContentValues cv = new ContentValues();
		cv.put("encPath", path);
		cv.put("encName", name);
		cv.put("encSize", size);
		return database.insert("EncFiles", "", cv);
	}
	
	public long historyFileInsert(String path, String name, long size, String mime, String encPath, String encName, long encSize, String time) {
		
		ContentValues cv = new ContentValues();
		cv.put("path", path);
		cv.put("name", name);
		cv.put("size", size);
		cv.put("mime", mime);
		cv.put("encPath", encPath);
		cv.put("encName", encName);
		cv.put("encSize", encSize);
		cv.put("time", time);
		return database.insert("History", "", cv);
}
	
	public void fileDelete(String path1) {
		
		database.delete("Files", "path=?", new String[] {path1});
		
	}
	
	public void encFileDelete(String path1) {
		
		database.delete("EncFiles", "encPath=?", new String[] {path1});
		
	}
	
	public void deleteKey() {
		
		database.delete("keystore", "_id=1", null);
		
	}
	
	public int filesCount() {
		
		Cursor c3 = database.rawQuery("SELECT * FROM Files", null);
		return c3.getCount();
		
	}
	
	public void clearHistory() {
		
		database.delete("History", null, null);
	}
	
	public int getId(String name) {
		
		int id = -10;
		Cursor c4 = database.rawQuery("SELECT * FROM Files where name=\""+name+"\"", null);
		if(c4.moveToFirst()) {
			//id = Integer.parseInt(c4.getString(c4.getColumnIndex("_id")));
			id = c4.getInt(c4.getColumnIndex("_id"));
		}
		return id;	
	}
	
	public String getPath(int id) {
		
		String path = "";
		Cursor c5 = database.rawQuery("SELECT * FROM Files where _id=\""+id+"\"", null);
		if(c5.moveToFirst()) {
			path = c5.getString(c5.getColumnIndex("path"));
		}
		return path;	
	}
	
	public String getFile(int id) {
		
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM Files where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("name"));
		}
		return file;	
	}
	
	public int getSize(int id) {
		
		int size = 0;
		Cursor c7 = database.rawQuery("SELECT * FROM Files where _id=\""+id+"\"", null);
		if(c7.moveToFirst()) {
			size = Integer.parseInt(c7.getString(c7.getColumnIndex("size")));
		}
		return size;	
	}
	
	public String getType(int id) {
		
		String type = "";
		Cursor c8 = database.rawQuery("SELECT * FROM Files where _id=\""+id+"\"", null);
		if(c8.moveToFirst()) {
			type = c8.getString(c8.getColumnIndex("mime"));
		}
		return type;	
	}
	
	public String getTime(int id) {
		
		String type = "";
		Cursor c8 = database.rawQuery("SELECT * FROM History where _id=\""+id+"\"", null);
		if(c8.moveToFirst()) {
			type = c8.getString(c8.getColumnIndex("time"));
		}
		return type;	
	}
	
	public String getEncPath(int id) {
		
		String path = "";
		Cursor c5 = database.rawQuery("SELECT * FROM EncFiles where _id=\""+id+"\"", null);
		if(c5.moveToFirst()) {
			path = c5.getString(c5.getColumnIndex("encPath"));
		}
		return path;	
	}
	
	public String getEncFile(int id) {
		
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM EncFiles where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("EncName"));
		}
		return file;	
	}
	
	public int historyFilesCount() {
		
		Cursor c3 = database.rawQuery("SELECT * FROM History", null);
		return c3.getCount();
		
	}
	
	public String getHistory(int id) {
		
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM History where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("name"));
		}
		return file;

	}
	
	public String getHistoryTime(int id) {
		
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM History where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("time"));
		}
		return file;

	}

	public String getHistorySize(int id) {
	
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM History where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("size"));
		}
		return file;

	}

	public String getHistoryPath(int id) {
	
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM History where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("path"));
		}
		return file;

	}

	public String getHistoryEncFile(int id) {
	
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM History where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("encName"));
		}
		return file;

	}

	public String getHistoryEncPath(int id) {
	
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM History where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("encPath"));
		}
		return file;

	}

	public String getHistoryType(int id) {
	
		String file = "";
		Cursor c6 = database.rawQuery("SELECT * FROM History where _id=\""+id+"\"", null);
		if(c6.moveToFirst()) {
			file = c6.getString(c6.getColumnIndex("mime"));
		}
		return file;

	}
	
	public void reArrangeFiles() {
		
		ContentValues cv = new ContentValues();
		Cursor cursor = database.rawQuery("SELECT * FROM Files", null);
		String name;
		int i = 1;
		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			name = cursor.getString(cursor.getColumnIndex("name"));
			cv.put("_id", i);
			database.update(DatabaseOpenHelper.FILES_TABLE_NAME, cv, "name=?", new String[] {name});
			i++;
		}
	}
	
	public void reArrangeEncFiles() {
		
		ContentValues cv = new ContentValues();
		Cursor cursor = database.rawQuery("SELECT * FROM EncFiles", null);
		String encName;
		int i = 1;
		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			encName = cursor.getString(cursor.getColumnIndex("encName"));
			cv.put("_id", i);
			database.update(DatabaseOpenHelper.ENC_FILES_TABLE_NAME, cv, "encName=?", new String[] {encName});
			i++;
		}
	}
	
	public long putQA(String ques, String ans) {
		
		ContentValues cv = new ContentValues();
		cv.put("question", ques);
		cv.put("answer", ans);
		return database.insert("secQues", "", cv);
	}
	
	public String getQues() {
		
		String s = "";
		Cursor c = database.rawQuery("SELECT * FROM secQues where _id=\"1\"", null);
		if(c.moveToFirst()) {
			s = c.getString(c.getColumnIndex("question"));
		}
		return s;
	}
	
	public String getAns() {
		
		String s = "";
		Cursor c = database.rawQuery("SELECT * FROM secQues where _id=\"1\"", null);
		if(c.moveToFirst()) { 
			s = c.getString(c.getColumnIndex("answer"));
		}
		return s;
	}
	
	public boolean checkSQ() {
		
		Cursor c1 = database.rawQuery("SELECT * FROM secQues", null);
		if(c1.getCount() == 0) 
			return true;
		else 
			return false;
	}
	
}
