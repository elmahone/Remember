package com.example.remember;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.remember.DatabaseHelper.*;

public class DataSource {
    private static final String TAG = "DatabaseHelper";

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public DataSource(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void open() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    //region Reminder table CRUD functions

    //Create a reminder
    public long createReminder(Reminder reminder) {
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_DESC, reminder.getDescription());
        values.put(KEY_CAT_ID, reminder.getCategory());
        values.put(KEY_TIME, reminder.getTime());
        long reminder_id = db.insert(TABLE_REMINDER, null, values);

        return reminder_id;
    }

    //Fetch a reminder
    public Reminder getReminder(long rem_id) {
        open();
        String query = "SELECT * FROM " + TABLE_REMINDER + " WHERE "
                + KEY_ID + " = " + rem_id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Reminder reminder = new Reminder();
        reminder.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        reminder.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
        reminder.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESC)));
        reminder.setCategory(cursor.getInt(cursor.getColumnIndex(KEY_CAT_ID)));
        reminder.setTime(cursor.getLong(cursor.getColumnIndex(KEY_TIME)));
        return reminder;
    }

    //Fetch all reminders
    public List<Reminder> getAllReminders() {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_REMINDER;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESC)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(KEY_TIME)));
                reminders.add(reminder);

            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Fetch all reminders with given category
    public List<Reminder> getAllRemindersWithCategory(long cat_id) {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_REMINDER + " WHERE " + KEY_CAT_ID + " = " + cat_id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();

                reminder.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESC)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(KEY_TIME)));

                reminders.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Update a reminder
    public int updateReminder(Reminder reminder) {
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, reminder.getTitle());
        values.put(KEY_DESC, reminder.getDescription());
        values.put(KEY_CAT_ID, reminder.getCategory());
        values.put(KEY_TIME, reminder.getTime());

        return db.update(TABLE_REMINDER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(reminder.getId())});
    }

    //Delete a reminder
    public void deleteReminder(long reminder_id) {
        open();
        db.delete(TABLE_REMINDER, KEY_ID + " = ?", new String[]{String.valueOf(reminder_id)});
    }

    //Change reminders category
    public int updateReminderCategory(long id, long category_id) {
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_CAT_ID, category_id);

        // updating row
        return db.update(TABLE_REMINDER, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
    //endregion

    //region Category table CRUD functions

    //Create a category
    public long createCategory(Category category) {
        if (!existsInDb(TABLE_CATEGORY, KEY_CAT_NAME, category.getCategory())) {
            open();
            ContentValues values = new ContentValues();
            values.put(KEY_CAT_NAME, category.getCategory());
            values.put(KEY_BG_COLOR, category.getBackgroundColor());
            values.put(KEY_ICON_COLOR, category.getIconColor());
            values.put(KEY_ICON, category.getIcon());
            long category_id = db.insert(TABLE_CATEGORY, null, values);

            return category_id;
        } else {
            return 0;
        }
    }

    //Fetch a category
    public Category getCategory(long cat_id) {
        open();
        String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE " + KEY_ID + " = " + cat_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Category category = new Category();
        category.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        category.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CAT_NAME)));
        category.setBackgroundColor(cursor.getString(cursor.getColumnIndex(KEY_BG_COLOR)));
        category.setIconColor(cursor.getString(cursor.getColumnIndex(KEY_ICON_COLOR)));
        category.setIcon(cursor.getInt(cursor.getColumnIndex(KEY_ICON)));

        return category;
    }

    //Fetch all categories
    public List<Category> getAllCategories() {
        open();
        List<Category> categories = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                category.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CAT_NAME)));
                category.setBackgroundColor(cursor.getString(cursor.getColumnIndex(KEY_BG_COLOR)));
                category.setIconColor(cursor.getString(cursor.getColumnIndex(KEY_ICON_COLOR)));
                category.setIcon(cursor.getInt(cursor.getColumnIndex(KEY_ICON)));

                // adding to tags list
                categories.add(category);
            } while (cursor.moveToNext());
        }
        return categories;
    }

    //Update a category
    public int updateCategory(Category category) {
        if (!existsInDb(TABLE_CATEGORY, KEY_CAT_NAME, category.getCategory())) {
            open();
            ContentValues values = new ContentValues();
            values.put(KEY_CAT_NAME, category.getCategory());
            values.put(KEY_BG_COLOR, category.getBackgroundColor());
            values.put(KEY_ICON_COLOR, category.getIconColor());
            values.put(KEY_ICON, category.getIcon());
            return db.update(TABLE_CATEGORY, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(category.getId())});
        } else {
            return 0;
        }
    }

    //Delete a category
    public void deleteCategory(long category_id) {
        open();
        db.delete(TABLE_CATEGORY, KEY_ID + " = ?", new String[]{String.valueOf(category_id)});
    }
    //endregion

    //Check if given value exists in database
    private boolean existsInDb(String table, String column, String value) {
        open();
        String query = "SELECT * FROM " + table + " WHERE " + column + " = '" + value + "'";
        Log.d(TAG, query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
