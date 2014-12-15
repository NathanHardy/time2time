package com.walrusoft.time2time;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SirNathan on 12/14/2014.
 */
public class AdapterListReminders extends BaseAdapter {
    public static final String TAG = "AdapterListReminders";
    private List<Reminder> mItems;
    private LayoutInflater mInflater;
    public AdapterListReminders(Context context, List<Reminder> listReminders) {
        this.setItems(listReminders);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Reminder getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getID() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if(v == null) {
            v = mInflater.inflate(R.layout.list_item_reminder, parent, false);
            holder = new ViewHolder();
            holder.tvReminderDate = (TextView) v.findViewById(R.id.tvReminderDate);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

// fill row data
        Reminder currentItem = getItem(position);
        if(currentItem != null) {
            holder.tvReminderDate.setText(currentItem.getDate());
        }
        return v;
    }

    public List<Reminder> getItems() {return mItems;
    }

    public void setItems(List<Reminder> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView tvReminderDate;
    }



}
