package com.example.remember.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.remember.fragment.CategoryBirthdayFragment;
import com.example.remember.fragment.CategoryDefaultFragment;
import com.example.remember.fragment.CategoryImportantFragment;
import com.example.remember.fragment.CategoryMovieFragment;
import com.example.remember.fragment.CategoryPhoneFragment;
import com.example.remember.fragment.CategoryShoppingFragment;
import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.R;
import com.example.remember.adapter.CategoryAdapter;
import com.example.remember.database.DataSource;

import java.util.List;

public class NewReminderActivity extends AppCompatActivity {
    private static final String TAG = "NewReminderActivity";

    private DataSource dataSource;
    private CategoryAdapter adapter;

    private List<Category> categories;
    private List<Icon> icons;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        spinner = (Spinner) findViewById(R.id.category_spinner);

        dataSource = new DataSource(this);
        categories = dataSource.getAllCategories();
        icons = dataSource.getAllIcons();

        adapter = new CategoryAdapter(this, categories, icons);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String name = categories.get(position).getCategory();
                int catId = categories.get(position).getId();

                switch (name) {
                    case "Birthday":
                        CategoryBirthdayFragment birthdayFragment = new CategoryBirthdayFragment();
                        showFragment(catId, birthdayFragment);
                        break;
                    case "Phone Call":
                        CategoryPhoneFragment phoneFragment = new CategoryPhoneFragment();
                        showFragment(catId, phoneFragment);
                        break;
                    case "Important":
                        CategoryImportantFragment importantFragment = new CategoryImportantFragment();
                        showFragment(catId, importantFragment);
                        break;
                    case "Shopping":
                        CategoryShoppingFragment shoppingFragment = new CategoryShoppingFragment();
                        showFragment(catId, shoppingFragment);
                        break;
                    case "Movie":
                        CategoryMovieFragment movieFragment = new CategoryMovieFragment();
                        showFragment(catId, movieFragment);
                        break;
                    default:
                        CategoryDefaultFragment defaultFragment = new CategoryDefaultFragment();
                        showFragment(catId, defaultFragment);
                        break;
                }
                Log.v(TAG, name + " Selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.v(TAG, "Nothing Selected");
            }
        });
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

            case R.id.action_history:
                showHistory();
                return true;

            case R.id.action_settings:
                //todo settings
                return true;

            case R.id.action_off:
                Intent exitIntent = new Intent(this, MainActivity.class);
                exitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                exitIntent.putExtra("EXIT", true);
                startActivity(exitIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFragment(int catId, Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putInt("category", catId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }


    private void addCategory() {
        Intent intent = new Intent(NewReminderActivity.this, NewCategoryActivity.class);
        startActivity(intent);
    }


    private void showHistory(){
        Intent histIntent = new Intent(this, HistoryActivity.class);
        startActivity(histIntent);
    }
    private void showSettings(){}
    private void closeApp(){}
}
