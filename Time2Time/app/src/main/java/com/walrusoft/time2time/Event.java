package com.walrusoft.time2time;

/**
 * Created by SirNathan on 12/7/2014.
 */
public class Event {
    private long mID;
    private String mEventName;
    private String mEventDate;
    private int mImportance;
    private int mExcitement;

    public Event() {}

    public Event(String name, String date, int importance, int excitement) {
        this.mEventName = name;
        this.mEventDate = date;
        this.mImportance = importance;
        this.mExcitement = excitement;
    }

    public long getID() {return mID;}
    public void setID(long ID) {this.mID = ID;}

    public String getName() {return mEventName;}
    public void setName(String name) {this.mEventName = name;}

    public String getDate() {return mEventDate;}
    public void setDate(String date) {this.mEventDate = date;}

    public int getImportance() {return mImportance;}
    public void setImportance(int importance) {this.mImportance = importance;}

    public int getExcitement() {return mExcitement;}
    public void setExcitement(int excitement) {this.mExcitement = excitement;}


}
