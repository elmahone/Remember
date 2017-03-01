package com.example.remember.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.remember.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    //region Database details

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "rememberDB";
    //endregion

    //region Table Names
    public static final String TABLE_REMINDER = "reminders";
    public static final String TABLE_CATEGORY = "categories";
    public static final String TABLE_ICON = "icons";
    //endregion

    //region Common column names
    public static final String KEY_ID = "id";
    //endregion

    //region REMINDER Table - column names
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESC = "description";
    public static final String KEY_CAT_ID = "category_id";
    public static final String KEY_TIME = "time";

    //endregion

    //region CATEGORY Table - column names
    public static final String KEY_CAT_NAME = "category_name";
    public static final String KEY_BG_COLOR = "background_color";
    public static final String KEY_ICON_COLOR = "icon_color";
    public static final String KEY_ICON_ID = "icon_id";
    //endregion

    //region ICON table - column names
    public static final String KEY_ICON = "icon";
    //endregion

    //region Table Create Statements
    // Reminder table create statement
    private static final String CREATE_TABLE_REMINDER = "CREATE TABLE "
            + TABLE_REMINDER + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT, "
            + KEY_DESC + " TEXT, " + KEY_CAT_ID + " INTEGER, "
            + KEY_TIME + " INTEGER" + ")";

    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_CAT_NAME + " TEXT, "
            + KEY_BG_COLOR + " TEXT, "
            + KEY_ICON_COLOR + " TEXT, "
            + KEY_ICON_ID + " INTEGER)";

    private static final String CREATE_TABLE_ICON = "CREATE TABLE " + TABLE_ICON + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ICON + " INTEGER)";
    //endregion

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        Log.v(TAG, CREATE_TABLE_CATEGORY);
        Log.v(TAG, CREATE_TABLE_REMINDER);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_REMINDER);
        db.execSQL(CREATE_TABLE_ICON);

        //region Create default icons
        createIcon(db, R.drawable.ic_default);
        createIcon(db, R.drawable.ic_exercise);
        createIcon(db, R.drawable.ic_shopping);
        createIcon(db, R.drawable.ic_birthday);
        createIcon(db, R.drawable.ic_phone);
        createIcon(db, R.drawable.ic_important);
        createIcon(db, R.drawable.ic_bar);
        createIcon(db, R.drawable.ic_bike);
        createIcon(db, R.drawable.ic_cafe);
        createIcon(db, R.drawable.ic_cut);
        createIcon(db, R.drawable.ic_flight);
        createIcon(db, R.drawable.ic_group);
        createIcon(db, R.drawable.ic_hospital);
        createIcon(db, R.drawable.ic_location);
        createIcon(db, R.drawable.ic_mail);
        createIcon(db, R.drawable.ic_money);
        createIcon(db, R.drawable.ic_movies);
        //endregion

        //region Create default categories
        ContentValues values = new ContentValues();
        values.put(KEY_CAT_NAME, "Default");
        values.put(KEY_BG_COLOR, "#AEC6CF");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON_ID, 1);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Exercise");
        values.put(KEY_BG_COLOR, "#FF4848");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON_ID, 2);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Shopping");
        values.put(KEY_BG_COLOR, "#01F33E");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON_ID, 3);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Birthday");
        values.put(KEY_BG_COLOR, "#AD8BFE");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON_ID, 4);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Phone Call");
        values.put(KEY_BG_COLOR, "#06DCFB");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON_ID, 5);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Important");
        values.put(KEY_BG_COLOR, "#DFE32D");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON_ID, 6);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();
        //endregion

        //region Create test reminders
        values = new ContentValues();
        values.put(KEY_TITLE, "Janus's birthday");
        values.put(KEY_DESC, "40 v");
        values.put(KEY_CAT_ID, 4);

        values.put(KEY_TIME, 1487766586947L);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Go buy pants");
        values.put(KEY_DESC, "");
        values.put(KEY_CAT_ID, 3);
        values.put(KEY_TIME, 1488946937958L);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Gym");
        values.put(KEY_DESC, "Leg day");
        values.put(KEY_CAT_ID, 2);
        values.put(KEY_TIME, 1489346937958L);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Look for jobs");
        values.put(KEY_DESC, "McDonalds, Burger King, etc.");
        values.put(KEY_CAT_ID, 1);
        values.put(KEY_TIME, 1487999937958L);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();
        //endregion
    }

    private void createIcon(SQLiteDatabase db, int icon) {
        ContentValues values = new ContentValues();
        values.put(KEY_ICON, icon);
        db.insert(TABLE_ICON, null, values);
        values.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ICON);

        // create new tables
        onCreate(db);
    }
}
