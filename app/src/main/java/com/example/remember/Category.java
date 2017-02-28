package com.example.remember;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String category;
    private String background_color;
    private String icon_color;
    private int icon;

    //region Constructors

    public Category() {
    }

    public Category(int id, String category, String background_color, String icon_color, int icon) {
        this.id = id;
        this.category = category;
        this.background_color = background_color;
        this.icon_color = icon_color;
        this.icon = icon;
    }

    public Category(String category, String background_color, String icon_color, int icon) {
        this.category = category;
        this.background_color = background_color;
        this.icon_color = icon_color;
        this.icon = icon;
    }

    //endregion

    //region Getters

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getBackgroundColor() {
        return background_color;
    }

    public String getIconColor() {
        return icon_color;
    }

    public int getIcon() {
        return icon;
    }

//endregion

    //region Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBackgroundColor(String background_color) {
        this.background_color = background_color;
    }

    public void setIconColor(String icon_color) {
        this.icon_color = icon_color;
    }

    public void setIcon(int icon_id) {
        this.icon = icon_id;
    }

//endregion
}
