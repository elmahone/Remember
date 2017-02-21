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
    private static final int DATABASE_VERSION = 1;

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

    //region REMINDER Table - column nmaes
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESC = "description";
    public static final String KEY_CAT_ID = "category_id";

    public static final String KEY_MIN = "minute";
    public static final String KEY_HOUR = "hour";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";
    public static final String KEY_YEAR = "year";
    //endregion

    //region CATEGORY Table - column names
    public static final String KEY_CAT_NAME = "category_name";
    public static final String KEY_CAT_COLOR = "category_color";
    //endregion

    //region Table Create Statements
    // Reminder table create statement
    private static final String CREATE_TABLE_REMINDER = "CREATE TABLE "
            + TABLE_REMINDER + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT, "
            + KEY_DESC + " TEXT, " + KEY_CAT_ID + " INTEGER, "
            + KEY_MIN + " INTEGER, " + KEY_HOUR + " INTEGER, "
            + KEY_DAY + " INTEGER, " + KEY_MONTH + " INTEGER, "
            + KEY_YEAR + " INTEGER" + ")";

    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
            + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_CAT_NAME + " TEXT, "
            + KEY_CAT_COLOR + " TEXT)";
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
        values.put(KEY_CAT_COLOR, "#AEC6CF");
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Exercise");
        values.put(KEY_CAT_COLOR, "#FF4848");
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_CAT_NAME, "Shopping");
        values.put(KEY_CAT_COLOR, "#01F33E");
        db.insert(TABLE_CATEGORY, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Janus's birthday");
        values.put(KEY_DESC, "40 v");
        values.put(KEY_CAT_ID, 1);
        values.put(KEY_MIN, 0);
        values.put(KEY_HOUR, 12);
        values.put(KEY_DAY, 2);
        values.put(KEY_MONTH, 5);
        values.put(KEY_YEAR, 2017);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Go buy pants");
        values.put(KEY_DESC, "");
        values.put(KEY_CAT_ID, 3);
        values.put(KEY_MIN, 20);
        values.put(KEY_HOUR, 16);
        values.put(KEY_DAY, 7);
        values.put(KEY_MONTH, 4);
        values.put(KEY_YEAR, 2017);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Gym");
        values.put(KEY_DESC, "Leg day");
        values.put(KEY_CAT_ID, 2);
        values.put(KEY_MIN, 0);
        values.put(KEY_HOUR, 18);
        values.put(KEY_DAY, 30);
        values.put(KEY_MONTH, 3);
        values.put(KEY_YEAR, 2017);
        db.insert(TABLE_REMINDER, null, values);
        values.clear();

        values = new ContentValues();
        values.put(KEY_TITLE, "Look for jobs");
        values.put(KEY_DESC, "McDonalds, Burger King, etc.");
        values.put(KEY_CAT_ID, 1);
        values.put(KEY_MIN, 0);
        values.put(KEY_HOUR, 9);
        values.put(KEY_DAY, 20);
        values.put(KEY_MONTH, 5);
        values.put(KEY_YEAR, 2017);
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
