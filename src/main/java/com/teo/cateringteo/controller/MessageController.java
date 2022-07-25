package com.teo.cateringteo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MessageController {
    public enum MessageType {
        INFORMATION,
        ERROR,
        SUCCESS
    };

    @FXML
    private Label messageLabel;
    @FXML
    private HBox root;

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void setType(MessageType mType) {
        switch (mType) {
            case INFORMATION -> messageLabel.setTextFill(Color.BLACK);
            case ERROR -> messageLabel.setTextFill(Color.RED);
            case SUCCESS -> messageLabel.setTextFill(Color.GREEN);
        }
    }

    public void set(String message, MessageType mType){
        this.setMessage(message);
        this.setType(mType);
    }

    public void setHeight(int height){
        root.setPrefHeight(height);
    }
}
