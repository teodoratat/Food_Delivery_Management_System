package com.teo.cateringteo.BussinessLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to notify the employee about a new order
 *
 */
public class Observable {
    List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    public void notifyObservers(){
        observers.forEach(Observer::update);
    }
}
