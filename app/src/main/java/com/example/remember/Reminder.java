package com.example.remember;

import java.util.Date;

/**
 * Created by Black on 2017-02-15.
 */

public class Reminder {

    String title;
    String description;
    String category;

    int minute;
    int hour;
    int day;
    int month;
    int year;

    public Reminder(String title, String description, String category, int minute, int hour, int day, int month, int year) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDate() {
        String strDay = (day >= 10) ? "" + day : "0" + day;
        String strMonth = (month >= 10) ? "" + month : "0" + month;
        String strHour = (hour >= 10) ? "" + hour : "0" + hour;
        String strMinute = (minute >= 10) ? "" + minute : "0" + minute;
        return strDay + "." + strMonth + "." + year + " " + strHour + ":" + strMinute;
    }
}
