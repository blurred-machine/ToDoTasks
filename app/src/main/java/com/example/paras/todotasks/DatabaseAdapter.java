package com.example.paras.todotasks;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by paras on 5/5/2018.
 */

public class DatabaseAdapter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Task.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "TodoTasks";

    public static final String ID = "_id";
    public static final String TITLE = "Title";
    public static final String DESCRIPTION = "Description";
    public static final String DATE = "Date";
    public static final String STATUS = "Status";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" VARCHAR(255), "+DESCRIPTION+" VARCHAR(255), "+DATE+" VARCHAR(255), "+STATUS+" INTEGER DEFAULT 0);";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME+";";

    public static Context context;

    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Log.i("paras", "inside constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
            Log.i("paras", "inside create");
        }catch (SQLException e){
            Log.i("paras", "ERR");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            Log.i("paras", "inside upgrade");
            onCreate(sqLiteDatabase);
        }catch (SQLException e){
            Log.i("paras", "ERR");
        }
    }
}
