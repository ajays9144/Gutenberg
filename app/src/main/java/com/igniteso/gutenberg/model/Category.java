package com.igniteso.gutenberg.model;

import androidx.annotation.DrawableRes;

public class Category {
    private int id;
    private String categoryName;
    @DrawableRes
    private int categoryImage;

    public Category(int id, String categoryName, int categoryImage) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }
}
