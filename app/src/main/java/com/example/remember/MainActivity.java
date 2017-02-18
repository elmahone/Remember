package com.example.remember;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DataSource dataSource;
    ListView listView;
    ArrayList<Reminder> dataList = new ArrayList<>();
    Reminder reminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DataSource(this);

        reminder = new Reminder("Wake up", "Good morgon", 1, 30, 21, 22, 2, 2017);
        //dataSource.createReminder(reminder);
        dataList.add(reminder);
        reminder = new Reminder("Go to jog", "", 2, 0, 10, 16, 2, 2017);
        //dataSource.createReminder(reminder);
        dataList.add(reminder);
        Category category = new Category("Default");
        dataSource.createCategory(category);
        category = new Category("Exercise");
        dataSource.createCategory(category);
        category = new Category("Shopping");
        dataSource.createCategory(category);

        //dataSource.updateReminderCategory(1, 1);

        //List<Reminder> reminders = dataSource.getAllReminders();
        //List<Reminder> reminders = dataSource.getAllRemindersWithCategory(1);
        Category cat = dataSource.getCategory(1);
        Log.v(TAG, "Before change " + cat.getCategory());
        cat.setCategory("Default");
        dataSource.updateCategory(cat);
        cat = dataSource.getCategory(1);
        Log.v(TAG, "After change " + cat.getCategory());

        List<Reminder> reminders = dataSource.getAllReminders();
        for (Reminder rem : reminders) {
            Category c = dataSource.getCategory(rem.getCategory());
            Log.v(TAG, rem.getTitle() + " " + c.getCategory());
        }

        listView = (ListView) findViewById(R.id.reminder_list);
        listView.setAdapter(new ReminderAdapter(this, dataList));

        dataSource.close();
    }
}
