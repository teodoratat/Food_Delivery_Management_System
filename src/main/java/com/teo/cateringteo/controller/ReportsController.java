package com.teo.cateringteo.controller;

import com.teo.cateringteo.BussinessLogic.MenuItem;
import com.teo.cateringteo.BussinessLogic.Order;
import com.teo.cateringteo.CateringApplication;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class ReportsController {
    @FXML
    private Label reportLabel;
    @FXML
    private TextField hourStartField;
    @FXML
    private TextField hourStopField;
    @FXML
    private TextField orderedTimesField1;
    @FXML
    private TextField orderedTimesField2;
    @FXML
    private TextField moneySpentField;
    @FXML
    private DatePicker datePicker;

    @FXML
    public void onReport1Click() {
        int startHour, stopHour;
        try {
            startHour = Integer.parseInt(hourStartField.getText());
            stopHour = Integer.parseInt(hourStopField.getText());
        } catch (NumberFormatException e){
            reportLabel.setText("Format error.");
            return;
        }
        List<Map.Entry<Order, List<MenuItem>>> list = CateringApplication.deliveryService.timeIntervalReport(startHour, stopHour);
       reportLabel.setText(getReport1String(list));
    }
    @FXML
    public void onReport2Click(){
        int times;
        try {
            times = Integer.parseInt(orderedTimesField1.getText());
        } catch (NumberFormatException e){
            reportLabel.setText("Format error.");
            return;
        }
        List<MenuItem> list  = CateringApplication.deliveryService.orderedManyTimes(times);
        reportLabel.setText(getReport2String(list));
    }
    @FXML
    public void onReport3Click(){
        int times, money;
        try {
            times = Integer.parseInt(orderedTimesField2.getText());
            money = Integer.parseInt(moneySpentField.getText());
        } catch (NumberFormatException e){
            reportLabel.setText("Format error.");
            return;
        }
        List<Pair<String, Integer>> list =
                CateringApplication.deliveryService.usersWhoFrequentlyOrderALot(times, money);
        reportLabel.setText(getReport3String(list));
    }
    @FXML
    public void onReport4Click(){
        LocalDate date = datePicker.getValue();
        if(date == null){
            reportLabel.setText("Not a valid date.");
            return;
        }
        List<Pair<MenuItem, Integer>> list = CateringApplication.deliveryService.perDayStatistic(date);
        reportLabel.setText(getReport4String(list));
    }

    private String getReport4String(List<Pair<MenuItem, Integer>> list) {
        StringBuilder stringBuilder = new StringBuilder("Per day statistics:\n");
        for(Pair<MenuItem,Integer> pair : list){
            stringBuilder.append(pair.getKey().getTitle() + " was ordered " + pair.getValue() + " times\n");
        }
        return stringBuilder.toString();
    }

    @FXML
    public void onBackButtonClick(){
        CateringApplication.setView("admin-view.fxml", 750, 550);
    }

    private String getReport1String(List<Map.Entry<Order, List<MenuItem>>> list){
        StringBuilder stringBuilder = new StringBuilder("Time interval report:\n");
        for(Map.Entry<Order, List<MenuItem>> entry : list){
            Order order = entry.getKey();
            List<MenuItem> items = entry.getValue();
            stringBuilder.append(order.getId() + " " + order.getDate() + " " + order.getTime().truncatedTo(ChronoUnit.MINUTES) + " client: " + order.getClientName() + "\n [");
            for(MenuItem menuItem : items){
                stringBuilder.append(menuItem.getTitle() + ", ");
            }
            stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length()-1, " ]\n\n");
        }
        return stringBuilder.toString();
    }

    private String getReport2String(List<MenuItem> list){
        StringBuilder stringBuilder = new StringBuilder(
                "Ordered many times report:\n" +
                "The Products that were ordered more than the specified number of times are:\n"
        );
        for(MenuItem menuItem : list){
            stringBuilder.append(menuItem.getTitle() + "\n");
        }
        return stringBuilder.toString();
    }

    private String getReport3String(List<Pair<String, Integer>> list){
        StringBuilder stringBuilder = new StringBuilder("Users who frequently order a lot report:\n");
        for(Pair<String, Integer> pair : list){
            stringBuilder.append(pair.getKey() + " who spent more than the specified amount "
                    + pair.getValue() + " times.\n");
        }
        return stringBuilder.toString();
    }
}

