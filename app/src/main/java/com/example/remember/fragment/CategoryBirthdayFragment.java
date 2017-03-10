package com.example.remember.fragment;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.remember.AlarmReceiver;
import com.example.remember.R;
import com.example.remember.activity.MainActivity;
import com.example.remember.database.DataSource;
import com.example.remember.model.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CategoryBirthdayFragment extends Fragment {
    InputMethodManager inputManager;
    DataSource dataSource;
    Calendar calendar;
    Calendar thisYear = Calendar.getInstance();
    Calendar today = Calendar.getInstance();
    int catId;
    EditText title;
    EditText date;
    EditText desc;

    public CategoryBirthdayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_category_birthday, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataSource = new DataSource(getActivity());
        catId = getArguments().getInt("category");
        findViews();

        //region DatePicker & TimePicker listeners
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                if (calendar.get(Calendar.YEAR) == thisYear.get(Calendar.YEAR)) {
                    Toast.makeText(getActivity(), "This year", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getAge(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        //endregion

        // Date click listener
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                closeKeyboard();
                new DatePickerDialog(getActivity(), datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    // Override menu save button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveReminder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Find views from layout
    private void findViews() {
        title = (EditText) getView().findViewById(R.id.edit_reminder_title);
        date = (EditText) getView().findViewById(R.id.edit_reminder_date);
        desc = (EditText) getView().findViewById(R.id.edit_reminder_description);
    }

    // Save reminder to database
    private void saveReminder() {
        String t = title.getText().toString();
        String da = date.getText().toString();
        String de = desc.getText().toString();
        if (!t.matches("") && !da.matches("")) {
            Reminder reminder = new Reminder(t, de, calendar.getTimeInMillis(), catId, thisYear.getTimeInMillis());
            int id = (int) dataSource.createReminder(reminder);
            dataSource.close();
            setAlarm(thisYear, reminder, id);

            // Go back to MainActivity after saving
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Reminder needs a title and a date", Toast.LENGTH_SHORT).show();
        }
    }

    // Update date text field with calendar date
    // Set default birthday description if field is empty
    private void updateLabel() {
        String format = "MMMM dd. yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String t = title.getText().toString();
        date.setText(sdf.format(calendar.getTime()));
        if (!getAge().matches("0")) {
            desc.setText(t + " turns " + getAge());
        }
    }

    // Close keyboard
    private void closeKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // Count age from calendar date
    private String getAge() {
        thisYear.set(today.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 12, 0, 0);

        //if date is passed set to next year
        if (today.getTimeInMillis() > thisYear.getTimeInMillis()) {
            thisYear.set(today.get(Calendar.YEAR) + 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
        }
        int age = thisYear.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        if (thisYear.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        Integer ageInt = age;
        return ageInt.toString();
    }

    private void setAlarm(Calendar targetCal, Reminder reminder, int id) {
        Toast.makeText(getActivity(), "Alarm is set", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("reminder", reminder);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }
}
