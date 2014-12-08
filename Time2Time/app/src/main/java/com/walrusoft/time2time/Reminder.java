package com.walrusoft.time2time;

/**
 * Created by SirNathan on 12/7/2014.
 */
public class Reminder {
    private long mRID;
    private String mReminderDate;
    private Event mEvent;

    public Reminder() {}

    public Reminder(String date) {
        this.mReminderDate = date;
    }

    public long getID() {return mRID;}
    public void setID(long RID) {this.mRID = RID;}

    public String getDate() {return mReminderDate;}
    public void setDate(String date) {this.mReminderDate = date;}

    public Event getEvent() {return mEvent;}
    public void setEvent(Event event) {this.mEvent = event;}


}
