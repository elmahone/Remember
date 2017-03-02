package com.example.remember.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.remember.R;
import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.model.Reminder;

public class ReminderDetailsFragment extends Fragment {
    Reminder reminder;
    Category category;
    Icon icon;

    TextView reminderTitle;
    TextView reminderDate;
    TextView reminderDesc;

    public ReminderDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reminder_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        reminder = (Reminder) getArguments().getSerializable("reminder");
        fillViews();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void findViews() {
        reminderTitle = (TextView) getView().findViewById(R.id.reminder_title);
        reminderDate = (TextView) getView().findViewById(R.id.reminder_date);
        reminderDesc = (TextView) getView().findViewById(R.id.reminder_description);
    }

    private void fillViews() {
        reminderTitle.setText(reminder.getTitle());
        reminderDate.setText(reminder.stringDate());
        reminderDesc.setText(reminder.getDescription());
    }
}
