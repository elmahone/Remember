package com.example.remember.activity;

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
                        showBirthdayFragment(catId);
                        break;
                    case "Phone Call":
                        showPhoneCallFragment(catId);
                        break;
                    case "Important":
                        showImportantFragment(catId);
                        break;
                    case "Shopping":
                        showShoppingFragment(catId);
                        break;
                    default:
                        showDefaultFragment(catId);
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

            case R.id.action_settings:
                //todo settings
                return true;

            case R.id.action_off:
                //todo quit
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addCategory() {
        Intent intent = new Intent(NewReminderActivity.this, NewCategoryActivity.class);
        startActivity(intent);
    }

    //region showFragment functions
    private void showBirthdayFragment(int catId) {
        CategoryBirthdayFragment fragment = new CategoryBirthdayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category", catId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();

    }

    private void showPhoneCallFragment(int catId) {
    }

    private void showImportantFragment(int catId) {
        CategoryImportantFragment fragment = new CategoryImportantFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category", catId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void showShoppingFragment(int catId) {
        CategoryShoppingFragment fragment = new CategoryShoppingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category", catId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    private void showDefaultFragment(int catId) {
        CategoryDefaultFragment fragment = new CategoryDefaultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category", catId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
    //endregion
}
