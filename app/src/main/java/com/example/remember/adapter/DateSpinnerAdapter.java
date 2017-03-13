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

import com.example.remember.R;
import com.example.remember.model.Category;
import com.example.remember.model.Icon;

import java.util.List;

public class DateSpinnerAdapter extends BaseAdapter {
    private static final String TAG = "DateSpinnerAdapter";
    private Context context;
    private String[] list;
    private static LayoutInflater inflater = null;

    public DateSpinnerAdapter(Context context, String[] list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_row2, null);
        }
        String item = list[position];

        TextView name = (TextView) view.findViewById(R.id.name);
        //set name of category
        name.setText(item);

        return view;
    }
}
