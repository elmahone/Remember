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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.R;
import com.example.remember.adapter.IconAdapter;
import com.example.remember.database.DataSource;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.OnColorSelected;

import java.util.List;

public class NewCategoryActivity extends AppCompatActivity {
    private static final String TAG = "NewCategoryActivity";

    private DataSource dataSource;

    private final Context context = NewCategoryActivity.this;
    private ColorPicker cp1;
    private ColorPicker cp2;
    private int selectedIconPosition;

    private String selectedIconColor = "#FF000000";
    private String selectedIconBg = "#FFFFFFFF";
    private int selectedIcon;
    private List<Icon> icons;
    private RelativeLayout background;

    private ImageView iconView;
    private EditText editCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        cp1 = new ColorPicker(NewCategoryActivity.this, 128, 128, 128);
        cp2 = new ColorPicker(NewCategoryActivity.this, 128, 128, 128);

        //region Find views
        background = (RelativeLayout) findViewById(R.id.icon_background);
        Button backgroundColor = (Button) findViewById(R.id.changeBgColor);
        editCategory = (EditText) findViewById(R.id.editCategory);
        Button iconColor = (Button) findViewById(R.id.changeIconColor);
        GridView grid = (GridView) findViewById(R.id.icon_grid);
        iconView = (ImageView) findViewById(R.id.icon);
        //endregion

        dataSource = new DataSource(context);
        icons = dataSource.getAllIcons();
        IconAdapter adapter = new IconAdapter(context, icons);
        grid.setNumColumns(4);
        grid.setAdapter(adapter);

        //region Listeners

        // Background color button listener
        backgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp1.show();
                cp1.setOnColorSelected(new OnColorSelected() {
                    @Override
                    public void returnColor(int col) {
                        Log.d("hex", Integer.toHexString(col));
                        String color = "#" + Integer.toHexString(col).toUpperCase();
                        Log.v("COLOR", color);
                        background.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                                new int[]{Color.parseColor(color)}));
                        selectedIconBg = color;
                        cp1.dismiss();
                    }
                });
            }
        });
        // Icon color button listener
        iconColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp2.show();
                cp2.setOnColorSelected(new OnColorSelected() {
                    @Override
                    public void returnColor(int col) {
                        Log.d("hex", Integer.toHexString(col));
                        String color = "#" + Integer.toHexString(col).toUpperCase();
                        iconView.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                                new int[]{Color.parseColor(color)}));
                        selectedIconColor = color;
                        cp2.dismiss();

                    }
                });
            }
        });

        // Icon selection listener
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedIconPosition = position + 1;
                selectedIcon = icons.get(position).getIcon();
                iconView.setBackground(ContextCompat.getDrawable(context, selectedIcon));
            }
        });

        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        Drawable drawable = menu.findItem(R.id.action_save).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveCategory();
                return true;

            case R.id.action_history:
                showHistory();
                return true;

            case R.id.action_settings:
                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveCategory() {
        String catName = editCategory.getText().toString();
        // Make sure Category has a name and icon
        if (catName.matches("")) {
            Toast.makeText(context, "Category needs a name", Toast.LENGTH_SHORT).show();
        } else if (selectedIconPosition == 0) {
            Toast.makeText(context, "Category needs an icon", Toast.LENGTH_SHORT).show();
        } else {
            // Create and save new category
            Category category = new Category();
            category.setCategory(catName);
            category.setBackgroundColor(selectedIconBg);
            category.setIconColor(selectedIconColor);
            category.setIcon(selectedIconPosition);
            dataSource.createCategory(category);
            dataSource.close();

            // Go back to new reminder activity
            Intent i = new Intent(context, NewReminderActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }
    }

    private void showHistory() {
        Intent histIntent = new Intent(this, HistoryActivity.class);
        startActivity(histIntent);
    }

    private void showSettings() {
        Intent settingsIntent = new Intent(context, SettingsActivity.class);
        startActivity(settingsIntent);
    }
}
