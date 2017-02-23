package com.example.remember;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    //region Database details

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "rememberDB";
    //endregion

    //region Table Names
    public static final String TABLE_REMINDER = "reminders";
    public static final String TABLE_CATEGORY = "categories";
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

        ContentValues values = new ContentValues();
        values.put(KEY_CAT_NAME, "Default");
        values.put(KEY_BG_COLOR, "#AEC6CF");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON, R.drawable.ic_default);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Exercise");
        values.put(KEY_BG_COLOR, "#FF4848");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON, R.drawable.ic_exercise);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Shopping");
        values.put(KEY_BG_COLOR, "#01F33E");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON, R.drawable.ic_shopping);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Birthday");
        values.put(KEY_BG_COLOR, "#AD8BFE");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON, R.drawable.ic_birthday);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Phone Call");
        values.put(KEY_BG_COLOR, "#06DCFB");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON, R.drawable.ic_phone);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Important");
        values.put(KEY_BG_COLOR, "#DFE32D");
        values.put(KEY_ICON_COLOR, "#000000");
        values.put(KEY_ICON, R.drawable.ic_important);
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

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
        values.put(KEY_TIME, 1487768586947L);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Gym");
        values.put(KEY_DESC, "Leg day");
        values.put(KEY_CAT_ID, 2);
        values.put(KEY_TIME, 1487868586947L);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Look for jobs");
        values.put(KEY_DESC, "McDonalds, Burger King, etc.");
        values.put(KEY_CAT_ID, 1);
        values.put(KEY_TIME, 1487758586947L);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        // create new tables
        onCreate(db);
    }
}
