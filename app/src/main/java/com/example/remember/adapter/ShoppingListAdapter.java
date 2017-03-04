package com.example.remember.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.remember.R;

import java.util.List;

public class ShoppingListAdapter extends BaseAdapter {
    private static final String TAG = "ReminderAdapter";
    private Context context;
    private List<String> items;
    private static LayoutInflater inflater = null;
    private boolean editable;

    public ShoppingListAdapter(Context context, List<String> items, boolean editable) {
        this.context = context;
        this.items = items;
        this.editable = editable;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(String item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null && editable) {
            view = inflater.inflate(R.layout.shopping_list_item2, null);

        } else if (view == null && !editable) {
            view = inflater.inflate(R.layout.shopping_list_item, null);

        }
        final TextView itemName = (TextView) view.findViewById(R.id.item_name);
        itemName.setText(items.get(position));

        if (editable) {
            Button remove = (Button) view.findViewById(R.id.remove_item);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            final CheckBox check = (CheckBox) view.findViewById(R.id.check_item);

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        itemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        itemName.setPaintFlags(0);
                    }
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (check.isChecked()) {
                        check.setChecked(false);
                    } else {
                        check.setChecked(true);
                    }
                }
            });

        }
        return view;
    }
}
