package com.walrusoft.time2time;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EventDetailActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    public static final int REQUEST_CODE_ADD_REMINDER = 40;
    public static final String EXTRA_ADDED_REMINDER = "extra_key_added_reminder";
    public static final String EXTRA_SELECTED_EVENT_ID = "extra_key_selected_event_id";

    private ListView mListViewReminders;
    private TextView mTxtEmptyListReminders;
    private Button mBtnAddReminder;
    private AdapterListReminders mAdapter;
    private List<Reminder> mListReminders;
    private ReminderDAO mReminderDAO;
    private long mEventID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        initViews();

        mReminderDAO = new ReminderDAO(this);
        Intent intent = getIntent();
        if(intent != null) {
            this.mEventID = intent.getLongExtra(EXTRA_SELECTED_EVENT_ID, -1);
        }

        if(mEventID != -1) {
            mListReminders = mReminderDAO.getRemindersOfEvents(mEventID);

            if(mListReminders != null && !mListReminders.isEmpty()) {
                mAdapter = new AdapterListReminders(this, mListReminders);
                mListViewReminders.setAdapter(mAdapter);
            }
            else {
                mTxtEmptyListReminders.setVisibility(View.VISIBLE);
                mListViewReminders.setVisibility(View.GONE);
            }
        }
    }

    private void initViews() {
        this.mListViewReminders = (ListView) findViewById(R.id.list_view_reminders);
        this.mTxtEmptyListReminders = (TextView) findViewById(R.id.tv_empty_list_reminders);
        this.mBtnAddReminder = (Button) findViewById(R.id.btn_add_reminder);
        this.mListViewReminders.setOnItemClickListener(this);
        this.mListViewReminders.setOnItemLongClickListener(this);
        this.mBtnAddReminder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_reminder:
                Intent intent = new Intent(this, ActivityAddReminder.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_REMINDER);
                break;
            case R.id.btn_edit_event:
                //TODO
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_REMINDER) {
            if(resultCode == RESULT_OK) {
                if(mListReminders == null || !mListReminders.isEmpty()) {
                    mListReminders = new ArrayList<Reminder>();
                }

                if(mReminderDAO == null)
                    mReminderDAO = new ReminderDAO(this);
                mListReminders = mReminderDAO.getRemindersOfEvents(mEventID);

                if(mListReminders != null && !mListReminders.isEmpty() && mListViewReminders.getVisibility() != View.VISIBLE) {
                    mTxtEmptyListReminders.setVisibility(View.GONE);
                    mListViewReminders.setVisibility(View.VISIBLE);
                }

                if(mAdapter == null) {
                    mAdapter = new AdapterListReminders(this, mListReminders);
                    mListViewReminders.setAdapter(mAdapter);
                }

                else {
                    mAdapter.setItems(mListReminders);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReminderDAO.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Reminder clickedReminder = mAdapter.getItem(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Reminder clickedReminder = mAdapter.getItem(position);
        showDeleteDialogConfirmation(clickedReminder);
        return true;
    }

    private void showDeleteDialogConfirmation(final Reminder reminder) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Delete reminder?");

        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mReminderDAO != null) {
                    mReminderDAO.deleteReminder(reminder);

                    mListReminders.remove(reminder);
                    if(mListReminders.isEmpty()) {
                        mListViewReminders.setVisibility(View.GONE);
                        mTxtEmptyListReminders.setVisibility(View.VISIBLE);
                    }

                    mAdapter.setItems(mListReminders);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(EventDetailActivity.this, R.string.reminder_deleted_successfully, Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
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
}
