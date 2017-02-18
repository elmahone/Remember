package com.example.remember;

public class Category {

    private int id;
    private String category;

    //region Constructors

    public Category() {

    }

    public Category(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public Category(String category) {
        this.category = category;
    }
    //endregion

    //region Getters

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }
    //endregion

    //region Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //endregion
}
