package com.walrusoft.time2time;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SirNathan on 12/8/2014.
 */
public class AdapterListEvents extends BaseAdapter {
    public static final String TAG = "AdapterListEvents";
    private List<Event> mItems;
    private LayoutInflater mInflater;
    public AdapterListEvents(Context context, List<Event> listEvents) {
        this.setItems(listEvents);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Event getItem(int position) {
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
            v = mInflater.inflate(R.layout.list_item_event, parent, false);
            holder = new ViewHolder();
            holder.tvEventName = (TextView) v.findViewById(R.id.tvEventName);
            holder.tvEventDate = (TextView) v.findViewById(R.id.tvEventDate);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

// fill row data
        Event currentItem = getItem(position);
        if(currentItem != null) {
            holder.tvEventName.setText(currentItem.getName());
            holder.tvEventDate.setText(currentItem.getDate());
        }
        return v;
    }

    public List<Event> getItems() {return mItems;
    }

    public void setItems(List<Event> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView tvEventName;
        TextView tvEventDate;
    }



}
