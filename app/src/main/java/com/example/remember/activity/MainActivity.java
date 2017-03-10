package com.example.remember.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.R;
import com.example.remember.model.Reminder;
import com.example.remember.adapter.CategoryAdapter;
import com.example.remember.adapter.ReminderAdapter;
import com.example.remember.database.DataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public final static String REMINDER_DETAILS = "com.example.remember.REMINDER_DETAILS";
    private DataSource dataSource;
    private ListView listView;
    private Spinner spinner;
    private List<Reminder> reminders = new ArrayList<>();
    private List<Icon> icons = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<Category> spinCat = new ArrayList<>();
    private ReminderAdapter remAdapter;
    private CategoryAdapter catAdapter;
    private Intent intent;
    private Context context = MainActivity.this;
    private int catId;
    private long currentTime = new Date().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("alarm")) {
            Reminder r = (Reminder) getIntent().getSerializableExtra(REMINDER_DETAILS);
            Intent i = new Intent(this, ReminderDetailsActivity.class);
            i.putExtra(REMINDER_DETAILS, r);
            startActivity(i);
        }

        setUpViews();
        setUpData();
        setUpSpinner();

        dataSource.close();

        // Listener for list item clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Reminder r = (Reminder) listView.getItemAtPosition(position);
                intent = new Intent(context, ReminderDetailsActivity.class);
                intent.putExtra(REMINDER_DETAILS, r);
                startActivity(intent);
            }
        });

        // Spinner select listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    reminders = dataSource.getAllFutureReminders(currentTime);
                } else {
                    catId = spinCat.get(i).getId();
                    reminders = dataSource.getAllFutureRemindersWithCategory(catId, currentTime);
                }
                setUpListView(reminders, categories);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Drawable drawable = menu.findItem(R.id.action_add).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addReminder();
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_off:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //region Setup functions
    private void setUpSpinner() {
        catAdapter = new CategoryAdapter(context, spinCat, icons);
        spinner.setAdapter(catAdapter);
    }

    private void setUpViews() {
        listView = (ListView) findViewById(R.id.reminder_list);
        spinner = (Spinner) findViewById(R.id.category_spinner);
    }

    private void setUpData() {
        dataSource = new DataSource(context);
        reminders = dataSource.getAllFutureReminders(currentTime);
        icons = dataSource.getAllIcons();
        categories = dataSource.getAllCategories();
        spinCat = dataSource.getAllCategories();
        spinCat.add(0, new Category("All", "#e2e2e2", "#00FFFFFF", 0));
        Log.v(TAG, icons.size() + "");
        setUpListView(reminders, categories);
    }

    private void setUpListView(List<Reminder> rem, List<Category> cat) {
        remAdapter = new ReminderAdapter(context, rem, cat, icons);
        listView.setAdapter(remAdapter);
    }
    //endregion

    //region Menu buttons
    private void addReminder() {
        Log.v(TAG, "Add button clicked");
        intent = new Intent(context, NewReminderActivity.class);
        startActivity(intent);
    }
    //endregion
}