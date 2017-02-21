package com.example.remember;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DataSource dataSource;
    ListView listView;
    List<Reminder> reminders = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.add_button);
        dataSource = new DataSource(this);

        reminders = dataSource.getAllReminders();
        categories = dataSource.getAllCategories();

        listView = (ListView) findViewById(R.id.reminder_list);
        listView.setAdapter(new ReminderAdapter(this, reminders, categories));
        dataSource.close();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Add button clicked");
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