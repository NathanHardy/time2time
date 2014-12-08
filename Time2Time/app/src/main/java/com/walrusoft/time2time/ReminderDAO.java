package com.walrusoft.time2time;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by SirNathan on 12/7/2014.
 */
public class ReminderDAO {
    public static final String TAG = "ReminderDAO";
    private Context mContext;

    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private String[] mAllColumns = {
            DBHelper.COLUMN_REMINDER_ID, DBHelper.COLUMN_REMINDER_DATE, DBHelper.COLUMN_EVENT_FID};

    public ReminderDAO(Context context) {
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

    public Reminder createReminder(String date, long eventID) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_REMINDER_DATE, date);
        values.put(DBHelper.COLUMN_EVENT_FID, eventID);

        long insertID = mDatabase.insert(DBHelper.TABLE_REMINDERS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_REMINDERS, mAllColumns, DBHelper.COLUMN_REMINDER_ID + " = " + "'" + insertID + "'", null, null, null, null);
        cursor.moveToFirst();
        Reminder newReminder = cursorToReminder(cursor);
        cursor.close();
        return newReminder;
    }

    public void deleteReminder(Reminder reminder) {
        long ID = reminder.getID();
        mDatabase.delete(DBHelper.TABLE_REMINDERS, DBHelper.COLUMN_REMINDER_ID + " = " + ID, null);
    }

    private Reminder cursorToReminder(Cursor cursor) {
        Reminder reminder = new Reminder();
        reminder.setID(cursor.getLong(0));
        reminder.setDate(cursor.getString(1));
        long eventID = cursor.getLong(2);
        EventDAO dao = new EventDAO(mContext);
        Event event = dao.getEventByID(eventID);
        if(event != null)
            reminder.setEvent(event);

        return reminder;
    }

}
