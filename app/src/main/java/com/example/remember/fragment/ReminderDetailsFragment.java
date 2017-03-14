package com.example.remember.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.remember.R;
import com.example.remember.activity.ShowImageActivity;
import com.example.remember.adapter.ShoppingListAdapter;
import com.example.remember.model.Category;
import com.example.remember.model.Reminder;

import java.util.List;

public class ReminderDetailsFragment extends Fragment {
    private Reminder reminder;
    private Category category;

    private SharedPreferences pref;

    private Button callButton;
    private ImageView image;
    private ListView shoppingList;
    private TextView reminderTitle;
    private TextView reminderDate;
    private TextView reminderDesc;
    private TextView reminderPhone;

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
                return inflater.inflate(R.layout.fragment_category_phone_details, container, false);
            case "Important":
                return inflater.inflate(R.layout.fragment_category_important_details, container, false);
            case "Shopping":
                return inflater.inflate(R.layout.fragment_category_shopping_details, container, false);
            case "Movie":
                return inflater.inflate(R.layout.fragment_category_movie_details, container, false);
            default:
                return inflater.inflate(R.layout.fragment_reminder_details, container, false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        findViews();
        fillViews();

        if (callButton != null) {
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + reminder.getDescription()));
                    startActivity(callIntent);
                }
            });
        }
        if (image != null) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ShowImageActivity.class);
                    intent.putExtra("imageUri", reminder.getDescription());
                    startActivity(intent);
                }
            });
        }
    }

    // Find views depending on what category is selected
    private void findViews() {
        if (getView() != null) {
            reminderTitle = (TextView) getView().findViewById(R.id.reminder_title);
            reminderDate = (TextView) getView().findViewById(R.id.reminder_date);
            switch (category.getCategory()) {
                case "Birthday":
                    reminderDesc = (TextView) getView().findViewById(R.id.reminder_description);
                    break;
                case "Phone Call":
                    reminderPhone = (TextView) getView().findViewById(R.id.reminder_phone);
                    callButton = (Button) getView().findViewById(R.id.call_button);
                    break;
                case "Important":
                    reminderDesc = (TextView) getView().findViewById(R.id.reminder_description);
                    break;
                case "Shopping":
                    shoppingList = (ListView) getView().findViewById(R.id.shopping_list);
                    break;
                case "Movie":
                    image = (ImageView) getView().findViewById(R.id.imageView);
                    break;
                default:
                    reminderDesc = (TextView) getView().findViewById(R.id.reminder_description);
                    break;
            }
        }
    }

    // Fill text fields with data saved to database
    private void fillViews() {
        String formatPref = pref.getString("date_format_list", "0");
        reminderTitle.setText(reminder.getTitle());
        reminderDate.setText(reminder.stringDate(formatPref));
        switch (category.getCategory()) {
            case "Birthday":
                reminderDesc.setText(reminder.getDescription());
                reminderDate.setText(reminder.stringBirthDate(formatPref));
                break;
            case "Phone Call":
                reminderPhone.setText(reminder.getDescription());
                if (reminder.getDescription().matches("") || reminder.getDescription() == null) {
                    callButton.setVisibility(View.INVISIBLE);
                } else {
                    callButton.setText("Call Number");
                }
                break;
            case "Important":
                reminderDesc.setText(reminder.getDescription());
                break;
            case "Shopping":
                List<String> values = reminder.getList();
                ShoppingListAdapter adapter = new ShoppingListAdapter(getActivity(), values, false);
                shoppingList.setAdapter(adapter);
                break;
            case "Movie":
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                image.getLayoutParams().height = size.y;

                image.setImageURI(Uri.parse(reminder.getDescription()));
                break;
            default:
                reminderDesc.setText(reminder.getDescription());
                break;

        }
    }
}
