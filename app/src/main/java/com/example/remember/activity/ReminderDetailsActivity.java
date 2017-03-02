package com.example.remember.activity;

import android.app.FragmentTransaction;
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

import com.example.remember.fragment.ReminderDetailsFragment;
import com.example.remember.fragment.ReminderEditFragment;
import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.R;
import com.example.remember.model.Reminder;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_details);

        iconBackground = (RelativeLayout) findViewById(R.id.icon_background);
        iconView = (ImageView) findViewById(R.id.icon);

        dataSource = new DataSource(context);
        intent = getIntent();
        reminder = (Reminder) intent.getSerializableExtra(MainActivity.REMINDER_DETAILS);
        category = dataSource.getCategory(reminder.getCategory());
        icon = dataSource.getIcon(category.getIcon());

        iconBackground.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                new int[]{Color.parseColor(category.getBackgroundColor())}));
        iconView.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                new int[]{Color.parseColor(category.getIconColor())}));
        iconView.setBackground(ContextCompat.getDrawable(context, icon.getIcon()));

        if(savedInstanceState == null) {
            showDetailsFragment();
        }
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
                showEditFragment();
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_off:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDetailsFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        ReminderDetailsFragment detailsFragment = new ReminderDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("reminder", reminder);
        detailsFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_content, detailsFragment, "details_fragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void showEditFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        ReminderEditFragment editFragment = new ReminderEditFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("reminder", reminder);
        editFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_content, editFragment, "details_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


}
