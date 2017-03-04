package com.example.remember.fragment;

import android.app.DatePickerDialog;
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
    InputMethodManager inputManager;
    DataSource dataSource;
    Calendar calendar = Calendar.getInstance();

    Reminder reminder;
    Category category;
    List<String> values = new ArrayList<>();


    EditText title;
    EditText date;

    //Default
    EditText desc;

    //Shopping
    ShoppingListAdapter adapter;
    Button addRow;
    ListView shoppingList;
    EditText newListItem;
    String newItem;

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
                return inflater.inflate(R.layout.fragment_category_default, container, false);
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
        calendar.setTimeInMillis(reminder.getTime());
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
                new TimePickerDialog(getActivity(), timePicker,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true).show();
            }
        });

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void findViews() {
        title = (EditText) getView().findViewById(R.id.edit_reminder_title);
        date = (EditText) getView().findViewById(R.id.edit_reminder_date);
        switch (category.getCategory()) {
            case "Birthday":
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

    private void fillViews() {
        title.setText(reminder.getTitle());
        date.setText(reminder.stringDate());
        switch (category.getCategory()) {
            case "Birthday":
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

    private void saveChanges() {
        if (!title.getText().toString().matches("")) {
            reminder.setTitle(title.getText().toString());
            reminder.setTime(calendar.getTimeInMillis());
            switch (category.getCategory()) {
                case "Birthday":
                    break;
                case "Phone Call":
                    break;
                case "Important":
                    break;
                case "Shopping":
                    reminder.setList(adapter.getItems());
                    break;
                default:
                    reminder.setDescription(desc.getText().toString());
                    break;
            }
            dataSource.updateReminder(reminder);
            dataSource.close();
            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), getActivity().getClass());
            intent.putExtra(MainActivity.REMINDER_DETAILS, reminder);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Reminder needs a title", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLabel() {
        String format = "dd.MM.yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        date.setText(sdf.format(calendar.getTime()));
    }

    private void closeKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
