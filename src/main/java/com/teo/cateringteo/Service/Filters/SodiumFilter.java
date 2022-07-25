package com.teo.cateringteo.Service.Filters;


import com.teo.cateringteo.BussinessLogic.MenuItem;

import java.util.function.Predicate;

/**
 * When sent to DeliveryService::filterMenu(Predicate[])
 * as one of the elements of the array of Predicates, this filter
 * will select only the MenuItems which have at the sodium range between the min and max value.
 */
public class SodiumFilter implements Predicate<MenuItem> {
    private int min, max;

    public SodiumFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean test(MenuItem menuItem) {
        return min <= menuItem.getSodium() && menuItem.getSodium() <= max;
    }

    @Override
    public String toString() {
        return "SodiumFilter(" + min + ", " + max + ") ";
    }
}
