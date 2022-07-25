package com.teo.cateringteo.controller;

import com.teo.cateringteo.BussinessLogic.User;
import com.teo.cateringteo.CateringApplication;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.security.auth.login.LoginException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void onLoginButtonClick(){
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()){
            System.out.println("FIELDS NOT FILLED");
            return;//maybe print error too
        }
        User currentUser;
        try {
            currentUser = CateringApplication.deliveryService.logIn(
                    usernameField.getText(),
                    passwordField.getText()
            );
        } catch (LoginException e) {
            System.out.println("USER NOT FOUND");
            return; //print user not found
        }

        CateringApplication.currentUser = currentUser;
        switch (currentUser.getType()){
            case ADMIN -> CateringApplication.setView("admin-view.fxml", 750, 550);
            case CLIENT -> CateringApplication.setView("client-view.fxml", 750, 550);
            case EMPLOYEE -> CateringApplication.setView("employee-view.fxml", 750, 550);
        }
    }

    @FXML
    public void onSignUpButtonClick(){
        CateringApplication.setView("signup-view.fxml", 600, 400);
    }
}
