package com.walrusoft.time2time;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;


public class ActivityAddEvent extends Activity {

    Button bsave;
    EditText etEventName;
    private EventDAO mEventDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_event);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        etEventName = (EditText) findViewById(R.id.et_add_event_name);
        bsave = (Button) findViewById(R.id.btn_save);
        bsave.setOnClickListener(btnOnClickListener);

        mEventDAO = new EventDAO(this);
    }

    Button.OnClickListener btnOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == bsave) {
                Editable eventName = etEventName.getText();
                if(!TextUtils.isEmpty(eventName)) {
                    ContentValues values = new ContentValues();
                    values.put(DBHelper.COLUMN_EVENT_NAME, eventName.toString());
                    mEventDAO.insert(values);
                }
                finish();
            }
            //TODO
            //if view is close, finish
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_add_event, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_activity_add_event, container, false);
            return rootView;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventDAO.close();
    }
}
