package com.example.remember.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.remember.R;
import com.example.remember.adapter.CategoryAdapter;
import com.example.remember.adapter.ReminderAdapter;
import com.example.remember.database.DataSource;
import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.model.Reminder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final static String REMINDER_DETAILS = "com.example.remember.REMINDER_DETAILS";

    private ReminderAdapter remAdapter;
    private DataSource dataSource;
    private int catId;

    private final long currentTime = new Date().getTime();
    private final Context context = HistoryActivity.this;
    private Intent intent;

    private List<Category> categories = new ArrayList<>();
    private List<Reminder> reminders = new ArrayList<>();
    private List<Category> spinCat = new ArrayList<>();
    private List<Icon> icons = new ArrayList<>();

    private ListView listView;
    private Spinner catSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dataSource = new DataSource(context);

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

        // Category Spinner select listener
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (catSpinner.getSelectedItemPosition() == 0) {
                    reminders = dataSource.getAllPastReminders(currentTime);
                } else {
                    catId = spinCat.get(catSpinner.getSelectedItemPosition()).getId();
                    reminders = dataSource.getAllPastRemindersWithCategory(catId, currentTime);
                }

                setUpListView(reminders, categories);
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
        menu.setHeaderTitle("Delete from history");
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
                            dataSource.deleteReminder(selRem.getId());
                            Toast.makeText(HistoryActivity.this, selRem.getTitle() + " deleted", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.history_menu, menu);
        Drawable drawable = menu.findItem(R.id.action_home).getIcon();
        Drawable drawable2 = menu.findItem(R.id.action_clear).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
        if (drawable2 != null) {
            drawable2.mutate();
            drawable2.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                goHome();
                return true;

            case R.id.action_clear:
                clearHistory();
                return true;

            case R.id.action_settings:
                showSettings();
                return true;

            case R.id.action_off:
                closeApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //region Setup functions
    private void setUpSpinner() {
        CategoryAdapter catAdapter = new CategoryAdapter(context, spinCat, icons);
        catSpinner.setAdapter(catAdapter);
    }

    private void setUpViews() {
        listView = (ListView) findViewById(R.id.reminder_list);
        catSpinner = (Spinner) findViewById(R.id.category_spinner);
    }

    private void setUpData() {
        reminders = dataSource.getAllPastReminders(currentTime);
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
    //endregion

    //region Menu buttons
    private void goHome() {
        Intent homeIntent = new Intent(context, MainActivity.class);
        startActivity(homeIntent);
    }

    private void clearHistory() {
        new AlertDialog.Builder(this)
                .setTitle("Clear history")
                .setMessage("Do you really want to clear your entire reminder history?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dataSource.deletePastReminders();
                        Toast.makeText(HistoryActivity.this, "History cleared", Toast.LENGTH_SHORT).show();
                        setUpData();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
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
