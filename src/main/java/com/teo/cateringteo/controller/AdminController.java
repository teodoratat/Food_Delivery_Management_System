package com.teo.cateringteo.controller;

import com.teo.cateringteo.BussinessLogic.BaseProduct;
import com.teo.cateringteo.BussinessLogic.ComposedProduct;
import com.teo.cateringteo.BussinessLogic.MenuItem;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdminController implements Initializable, CanReceiveFiltersI {
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
    private MenuButton componentsMenuButton;
    @FXML
    private MenuButton filtersMenuButton;

    @FXML
    private TextField productNameField;
    @FXML
    private TextField pathField;

    private List<MenuItem> components = new ArrayList<>();
    private List<Predicate<MenuItem>> filters = new ArrayList<>();
    private List<MenuItem> displayedItems = new ArrayList<>();

    private Stage popUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting Cell Factories
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ratingColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        caloriesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        proteinColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        fatColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        sodiumColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        //setting Cell Value Factories
        titleColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getTitle()));
        ratingColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getRating()));
        caloriesColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getCalories()));
        proteinColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getProtein()));
        fatColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getFat()));
        sodiumColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getSodium()));
        priceColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue().getPrice()));

        //setting On Edit Commit Events
        titleColumn.setOnEditCommit(editEvent -> {
            editEvent.getRowValue().setTitle(editEvent.getNewValue());
            CateringApplication.deliveryService.loadInFile();
        });
        ratingColumn.setOnEditCommit(editEvent -> {
            if (editEvent.getRowValue() instanceof BaseProduct) {
                ((BaseProduct) editEvent.getRowValue()).setRating(editEvent.getNewValue());
                CateringApplication.deliveryService.loadInFile();
            } else {
                table.refresh();
            }
        });
        caloriesColumn.setOnEditCommit(editEvent -> {
            if (editEvent.getRowValue() instanceof BaseProduct) {
                ((BaseProduct) editEvent.getRowValue()).setCalories(editEvent.getNewValue());
                CateringApplication.deliveryService.loadInFile();
            } else {
                table.refresh();
            }
        });
        proteinColumn.setOnEditCommit(editEvent -> {
            if (editEvent.getRowValue() instanceof BaseProduct) {
                ((BaseProduct) editEvent.getRowValue()).setProtein(editEvent.getNewValue());
                CateringApplication.deliveryService.loadInFile();
            } else {
                table.refresh();
            }
        });
        fatColumn.setOnEditCommit(editEvent -> {
            if (editEvent.getRowValue() instanceof BaseProduct) {
                ((BaseProduct) editEvent.getRowValue()).setFat(editEvent.getNewValue());
                CateringApplication.deliveryService.loadInFile();
            } else {
                table.refresh();
            }
        });
        sodiumColumn.setOnEditCommit(editEvent -> {
            if (editEvent.getRowValue() instanceof BaseProduct) {
                ((BaseProduct) editEvent.getRowValue()).setSodium(editEvent.getNewValue());
                CateringApplication.deliveryService.loadInFile();
            } else {
                table.refresh();
            }
        });
        proteinColumn.setOnEditCommit(editEvent -> {
            if (editEvent.getRowValue() instanceof BaseProduct) {
                ((BaseProduct) editEvent.getRowValue()).setPrice(editEvent.getNewValue());
                CateringApplication.deliveryService.loadInFile();
            } else {
                table.refresh();
            }
        });
        reload();
    }

    @FXML
    void onEmptyButtonClick() {
        components.clear();
        componentsMenuButton.getItems().clear();
    }

    @FXML
    void onAddBaseProductButtonClick() {
        String name = productNameField.getText();
        if (name.isBlank()) {
            CateringApplication.popMessage("Please insert a name.", MessageController.MessageType.ERROR);
            return;
        }
        //update delivery service and serialize it
        CateringApplication.deliveryService.addMenuItem(new BaseProduct(name, 0, 0, 0, 0, 0, 0));
        //reloads all items in the gui.
        this.reload();
        CateringApplication.popMessage("Base Product added.", MessageController.MessageType.SUCCESS);
    }

    @FXML
    void onAddSelectedButtonClick() {
        MenuItem selected = getSelectedItem();
        if (!(selected instanceof BaseProduct)) {
            CateringApplication.popMessage("The selected item is not a base product", MessageController.MessageType.ERROR);
            return;
        }
        components.add(selected);
        addEntryInComponentsMenuButton(selected.getTitle());
    }

    @FXML
    void onAddComposedProductButtonClick() {
        if (components.isEmpty()) {
            CateringApplication.popMessage("No components added.", MessageController.MessageType.ERROR);
            return;
        }

        String name = productNameField.getText();
        if (name.isBlank()) {
            CateringApplication.popMessage("Please insert a name.", MessageController.MessageType.ERROR);
            return;
        }

        ComposedProduct composedProduct = new ComposedProduct(name);
        for (MenuItem menuItem : components) {
            composedProduct.add((BaseProduct) menuItem);
        }
        CateringApplication.deliveryService.addMenuItem(composedProduct);


        this.reload();
        onEmptyButtonClick();
        CateringApplication.popMessage("Composed Product added.", MessageController.MessageType.SUCCESS);
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

    @FXML
    void onReportsClick() {
        CateringApplication.setView("reports-view.fxml", 750, 550);
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

    public void onDeleteClick() {
        MenuItem selected = getSelectedItem();
        if(selected == null) return;
        CateringApplication.deliveryService.removeMenuItem(selected);
        //refresh
        displayedItems.clear();
        displayedItems.addAll(CateringApplication.deliveryService.filterMenu(filters));
        table.refresh();
    }

    public void onImportClick() {
        if(pathField.getText().isBlank()){
            CateringApplication.popMessage("Insert the path of the cvs file", MessageController.MessageType.ERROR);
            return;
        }
        try {
            CateringApplication.deliveryService.importMenu(pathField.getText());
        } catch (IOException e) {
            CateringApplication.popMessage("File can not be accessed!", MessageController.MessageType.ERROR);
        }
        this.reload();
    }

    private void addEntryInComponentsMenuButton(String title) {
        javafx.scene.control.MenuItem fxMenuItem = new javafx.scene.control.MenuItem(title);
        componentsMenuButton.getItems().add(fxMenuItem);
    }

    private void addEntryInFilterMenuButton(String title) {
        javafx.scene.control.MenuItem fxMenuItem = new javafx.scene.control.MenuItem(title);
        filtersMenuButton.getItems().add(fxMenuItem);
    }

    private MenuItem getSelectedItem() {
        ObservableList<MenuItem> singleProduct;
        singleProduct = this.table.getSelectionModel().getSelectedItems();
        if (singleProduct.size() > 0) {
            return singleProduct.get(0);
        } else return null;
    }

    private void reload() {
        displayedItems.clear();
        displayedItems.addAll(CateringApplication.deliveryService.filterMenu());
        table.setItems(FXCollections.observableList(displayedItems));
        onRemoveAllButtonClick();
        productNameField.clear();
        table.refresh();
    }

}
