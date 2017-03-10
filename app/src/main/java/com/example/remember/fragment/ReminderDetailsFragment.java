package com.example.remember.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.remember.R;
import com.example.remember.adapter.ShoppingListAdapter;
import com.example.remember.model.Category;
import com.example.remember.model.Reminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderDetailsFragment extends Fragment {
    private Reminder reminder;
    private Category category;
    private ShoppingListAdapter adapter;

    private List<String> values = new ArrayList<>();

    private ListView shoppingList;
    private TextView reminderTitle;
    private TextView reminderDate;
    private TextView reminderDesc;

    public ReminderDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reminder = (Reminder) getArguments().getSerializable("reminder");
            category = (Category) getArguments().getSerializable("category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (category.getCategory()) {
            case "Birthday":
                return inflater.inflate(R.layout.fragment_category_birthday_details, container, false);
            case "Phone Call":
                return inflater.inflate(R.layout.fragment_reminder_details, container, false);
            case "Important":
                return inflater.inflate(R.layout.fragment_reminder_details, container, false);
            case "Shopping":
                return inflater.inflate(R.layout.fragment_category_shopping_details, container, false);
            default:
                return inflater.inflate(R.layout.fragment_reminder_details, container, false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        fillViews();
    }

    // Find views depending on what category is selected
    private void findViews() {
        reminderTitle = (TextView) getView().findViewById(R.id.reminder_title);
        reminderDate = (TextView) getView().findViewById(R.id.reminder_date);
        switch (category.getCategory()) {
            case "Birthday":
                reminderDesc = (TextView) getView().findViewById(R.id.reminder_description);
                break;
            case "Phone Call":
                break;
            case "Important":
                break;
            case "Shopping":
                shoppingList = (ListView) getView().findViewById(R.id.shopping_list);
                break;
            default:
                reminderDesc = (TextView) getView().findViewById(R.id.reminder_description);
                break;
        }
    }

    // Fill text fields with data saved to database
    private void fillViews() {
        reminderTitle.setText(reminder.getTitle());
        reminderDate.setText(reminder.stringDate());
        switch (category.getCategory()) {
            case "Birthday":
                reminderDesc.setText(reminder.getDescription());
                reminderDate.setText(reminder.stringBirthDate());
                break;
            case "Phone Call":
                break;
            case "Important":
                break;
            case "Shopping":
                values = reminder.getList();
                adapter = new ShoppingListAdapter(getActivity(), values, false);
                shoppingList.setAdapter(adapter);
                break;
            default:
                reminderDesc.setText(reminder.getDescription());
                break;
        }
    }
}
