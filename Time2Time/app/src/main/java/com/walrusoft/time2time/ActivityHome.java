package com.walrusoft.time2time;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class ActivityHome extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final int REQUEST_CODE_ADD_EVENT = 40;
    public static final String EXTRA_ADDED_EVENT = "extra_key_added_event";
    private ListView mListViewEvents;
    private TextView mTVEmptyListEvents;
    private AdapterListEvents mAdapter;
    private List<Event> mListEvents;
    private EventDAO mEventDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        initViews();

        mEventDAO = new EventDAO(this);
        mListEvents = mEventDAO.getAllEvents();
        if (mListEvents != null && !mListEvents.isEmpty()) {
            mAdapter = new AdapterListEvents(this, mListEvents);
            mListViewEvents.setAdapter(mAdapter);
        }
        else {
            mTVEmptyListEvents.setVisibility(View.VISIBLE);
            mListViewEvents.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        this.mListViewEvents = (ListView) findViewById(R.id.list_view_events);
        this.mTVEmptyListEvents = (TextView) findViewById(R.id.tv_empty_list_events);
        //this.mBtnAddEvent = (ImageButton) findViewById(R.id.btn_add_event);
        this.mListViewEvents.setOnItemClickListener(this);
        //this.mBtnAddEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_activity:
                Intent intent = new Intent(this, ActivityAddEvent.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_EVENT);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event clickedEvent = mAdapter.getItem(position);
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra(EventDetailActivity.EXTRA_SELECTED_EVENT_ID, clickedEvent.getID());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.add_activity) {
            startActivity(new Intent (ActivityHome.this, ActivityAddEvent.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }
}
