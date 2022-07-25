package com.teo.cateringteo.controller;

import com.teo.cateringteo.BussinessLogic.MenuItem;
import com.teo.cateringteo.Service.Filters.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SelectFilterController implements Initializable {
    @FXML
    private ComboBox<String> filterTypeComboBox;
    @FXML
    private TextField keyWordsTextField;
    @FXML
    private Spinner<Integer> minSpinner;
    @FXML
    private Spinner<Integer> maxSpinner;

    private CanReceiveFiltersI parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] filterTypes = {"Key words", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
        filterTypeComboBox.getItems().addAll(filterTypes);
        filterTypeComboBox.setValue("Key words");
        minSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3000, 0));
        maxSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3000, 500));
        minSpinner.setVisible(false);
        maxSpinner.setVisible(false);
    }

    @FXML
    public void onAddFilterButtonClick(){
        Predicate<MenuItem> filter;
        if(filterTypeComboBox.getValue().equals("Key words") && keyWordsTextField.getText().isBlank()){
            return;
        } else if(minSpinner.getValue() == null || maxSpinner.getValue() == null || maxSpinner.getValue() < minSpinner.getValue()){
            return;
        }

        if(parent != null){
            filter = switch (filterTypeComboBox.getValue()){
                case "Key words" -> new KeyWordsFilter(keyWordsTextField.getText().split(" "));
                case "Rating" -> new RatingFilter(minSpinner.getValue(), maxSpinner.getValue());
                case "Calories" -> new CaloriesFilter(minSpinner.getValue(), maxSpinner.getValue());
                case "Protein" -> new ProteinFilter(minSpinner.getValue(), maxSpinner.getValue());
                case "Fat" -> new FatFilter(minSpinner.getValue(), maxSpinner.getValue());
                case "Sodium" -> new SodiumFilter(minSpinner.getValue(), maxSpinner.getValue());
                case "Price" -> new PriceFilter(minSpinner.getValue(), maxSpinner.getValue());
                default -> throw new IllegalStateException("Unexpected value: " + filterTypeComboBox.getValue());
            };
            parent.receiveFilter(filter);
        }
    }

    public void typeSelected(){
        if(filterTypeComboBox.getValue().equals("Key words")){
            minSpinner.setVisible(false);
            maxSpinner.setVisible(false);
            keyWordsTextField.setVisible(true);
        } else{
            minSpinner.setVisible(true);
            maxSpinner.setVisible(true);
            keyWordsTextField.setVisible(false);
        }
    }
    public void setParent(CanReceiveFiltersI parent) {
        this.parent = parent;
    }
}
