package com.example.remember.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.remember.Category;
import com.example.remember.Icon;
import com.example.remember.R;
import com.example.remember.Reminder;
import com.example.remember.database.DataSource;


public class ReminderDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ReminderDetailsActivity";
    DataSource dataSource;
    Context context = ReminderDetailsActivity.this;
    Reminder reminder;
    Category category;
    Icon icon;
    Intent intent;

    RelativeLayout iconBackground;
    ImageView iconView;
    TextView categoryName;
    TextView reminderTitle;
    TextView reminderDate;
    TextView reminderDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_details);

        findViews();
        dataSource = new DataSource(context);
        intent = getIntent();
        reminder = (Reminder) intent.getSerializableExtra(MainActivity.REMINDER_DETAILS);
        category = dataSource.getCategory(reminder.getCategory());
        icon = dataSource.getIcon(category.getIcon());
        fillViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        Drawable drawable = menu.findItem(R.id.action_edit).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_off:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findViews() {
        iconBackground = (RelativeLayout) findViewById(R.id.icon_background);
        iconView = (ImageView) findViewById(R.id.icon);

        categoryName = (TextView) findViewById(R.id.category_name);
        reminderTitle = (TextView) findViewById(R.id.reminder_title);
        reminderDate = (TextView) findViewById(R.id.reminder_date);
        reminderDesc = (TextView) findViewById(R.id.reminder_description);
    }

    private void fillViews() {
        iconBackground.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                new int[]{Color.parseColor(category.getBackgroundColor())}));
        iconView.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                new int[]{Color.parseColor(category.getIconColor())}));
        iconView.setBackground(ContextCompat.getDrawable(context, icon.getIcon()));

        categoryName.setText(category.getCategory());
        reminderTitle.setText(reminder.getTitle());
        reminderDate.setText(reminder.stringDate());
        reminderDesc.setText(reminder.getDescription());

    }
}
