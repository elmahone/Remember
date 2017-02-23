package com.example.remember;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DataSource dataSource;
    private ListView listView;
    private Spinner spinner;

    private List<Reminder> reminders = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<Category> spinCat = new ArrayList<>();
    private ReminderAdapter remAdapter;
    private CategoryAdapter catAdapter;
    private Button addButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.reminder_list);
        addButton = (Button) findViewById(R.id.add_button);
        spinner = (Spinner) findViewById(R.id.category_spinner);

        dataSource = new DataSource(this);

        reminders = dataSource.getAllReminders();
        categories = dataSource.getAllCategories();
        spinCat = dataSource.getAllCategories();

        remAdapter = new ReminderAdapter(this, reminders, categories);
        listView.setAdapter(remAdapter);

        spinCat.add(0, new Category("All", "#e2e2e2", "#00FFFFFF", 0));
        catAdapter = new CategoryAdapter(this, spinCat);
        spinner.setAdapter(catAdapter);

        dataSource.close();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Add button clicked");
                intent = new Intent(MainActivity.this, NewReminderActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
}