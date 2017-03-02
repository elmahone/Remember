package com.example.remember.model;

import java.io.Serializable;

public class Icon implements Serializable{
    int id;
    int icon;

    //region Constructors

    public Icon() {

    }

    public Icon(int id, int icon) {
        this.id = id;
        this.icon = icon;
    }

    public Icon(int icon) {
        this.icon = icon;
    }
    //endregion

    //region Getters

    public int getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }
    //endregion

    //region Setters

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setId(int id) {
        this.id = id;
    }
    //endregion
}
