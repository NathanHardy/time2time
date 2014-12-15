package com.walrusoft.time2time;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by SirNathan on 12/7/2014.
 */
public class DBHelper extends SQLiteOpenHelper {
    //table and column names for Events table
    public static final String TABLE_EVENTS = "Events";
    public static final String COLUMN_EVENT_ID = "_EID";
    public static final String COLUMN_EVENT_NAME = "Name";
    public static final String COLUMN_EVENT_DATE = "Date";
    public static final String COLUMN_EVENT_IMPORTANCE = "Importance";
    public static final String COLUMN_EVENT_EXCITEMENT = "Excitement";

    //table and column names for Reminders table
    public static final String TABLE_REMINDERS = "Reminders";
    public static final String COLUMN_REMINDER_ID = COLUMN_EVENT_ID;
    //public static final String COLUMN_EVENT_FID = "_EID";
    public static final String COLUMN_REMINDER_DATE = "Date";
    public static final String COLUMN_REMINDER_EVENT_ID = "event_id";

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    // builds a String to create Events table
    private static final String SQL_CREATE_TABLE_EVENTS = "CREATE TABLE "
            + TABLE_EVENTS
            + "( "
            + COLUMN_EVENT_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_EVENT_NAME + " TEXT NOT NULL, "
            + COLUMN_EVENT_DATE + " DATE, "
            + COLUMN_EVENT_IMPORTANCE + " INTEGER, "
            + COLUMN_EVENT_EXCITEMENT + " INTEGER "
            + ");";

    // builds a String to create Reminders table
    private static final String SQL_CREATE_TABLE_REMINDERS = "CREATE TABLE "
            + TABLE_REMINDERS
            + "( "
            + COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_REMINDER_DATE + " DATE, "
            + COLUMN_REMINDER_EVENT_ID + " INTEGER NOT NULL "
            + ");";
            /*
            + "FOREIGN KEY ( " + COLUMN_EVENT_FID + ") REFERENCES " + TABLE_EVENTS
                    + " (" + COLUMN_EVENT_ID + "));";
            */

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // runs the above Strings as SQL commands to create Events and Reminders tables
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_EVENTS);
        database.execSQL(SQL_CREATE_TABLE_REMINDERS);
    }

    // drops tables and then runs OnCreate()
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        onCreate(database);
    }

    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



}
