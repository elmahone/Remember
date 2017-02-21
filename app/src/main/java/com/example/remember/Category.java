package com.example.remember;

public class Category {
    private int id;
    private String category;
    private String color;

    //region Constructors

    public Category() {

    }

    public Category(int id, String category, String color) {
        this.id = id;
        this.category = category;
        this.color = color;
    }

    public Category(String category, String color) {
        this.category = category;
        this.color = color;
    }
    //endregion

    //region Getters

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    //endregion

    //region Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //endregion
}
