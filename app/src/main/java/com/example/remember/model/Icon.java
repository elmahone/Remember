package com.example.remember.model;

import java.io.Serializable;

public class Icon implements Serializable {
    private int id;
    private int icon;

    // Constructor
    public Icon() {
    }

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
