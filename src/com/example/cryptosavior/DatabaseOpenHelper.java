package com.example.cryptosavior;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
	
	public static final String DB_NAME = "keys.db";
	public static final String TABLE_NAME = "keystore";
	public static final String FILES_TABLE_NAME = "Files";
	public static final String HISTORY_TABLE_NAME = "History";
	public static final String ENC_FILES_TABLE_NAME = "EncFiles";
	public static final String SEC_QUES_TABLE_NAME = "secQues";
	
	public static final String TABLE_SQL = "Create table "+ TABLE_NAME 
											+"( _id INTEGER PRIMARY KEY ,"
											+"mykey TEXT );";
	
	public static final String FILES_TABLE = "Create table " + FILES_TABLE_NAME
											+"( _id INTEGER PRIMARY KEY ,"
											+"path TEXT ," 
											+"name TEXT ," 
											+"size INTEGER ,"
											+"mime TEXT"
											+");";
	
	public static final String HISTORY = "Create table " + HISTORY_TABLE_NAME
											+"( _id INTEGER PRIMARY KEY ,"
											+"path TEXT ," 
											+"name TEXT ," 
											+"size INTEGER ,"
											+"mime TEXT ,"
											+"encPath TEXT ,"
											+"encName TEXT ,"
											+"encSize INTEGER ,"
											+"time DATETIME"
											+");";
	
	public static final String ENC_FILES_TABLE = "Create table " + ENC_FILES_TABLE_NAME
												+"( _id INTEGER PRIMARY KEY ,"
												+"encPath TEXT ,"
												+"encName TEXT ,"
												+"encSize INTEGER"
												+");";

	public static final String SECURITY_QUESTION_TABLE = "Create table "+ SEC_QUES_TABLE_NAME
														  +"( _id INTEGER PRIMARY KEY ,"
														  +"question TEXT ,"
														  +"answer TEXT );";
	public DatabaseOpenHelper(Context context) {
		super(context, DB_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(TABLE_SQL);
		database.execSQL(FILES_TABLE);
		database.execSQL(ENC_FILES_TABLE);
		database.execSQL(SECURITY_QUESTION_TABLE);
		database.execSQL(HISTORY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
