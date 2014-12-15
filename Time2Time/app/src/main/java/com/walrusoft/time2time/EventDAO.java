package com.walrusoft.time2time;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SirNathan on 12/7/2014.
 */
public class EventDAO {
    public static final String TAG = "EventDAO";
    private Context mContext;

    private SQLiteDatabase mDatabase;
    //create an instance of DBHelper to talk to the database
    private DBHelper mDBHelper;
    //String array contains all the column names of the Event table,
    //this is used for SQL queries involved in the creation of an Event
    private String[] mAllColumns = {
            DBHelper.COLUMN_EVENT_ID, DBHelper.COLUMN_EVENT_NAME, DBHelper.COLUMN_EVENT_DATE,
            DBHelper.COLUMN_EVENT_IMPORTANCE, DBHelper.COLUMN_EVENT_EXCITEMENT};

    public EventDAO(Context context) {
        mDBHelper = new DBHelper(context);
        this.mContext = context;

        try {
            open();
        }

        catch (SQLException e) {
            Log.e(TAG, "SQL Exception while opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    //inserts a new row in the Event table
    //values is a group of entries for a single row
    public void insert(ContentValues values) {
        //Cursor cursor = mDatabase.query(DBHelper.TABLE_EVENTS, mAllColumns, null, null, null, null, null);
        long insertID = mDatabase.insert(DBHelper.TABLE_EVENTS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_EVENTS, mAllColumns, DBHelper.COLUMN_EVENT_ID + " = " + "'"+insertID+"'", null, null, null, null);
        cursor.moveToFirst();
        cursor.close();
    }

    //creates an Event row
    public Event createEvent(String name, String date, int importance, int excitement) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_EVENT_NAME, name);
        values.put(DBHelper.COLUMN_EVENT_DATE, date);
        values.put(DBHelper.COLUMN_EVENT_IMPORTANCE, importance);
        values.put(DBHelper.COLUMN_EVENT_EXCITEMENT, excitement);

        long insertID = mDatabase.insert(DBHelper.TABLE_EVENTS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_EVENTS, mAllColumns, DBHelper.COLUMN_EVENT_ID + " = " + "'" + insertID + "'", null, null, null, null);
        cursor.moveToFirst();
        Event newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    public void deleteEvent(Event event) {
        long ID = event.getID();
        mDatabase.delete(DBHelper.TABLE_EVENTS, DBHelper.COLUMN_EVENT_ID + " = " + ID, null);
    }

    public Event getEventByID(long ID) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_EVENTS, mAllColumns, DBHelper.COLUMN_EVENT_ID + " = ? ", new String[] {String.valueOf(ID)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Event event = cursorToEvent(cursor);
        return event;
    }

    //used to populate ListView
    public List<Event> getAllEvents() {
        List<Event> listEvents = new ArrayList<Event>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_EVENTS, mAllColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Event event = cursorToEvent(cursor);
                listEvents.add(event);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listEvents;
    }

    //called in inserts and creates
    //creates a new instance of event
    //then sets ID, name, date, importance, and excitement
    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setID(cursor.getLong(0));
        event.setName(cursor.getString(1));
        event.setDate(cursor.getString(2));
        event.setImportance(cursor.getInt(3));
        event.setExcitement(cursor.getInt(4));

        return event;
    }

}
