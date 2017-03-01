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

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private static final String TAG = "CategoryAdapter";
    private Context context;
    private List<Category> categories;
    private List<Icon> icons;
    private static LayoutInflater inflater = null;

    public CategoryAdapter(Context context, List<Category> categories, List<Icon> icons) {
        this.context = context;
        this.categories = categories;
        this.icons = icons;
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
        LinearLayout cat_icon_bg = (LinearLayout) view.findViewById(R.id.category_icon_background);
        ImageView cat_icon = (ImageView) view.findViewById(R.id.category_icon_field);

        //set name of category
        name.setText(category.getCategory());
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
