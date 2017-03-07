package com.example.remember.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reminder implements Serializable {

    private int id;
    private String title;
    private String description;
    private int category_id;
    private List<String> list;

    private long birthday;
    private long time;

    //region Constructors

    public Reminder() {

    }

    public Reminder(int id, String title, String description, int category_id, long time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category_id = category_id;
        this.time = time;
    }

    public Reminder(String title, String description, int category_id, long time) {
        this.title = title;
        this.description = description;
        this.category_id = category_id;
        this.time = time;
    }

    public Reminder(String title, String description, long birthday, int category_id, long time) {
        this.title = title;
        this.description = description;
        this.birthday = birthday;
        this.category_id = category_id;
        this.time = time;
    }

    public Reminder(String title, List<String> list, int category_id, long time) {
        this.title = title;
        this.list = list;
        this.category_id = category_id;
        this.time = time;
    }

    public Reminder(String title, int category_id, long time) {
        this.title = title;
        this.category_id = category_id;
        this.time = time;
    }
    //endregion

    //region Getters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory() {
        return category_id;
    }

    public long getBirthday() {
        return birthday;
    }

    public long getTime() {
        return time;
    }

    public List<String> getList() {
        return list;
    }

    public String getListString() {
        if (list != null) {
            JSONObject json = new JSONObject();
            try {
                json.put("arrayList", new JSONArray(list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String strList = json.toString();
            return strList;
        } else {
            return null;
        }
    }

    //endregion

    //region Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(int category_id) {
        this.category_id = category_id;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setList(String strList) {
        if(strList != null) {
            JSONObject json = null;
            try {
                json = new JSONObject(strList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = json.optJSONArray("arrayList");
            List<String> list = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.list = list;
        }
    }

    //endregion

    //region Functions

    public String stringBirthDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(birthday);
        return new SimpleDateFormat("MMMM dd. yyyy").format(cal.getTime());
    }

    public String stringDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(cal.getTime());
    }
    //endregion
}
