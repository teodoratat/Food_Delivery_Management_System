package com.teo.cateringteo.BussinessLogic;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Random;

/**
 * The type Order.
 */
public class Order implements Serializable {
    public Integer id;
    public String clientName;
    public LocalDate date;
    public LocalTime time;


    /**
     * Instantiates a new Order.
     *
     * @param clientName the client name
     */
    public Order(String clientName) {
        this.id = new Random().nextInt(9999);
        this.clientName = clientName;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return clientName.equals(order.clientName) && date.equals(order.date) && time.equals(order.time);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, date, time);
    }

    @Override
    public String toString() {
        return "Order #" + this.id + " Date/time: " + this.date + " " + this.time + " by " + this.clientName;
    }
}

