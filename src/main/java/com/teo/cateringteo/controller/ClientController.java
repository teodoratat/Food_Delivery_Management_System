package com.teo.cateringteo.controller;

import com.teo.cateringteo.BussinessLogic.MenuItem;
import com.teo.cateringteo.BussinessLogic.Order;
import com.teo.cateringteo.CateringApplication;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ClientController implements Initializable, CanReceiveFiltersI {
    @FXML
    private TableView<MenuItem> table;
    @FXML
    private TableColumn<MenuItem, String> titleColumn;
    @FXML
    private TableColumn<MenuItem, Float> ratingColumn;
    @FXML
    private TableColumn<MenuItem, Integer> caloriesColumn;
    @FXML
    private TableColumn<MenuItem, Integer> proteinColumn;
    @FXML
    private TableColumn<MenuItem, Integer> fatColumn;
    @FXML
    private TableColumn<MenuItem, Integer> sodiumColumn;
    @FXML
    private TableColumn<MenuItem, Integer> priceColumn;

    @FXML
    private MenuButton cartMenuButton;
    @FXML
    private MenuButton filtersMenuButton;

    private List<MenuItem> cart = new ArrayList<>();
    private List<Predicate<MenuItem>> filters = new ArrayList<>();
    private List<MenuItem> displayedItems = new ArrayList<>();

    private Stage popUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getTitle()));
        ratingColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getRating()));
        caloriesColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getCalories()));
        proteinColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getProtein()));
        fatColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getFat()));
        sodiumColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getSodium()));
        priceColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getPrice()));
        displayedItems.addAll(CateringApplication.deliveryService.filterMenu());
        table.setItems(FXCollections.observableList(displayedItems));
    }

    @FXML
    void onEmptyButtonClick() {
        cart.clear();
        cartMenuButton.getItems().clear();
    }

    @FXML
    void onPlaceOrderButtonClick() {
        if (cart.isEmpty()) {
            CateringApplication.popMessage("The cart is empty.", MessageController.MessageType.ERROR);
            return;
        }
        Order order = new Order(CateringApplication.currentUser.getUsername());
        CateringApplication.deliveryService.addOrder(order, cart);
        popBillMessage();

        cart.clear();
        cartMenuButton.getItems().clear();
    }

    @FXML
    void onAddToCartButtonClick() {
        MenuItem selectedItem = getSelectedItem();
        if (selectedItem == null) return;
        cart.add(selectedItem);
        addEntryInCartMenuButton(selectedItem.getTitle());
        CateringApplication.popMessage("Item added.", MessageController.MessageType.SUCCESS);
    }

    @FXML
    void onRemoveAllButtonClick() {
        filters.clear();
        displayedItems.clear();
        displayedItems.addAll(CateringApplication.deliveryService.filterMenu());
        filtersMenuButton.getItems().clear();
        table.refresh();
    }

    @FXML
    void onAddFilterButtonClick() throws IOException {
        if (popUp != null) {
            this.popUp.close();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(CateringApplication.class.getResource("select-filter-view.fxml"));
        Parent rootNode = (Parent) fxmlLoader.load();
        SelectFilterController controller = fxmlLoader.<SelectFilterController>getController();
        controller.setParent(this);
        Scene scene = new Scene(rootNode, 300, 180);
        Stage popUpStage = new Stage();
        this.popUp = popUpStage;
        popUpStage.setScene(scene);
        popUpStage.show();
    }

    @FXML
    void onBackButtonClick() {
        if (popUp != null) popUp.close();
        CateringApplication.setView("login-view.fxml", 600, 400);
    }

    @Override
    public void receiveFilter(Predicate<MenuItem> filter) {
        filters.add(filter);
        displayedItems.clear();
        displayedItems.addAll(CateringApplication.deliveryService.filterMenu(filters));
        table.refresh();

        addEntryInFilterMenuButton(filter.toString());

        this.popUp.close();
        this.popUp = null;
    }

    private void addEntryInCartMenuButton(String title) {
        javafx.scene.control.MenuItem fxMenuItem = new javafx.scene.control.MenuItem(title);
        cartMenuButton.getItems().add(fxMenuItem);
    }

    private void addEntryInFilterMenuButton(String title) {
        javafx.scene.control.MenuItem fxMenuItem = new javafx.scene.control.MenuItem(title);
        filtersMenuButton.getItems().add(fxMenuItem);
    }

    private void reload() {
        displayedItems.clear();
        displayedItems.addAll(CateringApplication.deliveryService.filterMenu());
        table.setItems(FXCollections.observableList(displayedItems));
        onRemoveAllButtonClick();
        table.refresh();
    }

    private MenuItem getSelectedItem() {
        ObservableList<MenuItem> singleProduct;
        singleProduct = this.table.getSelectionModel().getSelectedItems();
        if (singleProduct.size() > 0) {
            return singleProduct.get(0);
        } else return null;
    }

    private void popBillMessage(){
        StringBuilder stringBuilder = new StringBuilder("Order completed successfully!\n\n");
        int price = 0;
        for(MenuItem menuItem : cart){
            stringBuilder.append(menuItem.getPrice() + "$ ");
            stringBuilder.append(".......");
            stringBuilder.append(menuItem.getTitle());
            stringBuilder.append("\n");
            price += menuItem.getPrice();
        }
        stringBuilder.append("________________\n");
        stringBuilder.append("TOTAL: " + price + "$");

        MessageController controller =
                CateringApplication.popMessage(stringBuilder.toString(), MessageController.MessageType.INFORMATION, 250);
    }
}

