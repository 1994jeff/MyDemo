package com.example.snake.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDataBase extends SQLiteOpenHelper {
	
	private static final String CREATE_SCORE = "create table if not exists score(" +
			"id integer primary key  autoincrement," +
			"name varchar," +
			"score integer" +
			")";

	public GameDataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SCORE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
