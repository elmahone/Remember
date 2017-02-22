package com.example.remember;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private static final String TAG = "CategoryAdapter";
    private Context context;
    private List<Category> categories;
    private static LayoutInflater inflater = null;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_row, null);
        }
        Category category = categories.get(position);
        TextView name = (TextView) view.findViewById(R.id.category_name);
        ImageView cat_icon = (ImageView) view.findViewById(R.id.category_icon_field);

        name.setText(category.getCategory());
        cat_icon.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                new int[]{Color.parseColor(category.getColor())}));

        return view;
    }
}
