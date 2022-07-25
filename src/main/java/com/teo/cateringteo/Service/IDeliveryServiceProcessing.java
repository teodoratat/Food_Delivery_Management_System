package com.teo.cateringteo.Service;

import com.teo.cateringteo.BussinessLogic.MenuItem;
import com.teo.cateringteo.BussinessLogic.Order;
import com.teo.cateringteo.BussinessLogic.User;
import javafx.util.Pair;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface IDeliveryServiceProcessing {
    void importMenu(String filePath) throws IOException;
    void addMenuItem(MenuItem menuItem);
    void removeMenuItem(MenuItem menuItem);

    void addOrder(Order order, List<MenuItem> orderedItems);
    List<MenuItem> filterMenu(Predicate<MenuItem> ... predicates);
    List<MenuItem> filterMenu(List<Predicate<MenuItem>>  predicates);

    User logIn(String username, String password) throws LoginException;
    User signUp(User user) throws LoginException;

    List<Map.Entry<Order,List<MenuItem>>> timeIntervalReport(int startHour, int stopHour);
    List<MenuItem> orderedManyTimes(int timesOrdered);
    List<Pair<String, Integer>> usersWhoFrequentlyOrderALot(int howManyTimes, int howMuch);
    List<Pair<MenuItem, Integer>> perDayStatistic(LocalDate day);
}
