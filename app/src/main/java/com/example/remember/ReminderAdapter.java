package com.example.remember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Black on 2017-02-15.
 */

public class ReminderAdapter extends BaseAdapter {
    Context context;
    ArrayList<Reminder> data;
    private static LayoutInflater inflater = null;

    public ReminderAdapter(Context context, ArrayList<Reminder> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final Reminder reminder) {
        data.add(reminder);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_row, null);
        }
        Reminder reminder = data.get(position);

        TextView header = (TextView) view.findViewById(R.id.list_header);
        TextView date = (TextView) view.findViewById(R.id.list_date);
        TextView text = (TextView) view.findViewById(R.id.list_text);

        switch (reminder.getCategory()){
            case "Alarm": {
                header.setText(reminder.getTitle() + " alert");
                date.setText(reminder.getDate() + " alert");
                text.setText(reminder.getDescription() + " alert");
                break;
            }
            case "Exercise": {
                header.setText(reminder.getTitle() + " ex");
                date.setText(reminder.getDate() + " ex");
                text.setText(reminder.getDescription() + " ex");
                break;
            }

        }
        return view;
    }
}
