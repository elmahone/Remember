package com.example.remember;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.OnColorSelected;

import java.util.List;

public class NewCategoryActivity extends AppCompatActivity {
    private static final String TAG = "NewCategoryActivity";
    private Context context = NewCategoryActivity.this;
    private DataSource dataSource;
    private IconAdapter adapter;
    private List<Icon> icons;
    private ColorPicker cp;

    private RelativeLayout background;
    private Button backgroundColor;
    private EditText editCategory;
    private Button saveCategory;
    private ImageView iconView;
    private Button iconColor;
    private GridView grid;

    private int selectedIconPosition;
    private String selectedIconColor = "#FF000000";
    private String selectedIconBg = "#FFFFFFFF";
    private int selectedIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        cp = new ColorPicker(NewCategoryActivity.this, 128, 128, 128);

        //region Find views
        background = (RelativeLayout) findViewById(R.id.icon_background);
        backgroundColor = (Button) findViewById(R.id.changeBgColor);
        editCategory = (EditText) findViewById(R.id.editCategory);
        saveCategory = (Button) findViewById(R.id.save_category);
        iconColor = (Button) findViewById(R.id.changeIconColor);
        grid = (GridView) findViewById(R.id.icon_grid);
        iconView = (ImageView) findViewById(R.id.icon);
        //endregion

        dataSource = new DataSource(context);
        icons = dataSource.getAllIcons();
        adapter = new IconAdapter(context, icons);
        grid.setNumColumns(4);
        grid.setAdapter(adapter);

        //region Listeners

        // Background color button listener
        backgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp.show();
                cp.setOnColorSelected(new OnColorSelected() {
                    @Override
                    public void returnColor(int col) {
                        Log.d("hex", Integer.toHexString(col));
                        String color = "#" + Integer.toHexString(col).toUpperCase();
                        Log.v("COLOR", color);
                        background.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                                new int[]{Color.parseColor(color)}));
                        selectedIconBg = color;
                        cp.dismiss();
                    }
                });
            }
        });
        // Icon color button listener
        iconColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cp.show();
                cp.setOnColorSelected(new OnColorSelected() {
                    @Override
                    public void returnColor(int col) {
                        Log.d("hex", Integer.toHexString(col));
                        String color = "#" + Integer.toHexString(col).toUpperCase();
                        iconView.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{}},
                                new int[]{Color.parseColor(color)}));
                        selectedIconColor = color;
                        cp.dismiss();

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

        // Save button listener
        saveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                    Toast.makeText(context, "Category " + catName + " created!", Toast.LENGTH_SHORT).show();

                    // Go back to new reminder activity
                    Intent i = new Intent(context, NewReminderActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                }

            }
        });
        //endregion
    }
}
