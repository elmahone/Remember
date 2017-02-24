package com.example.remember;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DataSource dataSource;
    private ListView listView;
    private Button addButton;
    private Spinner spinner;
    private List<Reminder> reminders = new ArrayList<>();
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

        setUpViews();
        setUpData();
        setUpSpinner();

        dataSource.close();

        // Listener for 'add new reminder' button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Add button clicked");
                intent = new Intent(context, NewReminderActivity.class);
                startActivity(intent);
            }
        });

        // Listener for list item clicks
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        // Spinner select listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    reminders = dataSource.getAllFutureReminders(currentTime);
                    remAdapter = new ReminderAdapter(context, reminders, categories);
                    listView.setAdapter(remAdapter);
                } else {
                    catId = spinCat.get(i).getId();
                    reminders = dataSource.getAllFutureRemindersWithCategory(catId, currentTime);
                    remAdapter = new ReminderAdapter(context, reminders, categories);
                    listView.setAdapter(remAdapter);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    //region Setup functions
    private void setUpSpinner() {
        catAdapter = new CategoryAdapter(context, spinCat);
        spinner.setAdapter(catAdapter);
    }

    private void setUpViews() {
        listView = (ListView) findViewById(R.id.reminder_list);
        addButton = (Button) findViewById(R.id.add_button);
        spinner = (Spinner) findViewById(R.id.category_spinner);
    }

    private void setUpData() {
        dataSource = new DataSource(context);
        reminders = dataSource.getAllFutureReminders(currentTime);
        categories = dataSource.getAllCategories();
        spinCat = dataSource.getAllCategories();
        spinCat.add(0, new Category("All", "#e2e2e2", "#00FFFFFF", 0));

        setUpListView(reminders, categories);
    }

    private void setUpListView(List<Reminder> rem, List<Category> cat) {
        remAdapter = new ReminderAdapter(context, rem, cat);
        listView.setAdapter(remAdapter);
    }
    //endregion
}