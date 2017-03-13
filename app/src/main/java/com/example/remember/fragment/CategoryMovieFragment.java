package com.example.remember.fragment;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.remember.AlarmReceiver;
import com.example.remember.R;
import com.example.remember.activity.MainActivity;
import com.example.remember.activity.ShowImageActivity;
import com.example.remember.database.DataSource;
import com.example.remember.model.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class CategoryMovieFragment extends Fragment {
    private DataSource dataSource;
    private int catId;

    private final Calendar calendar = Calendar.getInstance();
    private final int PICK_IMAGE = 1;
    private Uri selectedImageUri;

    private Button imageBtn;
    private EditText title;
    private EditText date;
    private ImageView image;

    public CategoryMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_category_movie, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataSource = new DataSource(getActivity());
        catId = getArguments().getInt("category");

        findViews();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        image.getLayoutParams().height = size.y;

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

        //region Click listeners
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                new TimePickerDialog(getActivity(), timePicker,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true).show();
            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowImageActivity.class);
                intent.putExtra("imageUri", selectedImageUri + "");
                startActivity(intent);
            }
        });
        //endregion
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                selectedImageUri = data.getData();
                image.setImageURI(selectedImageUri);
            }
        }
    }

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
        if (getView() != null) {
            title = (EditText) getView().findViewById(R.id.edit_reminder_title);
            date = (EditText) getView().findViewById(R.id.edit_reminder_date);
            imageBtn = (Button) getView().findViewById(R.id.image_button);
            image = (ImageView) getView().findViewById(R.id.imageView);
        }
    }

    // Update date text field with date gotten from calendar
    private void updateLabel() {
        String format = "dd.MM.yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        date.setText(sdf.format(calendar.getTime()));
    }

    // Save new reminder to database
    private void saveReminder() {
        String t = title.getText().toString();
        String da = date.getText().toString();
        if (!t.matches("") && !da.matches("")) {
            Reminder reminder = new Reminder(t, selectedImageUri + "", catId, calendar.getTimeInMillis());
            int id = (int) dataSource.createReminder(reminder);
            dataSource.close();
            setAlarm(calendar, id);

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Reminder needs a title and a date", Toast.LENGTH_SHORT).show();
        }
    }

    // Set alarm to notify on time gotten from calendar
    private void setAlarm(Calendar targetCal, int id) {
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("reminder", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    // Close keyboard
    private void closeKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
