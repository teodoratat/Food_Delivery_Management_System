package com.teo.cateringteo.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for the main window
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}