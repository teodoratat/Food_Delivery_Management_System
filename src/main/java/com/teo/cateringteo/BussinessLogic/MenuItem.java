package com.teo.cateringteo.BussinessLogic;


import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    private String title;

    public MenuItem() {
    }

    public MenuItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract float getRating();
    public abstract int getCalories();
    public abstract int getProtein();
    public abstract int getFat();
    public abstract int getSodium();
    public abstract int getPrice();
    public abstract String toString();
}
