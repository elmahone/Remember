package com.example.remember.fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.remember.AlarmReceiver;
import com.example.remember.R;
import com.example.remember.activity.MainActivity;
import com.example.remember.adapter.ShoppingListAdapter;
import com.example.remember.database.DataSource;
import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.model.Reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReminderEditFragment extends Fragment {
    private DataSource dataSource;
    private Category category;
    private Reminder reminder;
    private ShoppingListAdapter adapter;

    private Calendar calendar = Calendar.getInstance();
    private Calendar thisYear = Calendar.getInstance();
    private Calendar today = Calendar.getInstance();

    private InputMethodManager inputManager;

    private List<String> values = new ArrayList<>();
    private String newItem;

    private Button addRow;
    private ListView shoppingList;
    private EditText title;
    private EditText date;
    private EditText desc;
    private EditText newListItem;

    public ReminderEditFragment() {
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
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        switch (category.getCategory()) {
            case "Birthday":
                return inflater.inflate(R.layout.fragment_category_birthday, container, false);
            case "Phone Call":
                return inflater.inflate(R.layout.fragment_category_default, container, false);
            case "Important":
                return inflater.inflate(R.layout.fragment_category_default, container, false);
            case "Shopping":
                return inflater.inflate(R.layout.fragment_category_shopping, container, false);
            default:
                return inflater.inflate(R.layout.fragment_category_default, container, false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataSource = new DataSource(getActivity());

        findViews();
        // Set default times from database
        if (category.getCategory().matches("Birthday")) {
            calendar.setTimeInMillis(reminder.getBirthday());
            thisYear.setTimeInMillis(reminder.getTime());
        } else {
            calendar.setTimeInMillis(reminder.getTime());
        }
        fillViews();

        //region DatePicker & TimePicker listeners
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        final TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                new DatePickerDialog(getActivity(), datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        };
        //endregion

        // Date click listener
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                // If category is birthday don't show timePicker
                if (category.getCategory().matches("Birthday")) {
                    new DatePickerDialog(getActivity(), datePicker,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                } else {
                    new TimePickerDialog(getActivity(), timePicker,
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true).show();
                }
            }
        });

        // Add a new row to shopping list
        if (category.getCategory().matches("Shopping")) {
            addRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newItem = newListItem.getText().toString();
                    if (!newItem.matches("")) {
                        Toast.makeText(getActivity(), newItem, Toast.LENGTH_SHORT).show();
                        adapter.addItem(newItem);
                        newListItem.setText("");
                        closeKeyboard();
                    } else {
                        Toast.makeText(getActivity(), "Type something first", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.edit_menu, menu);
        Drawable drawable = menu.findItem(R.id.action_save).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }


    // Override save menu button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveChanges();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Find views depending on what category is selected
    private void findViews() {
        title = (EditText) getView().findViewById(R.id.edit_reminder_title);
        date = (EditText) getView().findViewById(R.id.edit_reminder_date);
        switch (category.getCategory()) {
            case "Birthday":
                desc = (EditText) getView().findViewById(R.id.edit_reminder_description);
                break;
            case "Phone Call":
                break;
            case "Important":
                break;
            case "Shopping":
                shoppingList = (ListView) getView().findViewById(R.id.shopping_list);
                addRow = (Button) getView().findViewById(R.id.add_new_list_item);
                newListItem = (EditText) getView().findViewById(R.id.new_list_item);
                break;
            default:
                desc = (EditText) getView().findViewById(R.id.edit_reminder_description);
                break;
        }
    }

    // Fill text fields with data saved to database
    private void fillViews() {
        title.setText(reminder.getTitle());
        date.setText(reminder.stringDate());
        switch (category.getCategory()) {
            case "Birthday":
                desc.setText(reminder.getDescription());
                date.setText(reminder.stringBirthDate());
                break;
            case "Phone Call":
                break;
            case "Important":
                break;
            case "Shopping":
                values = reminder.getList();
                adapter = new ShoppingListAdapter(getActivity(), values, true);
                shoppingList.setAdapter(adapter);
                break;
            default:
                desc.setText(reminder.getDescription());
                break;
        }
    }

    // Update date text field with calendar date
    // Set default birthday description if field is empty
    private void updateLabel() {
        if (category.getCategory().matches("Birthday")) {
            String format = "MMMM dd. yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            String t = title.getText().toString();
            date.setText(sdf.format(calendar.getTime()));
            if (!getAge().matches("0") && desc.getText().toString().matches("")) {
                desc.setText(t + " turns " + getAge());
            }
        } else {
            String format = "dd.MM.yyyy HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            date.setText(sdf.format(calendar.getTime()));
        }
    }

    // Save edited fields to database
    private void saveChanges() {
        if (!title.getText().toString().matches("")) {

            reminder.setTitle(title.getText().toString());
            switch (category.getCategory()) {
                case "Birthday":
                    reminder.setDescription(desc.getText().toString());
                    reminder.setBirthday(calendar.getTimeInMillis());
                    reminder.setTime(thisYear.getTimeInMillis());
                    setAlarm(thisYear, reminder);
                    break;
                case "Phone Call":
                    break;
                case "Important":
                    break;
                case "Shopping":
                    reminder.setList(adapter.getItems());
                    reminder.setTime(calendar.getTimeInMillis());
                    setAlarm(calendar, reminder);
                    break;
                default:
                    reminder.setDescription(desc.getText().toString());
                    reminder.setTime(calendar.getTimeInMillis());
                    setAlarm(calendar, reminder);
                    break;
            }
            dataSource.updateReminder(reminder);
            dataSource.close();
            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            // Send back to activity when saved
            Intent intent = new Intent(getActivity(), getActivity().getClass());
            intent.putExtra(MainActivity.REMINDER_DETAILS, reminder);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Reminder needs a title", Toast.LENGTH_SHORT).show();
        }
    }

    // Cancel previous alarm and set new alarm to notify on time gotten from calendar
    private void setAlarm(Calendar targetCal, Reminder reminder) {
        Toast.makeText(getActivity(), "Alarm is set", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("reminder", reminder);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), reminder.getId(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
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

        // if date has passed set to next year
        if (today.getTimeInMillis() > thisYear.getTimeInMillis()) {
            thisYear.set(today.get(Calendar.YEAR) + 1, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 12, 0, 0);
        }

        int age = thisYear.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);

        Integer ageInt = age;
        return ageInt.toString();
    }
}
