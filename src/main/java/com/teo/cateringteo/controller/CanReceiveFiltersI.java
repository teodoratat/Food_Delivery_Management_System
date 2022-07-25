package com.teo.cateringteo.controller;

import com.teo.cateringteo.BussinessLogic.MenuItem;

import java.util.function.Predicate;

public interface CanReceiveFiltersI {
    void receiveFilter(Predicate<MenuItem> filter);
}


