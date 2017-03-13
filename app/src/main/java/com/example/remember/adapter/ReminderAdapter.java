package com.example.remember.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.R;
import com.example.remember.model.Reminder;

import java.util.List;

public class ReminderAdapter extends BaseAdapter {
    private static final String TAG = "ReminderAdapter";
    private Context context;
    private List<Reminder> reminders;
    private List<Category> categories;
    private List<Icon> icons;
    private static LayoutInflater inflater = null;

    public ReminderAdapter(Context context, List<Reminder> reminders, List<Category> categories, List<Icon> icons) {
        this.context = context;
        this.reminders = reminders;
        this.categories = categories;
        this.icons = icons;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final Reminder reminder) {
        reminders.add(reminder);
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        reminders.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return reminders.size();
    }

    @Override
    public Reminder getItem(int position) {
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
        LinearLayout cat_icon_bg = (LinearLayout) view.findViewById(R.id.list_icon_background);
        ImageView cat_icon = (ImageView) view.findViewById(R.id.list_icon_field);

        //set title, date and description
        header.setText(reminder.getTitle());
        date.setText(reminder.stringDate());
        if (!category.getCategory().matches("Movie")) {
            text.setText(reminder.getDescription());
        } else {
            text.setText("");
        }

        //set icons background color
        cat_icon_bg.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                new int[]{Color.parseColor(category.getBackgroundColor())}));
        //set icon and icon color if icon is in model
        cat_icon.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                new int[]{Color.parseColor(category.getIconColor())}));
        if (category.getIcon() != 0) {
            Icon icon = icons.get(category.getIcon() - 1);
            cat_icon.setBackground(ContextCompat.getDrawable(context, icon.getIcon()));
        }

        return view;
    }
}
