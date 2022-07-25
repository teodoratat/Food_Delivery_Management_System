package com.teo.cateringteo.controller;

import com.teo.cateringteo.BussinessLogic.User;
import com.teo.cateringteo.CateringApplication;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.security.auth.login.LoginException;

public class SignupController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void onSignupButtonClick(){
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()){
            System.out.println("FIELDS NOT FILLED");
            return;//maybe print error too
        }
        try {
            CateringApplication.deliveryService.signUp(new User(-1,usernameField.getText(), passwordField.getText(), User.AccountType.CLIENT));
            CateringApplication.setView("login-view.fxml", 600, 400);
        } catch (LoginException e) {
            System.out.println("Signup failed!");
        }

    }

    @FXML
    public void onBackClick(){
        CateringApplication.setView("login-view.fxml", 600, 400);
    }
}
