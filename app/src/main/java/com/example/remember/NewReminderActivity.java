package com.example.remember;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewReminderActivity extends AppCompatActivity {
    private static final String TAG = "NewReminderActivity";
    private DataSource dataSource;
    private Calendar calendar = Calendar.getInstance();
    private Spinner spinner;
    private EditText editTitle;
    private EditText editDate;
    private EditText editDesc;
    private Button addCategory;
    private List<Category> categories;
    private List<Icon> icons;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        //region Find views
        spinner = (Spinner) findViewById(R.id.category_spinner);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editDate = (EditText) findViewById(R.id.editDate);
        editDesc = (EditText) findViewById(R.id.editDescription);
        //endregion

        dataSource = new DataSource(this);
        categories = dataSource.getAllCategories();
        icons = dataSource.getAllIcons();

        adapter = new CategoryAdapter(this, categories, icons);
        spinner.setAdapter(adapter);

        //region DatePicker & TimePicker listeners
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                new DatePickerDialog(NewReminderActivity.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        };
        //endregion

        //region Click Listeners

        // Date view listener
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(NewReminderActivity.this, time,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true).show();
            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String name = categories.get(position).getCategory();
                Log.v(TAG, name + " Selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.v(TAG, "Nothing Selected");
            }
        });


        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_save_menu, menu);
        Drawable add = menu.findItem(R.id.action_add).getIcon();
        Drawable save = menu.findItem(R.id.action_save).getIcon();
        if (add != null && save != null) {
            add.mutate();
            add.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            save.mutate();
            save.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addCategory();
                return true;
            case R.id.action_save:
                saveReminder();
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_off:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addCategory() {
        Intent intent = new Intent(NewReminderActivity.this, NewCategoryActivity.class);
        startActivity(intent);
    }

    private void saveReminder() {
        // Get values from views
        Category selectedCatecory = (Category) spinner.getSelectedItem();
        String title = editTitle.getText().toString();
        String dateField = editDate.getText().toString();
        long date = calendar.getTimeInMillis();
        String desc = editDesc.getText().toString();

        if (!title.matches("") && !dateField.matches("")) {
            Reminder newReminder = new Reminder(title, desc, selectedCatecory.getId(), date);
            Log.v(TAG, "Date: " + date);
            Log.v(TAG, "title: " + title);
            Log.v(TAG, "categ: " + selectedCatecory.getCategory());
            dataSource.createReminder(newReminder);
            dataSource.close();
            Toast.makeText(NewReminderActivity.this, "New Reminder Created!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewReminderActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else {
            Toast.makeText(NewReminderActivity.this, "Reminder needs a title and a date.", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateLabel() {
        String format = "dd.MM.yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        editDate.setText(sdf.format(calendar.getTime()));
    }
}
