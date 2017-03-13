package com.example.remember.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.remember.AlarmReceiver;
import com.example.remember.adapter.DateSpinnerAdapter;
import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.R;
import com.example.remember.model.Reminder;
import com.example.remember.adapter.CategoryAdapter;
import com.example.remember.adapter.ReminderAdapter;
import com.example.remember.database.DataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public final static String REMINDER_DETAILS = "com.example.remember.REMINDER_DETAILS";

    private ReminderAdapter remAdapter;
    private DataSource dataSource;
    private int catId;

    private final long currentTime = new Date().getTime();
    private final Context context = MainActivity.this;
    private Intent intent;
    private boolean allDates = true;

    private List<Category> categories = new ArrayList<>();
    private List<Reminder> reminders = new ArrayList<>();
    private List<Category> spinCat = new ArrayList<>();
    private List<Icon> icons = new ArrayList<>();

    private ListView listView;
    private Spinner catSpinner;
    private Spinner dateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new DataSource(context);

        // if close app button was pressed
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        // if alarm starts activity
        if (getIntent().hasExtra("alarm")) {
            int remId = Integer.valueOf(getIntent().getStringExtra("remId"));

            Reminder r = dataSource.getReminder(remId);
            dataSource.close();
            Intent i = new Intent(this, ReminderDetailsActivity.class);
            i.putExtra(REMINDER_DETAILS, r);
            startActivity(i);
        }

        setUpViews();
        setUpData();
        setUpSpinners();

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

        // Category Spinner select listener
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateUpListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Date filter Spinner select listener
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateUpListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Delete reminder");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;
        final Reminder selRem = remAdapter.getItem(position);

        if (item.getTitle() == "Delete") {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Reminder")
                    .setMessage("Are you sure?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            cancelAlarm(selRem.getId());
                            dataSource.deleteReminder(selRem.getId());
                            Toast.makeText(context, selRem.getTitle() + " deleted", Toast.LENGTH_SHORT).show();
                            remAdapter.remove(position);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        } else {
            return false;
        }
        return true;
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

            case R.id.action_history:
                showHistory();
                return true;

            case R.id.action_settings:
                //todo settings
                return true;

            case R.id.action_off:
                closeApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //region Setup functions
    private void setUpSpinners() {
        CategoryAdapter catAdapter = new CategoryAdapter(context, spinCat, icons);
        catSpinner.setAdapter(catAdapter);

        String[] list = getResources().getStringArray(R.array.date_spinner);
        DateSpinnerAdapter dataAdapter = new DateSpinnerAdapter(this, list);
        dateSpinner.setAdapter(dataAdapter);
    }

    private void setUpViews() {
        listView = (ListView) findViewById(R.id.reminder_list);
        catSpinner = (Spinner) findViewById(R.id.category_spinner);
        dateSpinner = (Spinner) findViewById(R.id.date_spinner);
    }

    private void setUpData() {
        reminders = dataSource.getAllFutureReminders(currentTime);
        icons = dataSource.getAllIcons();
        categories = dataSource.getAllCategories();
        spinCat = dataSource.getAllCategories();
        spinCat.add(0, new Category("All", "#e2e2e2", "#00FFFFFF", 0));
        setUpListView(reminders, categories);
    }

    private void setUpListView(List<Reminder> rem, List<Category> cat) {
        remAdapter = new ReminderAdapter(context, rem, cat, icons);
        listView.setAdapter(remAdapter);
    }

    private void updateUpListView() {

        String range = dateSpinner.getSelectedItem().toString();
        Calendar endCal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();

        boolean allCategories = true;
        if (catSpinner.getSelectedItemPosition() == 0) {
            allCategories = true;
        } else {
            allCategories = false;
            catId = spinCat.get(catSpinner.getSelectedItemPosition()).getId();
        }

        switch (range) {
            case "All":
                allDates = true;
                break;
            case "Today":
                allDates = false;
                endCal.set(Calendar.HOUR_OF_DAY, 23);
                endCal.set(Calendar.MINUTE, 59);
                endCal.set(Calendar.SECOND, 59);
                break;
            case "Tomorrow":
                allDates = false;
                startCal.add(Calendar.DAY_OF_WEEK, 1);
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                endCal.add(Calendar.DAY_OF_WEEK, 1);
                endCal.set(Calendar.HOUR_OF_DAY, 23);
                endCal.set(Calendar.MINUTE, 59);
                endCal.set(Calendar.SECOND, 59);
                break;
            case "This week":
                allDates = false;
                endCal.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_WEEK));
                endCal.set(Calendar.HOUR_OF_DAY, 23);
                endCal.set(Calendar.MINUTE, 59);
                endCal.set(Calendar.SECOND, 59);
                break;
            case "Next week":
                allDates = false;
                startCal.add(Calendar.WEEK_OF_YEAR, 1);
                startCal.set(Calendar.DAY_OF_WEEK, 1);
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                endCal.add(Calendar.WEEK_OF_YEAR, 1);
                endCal.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_WEEK));
                endCal.set(Calendar.HOUR_OF_DAY, 23);
                endCal.set(Calendar.MINUTE, 59);
                endCal.set(Calendar.SECOND, 59);
                break;
            case "This month":
                allDates = false;
                endCal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
                endCal.set(Calendar.HOUR_OF_DAY, 23);
                endCal.set(Calendar.MINUTE, 59);
                endCal.set(Calendar.SECOND, 59);
                break;
            case "Next month":
                allDates = false;
                startCal.add(Calendar.MONTH, 1);
                startCal.set(Calendar.DAY_OF_MONTH, 1);
                startCal.set(Calendar.HOUR_OF_DAY, 0);
                startCal.set(Calendar.MINUTE, 0);
                startCal.set(Calendar.SECOND, 0);

                endCal.add(Calendar.MONTH, 1);
                endCal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
                endCal.set(Calendar.HOUR_OF_DAY, 23);
                endCal.set(Calendar.MINUTE, 59);
                endCal.set(Calendar.SECOND, 59);
                break;
            case "This year":
                allDates = false;
                endCal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR));
                endCal.set(Calendar.HOUR_OF_DAY, 23);
                endCal.set(Calendar.MINUTE, 59);
                endCal.set(Calendar.SECOND, 59);
                break;
            default:
                break;
        }

        if (allCategories) {
            if (allDates) {
                reminders = dataSource.getAllFutureReminders(startCal.getTimeInMillis());
            } else {
                reminders = dataSource.getAllFutureRemindersBetweenDates(startCal.getTimeInMillis(), endCal.getTimeInMillis());
            }
        } else {
            if (allDates) {
                reminders = dataSource.getAllFutureRemindersWithCategory(catId, startCal.getTimeInMillis());
            } else {
                reminders = dataSource.getAllFutureRemindersWithCategoryBetweenDates(catId, startCal.getTimeInMillis(), endCal.getTimeInMillis());
            }
        }

        remAdapter = new ReminderAdapter(context, reminders, categories, icons);
        listView.setAdapter(remAdapter);
    }
    //endregion

    private void cancelAlarm(int id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("reminder", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    //region Menu buttons
    private void addReminder() {
        intent = new Intent(context, NewReminderActivity.class);
        startActivity(intent);
    }

    private void showHistory() {
        Intent histIntent = new Intent(this, HistoryActivity.class);
        startActivity(histIntent);
    }

    private void showSettings() {
    }

    private void closeApp() {
        Intent exitIntent = new Intent(this, MainActivity.class);
        exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        exitIntent.putExtra("EXIT", true);
        startActivity(exitIntent);
    }

    //endregion
}