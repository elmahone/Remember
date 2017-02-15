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
    ListView listView;
    ArrayList<Reminder> dataList = new ArrayList<>();
    Reminder reminder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reminder = new Reminder("Wake up", "Good morgon", "Alarm", 30, 21, 22, 2, 2017);
        dataList.add(reminder);
        reminder = new Reminder("Go to jog", "", "Exercise", 0, 10, 16, 2, 2017);
        dataList.add(reminder);
        
        listView = (ListView) findViewById(R.id.reminder_list);
        listView.setAdapter(new ReminderAdapter(this, dataList));

    }
}
