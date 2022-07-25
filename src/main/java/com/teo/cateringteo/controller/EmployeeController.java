package com.teo.cateringteo.controller;

import com.teo.cateringteo.BussinessLogic.MenuItem;
import com.teo.cateringteo.BussinessLogic.Observer;
import com.teo.cateringteo.BussinessLogic.Order;
import com.teo.cateringteo.CateringApplication;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable, Observer {
    @FXML
    private TableColumn<Map.Entry<Order, List<MenuItem>>,Integer> idColumn;
    @FXML
    private TableColumn<Map.Entry<Order, List<MenuItem>>,String> clientColumn;
    @FXML
    private TableColumn<Map.Entry<Order, List<MenuItem>>,String> dateColumn;
    @FXML
    private TableColumn<Map.Entry<Order, List<MenuItem>>,String> timeColumn;
    @FXML
    private TableColumn<Map.Entry<Order, List<MenuItem>>, MenuButton> itemsColumn;
    @FXML
    private TableView<Map.Entry<Order, List<MenuItem>>> table;

    private List<Map.Entry<Order, List<MenuItem>>> tableViewEntries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewEntries = new ArrayList<>(CateringApplication.deliveryService.getOrders().entrySet());

        idColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getKey().getId()));
        clientColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getKey().getClientName()));
        dateColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getKey().getDate().toString()));
        timeColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getKey().getTime().truncatedTo(ChronoUnit.SECONDS).toString()));
        itemsColumn.setCellValueFactory(p -> {
            MenuButton menuButton = new MenuButton("items");
            for(MenuItem menuItem : p.getValue().getValue()){
                menuButton.getItems().add(new javafx.scene.control.MenuItem(menuItem.getTitle()));
            }
            return new ReadOnlyObjectWrapper(menuButton);
        });
        table.setItems(FXCollections.observableList(tableViewEntries));
        CateringApplication.deliveryService.addObserver(this);
    }

    public void onBackButtonClick(){
        CateringApplication.setView("login-view.fxml", 600, 400);
    }

    @Override
    public void update() {
        tableViewEntries = new ArrayList<>(CateringApplication.deliveryService.getOrders().entrySet());
        table.setItems(FXCollections.observableList(tableViewEntries));
    }
}
