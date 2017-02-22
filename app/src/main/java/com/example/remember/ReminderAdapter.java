package com.example.remember;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReminderAdapter extends BaseAdapter {
    private static final String TAG = "ReminderAdapter";
    private Context context;
    private List<Reminder> reminders;
    private List<Category> categories;
    private static LayoutInflater inflater = null;

    public ReminderAdapter(Context context, List<Reminder> reminders, List<Category> categories) {
        this.context = context;
        this.reminders = reminders;
        this.categories = categories;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final Reminder reminder) {
        reminders.add(reminder);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public Object getItem(int position) {
        return reminders.get(position);
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
        Reminder reminder = reminders.get(position);
        Category category = categories.get(reminder.getCategory() - 1);
        TextView header = (TextView) view.findViewById(R.id.list_header);
        TextView date = (TextView) view.findViewById(R.id.list_date);
        TextView text = (TextView) view.findViewById(R.id.list_text);
        ImageView cat_icon = (ImageView) view.findViewById(R.id.list_icon_field);

        switch (reminder.getCategory()) {
            case 1: {
                header.setText(reminder.getTitle());
                date.setText(reminder.stringDate());
                text.setText(reminder.getDescription());
                cat_icon.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                        new int[]{Color.parseColor(category.getColor())}));
                break;
            }
            case 2: {
                header.setText(reminder.getTitle());
                date.setText(reminder.stringDate());
                text.setText(reminder.getDescription());
                cat_icon.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                        new int[]{Color.parseColor(category.getColor())}));
                break;
            }
            case 3: {
                header.setText(reminder.getTitle());
                date.setText(reminder.stringDate());
                text.setText(reminder.getDescription());
                cat_icon.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                        new int[]{Color.parseColor(category.getColor())}));
                break;
            }
        }
        return view;
    }
}
