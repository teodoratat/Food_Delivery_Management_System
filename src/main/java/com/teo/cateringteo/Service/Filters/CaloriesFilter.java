package com.teo.cateringteo.Service.Filters;


import com.teo.cateringteo.BussinessLogic.MenuItem;

import java.util.function.Predicate;

/**
 * When sent to DeliveryService::filterMenu(Predicate[])
 * as one of the elements of the array of Predicates, this filter
 * will select only the MenuItems which more calories the minCalories and fewer calories them maxCalories;
 */
public class CaloriesFilter implements Predicate<MenuItem> {
    private int minCalories, maxCalories;

    public CaloriesFilter(int minCalories, int maxCalories) {
        this.minCalories = minCalories;
        this.maxCalories = maxCalories;
    }

    @Override
    public boolean test(MenuItem menuItem) {
        return minCalories <= menuItem.getCalories() && menuItem.getCalories() <= maxCalories;
    }

    @Override
    public String toString() {
        return "CaloriesFilter(" + minCalories + ", " + maxCalories + ") ";
    }
}
