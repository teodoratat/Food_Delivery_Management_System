package com.teo.cateringteo.Service.Filters;


import com.teo.cateringteo.BussinessLogic.MenuItem;

import java.util.function.Predicate;
/**
 * When sent to DeliveryService::filterMenu(Predicate[])
 * as one of the elements of the array of Predicates, this filter
 * will select only the MenuItems which have at the fat range between the min and max value.
 */
public class FatFilter implements Predicate<MenuItem> {
    private int min, max;

    public FatFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean test(MenuItem menuItem) {
        return min <= menuItem.getFat() && menuItem.getFat() <= max;
    }

    @Override
    public String toString() {
        return "FatFilter(" + min + ", " + max + ") ";
    }
}
