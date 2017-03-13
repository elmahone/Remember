package com.example.remember.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.remember.model.Category;
import com.example.remember.model.Icon;
import com.example.remember.model.Reminder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataSource {
    private static final String TAG = "DataSource";

    private DatabaseHelper helper;
    private SQLiteDatabase db;

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
        values.put(DatabaseHelper.KEY_TITLE, reminder.getTitle());
        values.put(DatabaseHelper.KEY_DESC, reminder.getDescription());
        values.put(DatabaseHelper.KEY_LIST, reminder.getListString());
        values.put(DatabaseHelper.KEY_BIRTHDAY, reminder.getBirthday());
        values.put(DatabaseHelper.KEY_CAT_ID, reminder.getCategory());
        values.put(DatabaseHelper.KEY_TIME, reminder.getTime());
        long reminder_id = db.insert(DatabaseHelper.TABLE_REMINDER, null, values);

        return reminder_id;
    }

    //Fetch a reminder
    public Reminder getReminder(int rem_id) {
        open();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_REMINDER + " WHERE "
                + DatabaseHelper.KEY_ID + " = " + rem_id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Reminder reminder = new Reminder();
        reminder.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
        reminder.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)));
        reminder.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESC)));
        reminder.setList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_LIST)));
        reminder.setBirthday(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_BIRTHDAY)));
        reminder.setCategory(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_ID)));
        reminder.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));
        return reminder;
    }

    //Fetch all reminders
    public List<Reminder> getAllReminders() {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_REMINDER
                + " ORDER BY " + DatabaseHelper.KEY_TIME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESC)));
                reminder.setList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_LIST)));
                reminder.setBirthday(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_BIRTHDAY)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));
                reminders.add(reminder);

            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Fetch all future reminders
    public List<Reminder> getAllFutureReminders(long current) {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_REMINDER + " WHERE " + DatabaseHelper.KEY_TIME + " >= " + current
                + " ORDER BY " + DatabaseHelper.KEY_TIME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESC)));
                reminder.setList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_LIST)));
                reminder.setBirthday(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_BIRTHDAY)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));
                reminders.add(reminder);

            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Fetch all past reminders
    public List<Reminder> getAllPastReminders(long current) {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_REMINDER + " WHERE " + DatabaseHelper.KEY_TIME + " < " + current
                + " ORDER BY " + DatabaseHelper.KEY_TIME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESC)));
                reminder.setList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_LIST)));
                reminder.setBirthday(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_BIRTHDAY)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));
                reminders.add(reminder);

            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Fetch all future reminders between dates
    public List<Reminder> getAllFutureRemindersBetweenDates(long current, long end) {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_REMINDER
                + " WHERE " + DatabaseHelper.KEY_TIME + " >= " + current
                + " AND " + DatabaseHelper.KEY_TIME + " < " + end
                + " ORDER BY " + DatabaseHelper.KEY_TIME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESC)));
                reminder.setList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_LIST)));
                reminder.setBirthday(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_BIRTHDAY)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));
                reminders.add(reminder);

            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Fetch all future reminders with given category
    public List<Reminder> getAllFutureRemindersWithCategory(long cat_id, long current) {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_REMINDER
                + " WHERE " + DatabaseHelper.KEY_CAT_ID + " = " + cat_id
                + " AND " + DatabaseHelper.KEY_TIME + " >= " + current
                + " OR " + DatabaseHelper.KEY_CAT_ID + " = 6"
                + " AND " + DatabaseHelper.KEY_TIME + " >= " + current
                + " ORDER BY " + DatabaseHelper.KEY_TIME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();

                reminder.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESC)));
                reminder.setList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_LIST)));
                reminder.setBirthday(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_BIRTHDAY)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));

                reminders.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Fetch all future reminders with given category
    public List<Reminder> getAllPastRemindersWithCategory(long cat_id, long current) {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_REMINDER
                + " WHERE " + DatabaseHelper.KEY_CAT_ID + " = " + cat_id
                + " AND " + DatabaseHelper.KEY_TIME + " < " + current
                + " OR " + DatabaseHelper.KEY_CAT_ID + " = 6"
                + " AND " + DatabaseHelper.KEY_TIME + " < " + current
                + " ORDER BY " + DatabaseHelper.KEY_TIME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();

                reminder.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESC)));
                reminder.setList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_LIST)));
                reminder.setBirthday(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_BIRTHDAY)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));

                reminders.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Fetch all future reminders between dates with given category
    public List<Reminder> getAllFutureRemindersWithCategoryBetweenDates(long cat_id, long current, long end) {
        open();
        List<Reminder> reminders = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_REMINDER
                + " WHERE " + DatabaseHelper.KEY_CAT_ID + " = " + cat_id
                + " AND " + DatabaseHelper.KEY_TIME + " >= " + current
                + " AND " + DatabaseHelper.KEY_TIME + " < " + end
                + " OR " + DatabaseHelper.KEY_CAT_ID + " = 6"
                + " AND " + DatabaseHelper.KEY_TIME + " >= " + current
                + " AND " + DatabaseHelper.KEY_TIME + " < " + end
                + " ORDER BY " + DatabaseHelper.KEY_TIME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();

                reminder.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                reminder.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)));
                reminder.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESC)));
                reminder.setList(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_LIST)));
                reminder.setBirthday(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_BIRTHDAY)));
                reminder.setCategory(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_ID)));
                reminder.setTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));

                reminders.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminders;
    }

    //Update a reminder
    public int updateReminder(Reminder reminder) {
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TITLE, reminder.getTitle());
        values.put(DatabaseHelper.KEY_DESC, reminder.getDescription());
        values.put(DatabaseHelper.KEY_LIST, reminder.getListString());
        values.put(DatabaseHelper.KEY_BIRTHDAY, reminder.getBirthday());
        values.put(DatabaseHelper.KEY_CAT_ID, reminder.getCategory());
        values.put(DatabaseHelper.KEY_TIME, reminder.getTime());

        return db.update(DatabaseHelper.TABLE_REMINDER, values, DatabaseHelper.KEY_ID + " = ?",
                new String[]{String.valueOf(reminder.getId())});
    }

    //Delete all past reminders
    public void deletePastReminders() {
        open();
        List<Reminder> reminders = getAllPastReminders(Calendar.getInstance().getTimeInMillis());
        for (Reminder re : reminders) {
            db.delete(DatabaseHelper.TABLE_REMINDER, DatabaseHelper.KEY_ID + " = ?", new String[]{String.valueOf(re.getId())});
        }
    }

    //Delete a reminder
    public void deleteReminder(long reminder_id) {
        open();
        db.delete(DatabaseHelper.TABLE_REMINDER, DatabaseHelper.KEY_ID + " = ?", new String[]{String.valueOf(reminder_id)});
    }

    //Change reminders category
    public int updateReminderCategory(long id, long category_id) {
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_CAT_ID, category_id);

        // updating row
        return db.update(DatabaseHelper.TABLE_REMINDER, values, DatabaseHelper.KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }
    //endregion

    //Fetch all icons
    public List<Icon> getAllIcons() {
        open();
        List<Icon> icons = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_ICON;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Icon icon = new Icon();
                icon.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                icon.setIcon(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ICON)));
                icons.add(icon);

            } while (cursor.moveToNext());
        }
        return icons;
    }

    //Fetch an icon
    public Icon getIcon(long id) {
        open();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_ICON + " WHERE " + DatabaseHelper.KEY_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Icon icon = new Icon();
        icon.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
        icon.setIcon(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ICON)));

        return icon;
    }

    //region Category table CRUD functions

    //Create a category
    public long createCategory(Category category) {
        if (!existsInDb(DatabaseHelper.TABLE_CATEGORY, DatabaseHelper.KEY_CAT_NAME, category.getCategory())) {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.KEY_CAT_NAME, category.getCategory());
            values.put(DatabaseHelper.KEY_BG_COLOR, category.getBackgroundColor());
            values.put(DatabaseHelper.KEY_ICON_COLOR, category.getIconColor());
            values.put(DatabaseHelper.KEY_ICON_ID, category.getIcon());
            long category_id = db.insert(DatabaseHelper.TABLE_CATEGORY, null, values);

            return category_id;
        } else {
            return 0;
        }
    }

    //Fetch a category
    public Category getCategory(long cat_id) {
        open();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORY + " WHERE " + DatabaseHelper.KEY_ID + " = " + cat_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Category category = new Category();
        category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
        category.setCategory(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_NAME)));
        category.setBackgroundColor(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BG_COLOR)));
        category.setIconColor(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ICON_COLOR)));
        category.setIcon(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ICON_ID)));

        return category;
    }

    //Fetch all categories
    public List<Category> getAllCategories() {
        open();
        List<Category> categories = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_CATEGORY;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                category.setCategory(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CAT_NAME)));
                category.setBackgroundColor(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_BG_COLOR)));
                category.setIconColor(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_ICON_COLOR)));
                category.setIcon(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ICON_ID)));

                // adding to tags list
                categories.add(category);
            } while (cursor.moveToNext());
        }
        return categories;
    }

    //Update a category
    public int updateCategory(Category category) {
        if (!existsInDb(DatabaseHelper.TABLE_CATEGORY, DatabaseHelper.KEY_CAT_NAME, category.getCategory())) {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.KEY_CAT_NAME, category.getCategory());
            values.put(DatabaseHelper.KEY_BG_COLOR, category.getBackgroundColor());
            values.put(DatabaseHelper.KEY_ICON_COLOR, category.getIconColor());
            values.put(DatabaseHelper.KEY_ICON_ID, category.getIcon());
            return db.update(DatabaseHelper.TABLE_CATEGORY, values, DatabaseHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(category.getId())});
        } else {
            return 0;
        }
    }

    //Delete a category
    public void deleteCategory(long category_id) {
        open();
        db.delete(DatabaseHelper.TABLE_CATEGORY, DatabaseHelper.KEY_ID + " = ?", new String[]{String.valueOf(category_id)});
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
