package com.example.remember.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.remember.model.Icon;

import java.util.List;


public class IconAdapter extends BaseAdapter {
    private static final String TAG = "IconAdapter";
    private Context context;
    private List<Icon> icons;
    private Icon icon;
    private static LayoutInflater inflater = null;

    public IconAdapter(Context context, List<Icon> icons) {
        this.context = context;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return icons.size();
    }

    @Override
    public Object getItem(int position) {
        return icons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView iconView;
        icon = icons.get(position);
        if (view == null) {
            // if it's not recycled, initialize some attributes
            iconView = new ImageView(context);
            iconView.setLayoutParams(new GridView.LayoutParams(140, 140));
            iconView.setPadding(10, 10, 10, 10);
        } else {
            iconView = (ImageView) view;
        }
        // filenames is an array of strings
        iconView.setBackgroundResource(icon.getIcon());
        return iconView;
    }
}
