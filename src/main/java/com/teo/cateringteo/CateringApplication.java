package com.teo.cateringteo;

import com.teo.cateringteo.BussinessLogic.User;
import com.teo.cateringteo.Service.DeliveryService;
import com.teo.cateringteo.controller.MessageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CateringApplication extends Application {
    public static DeliveryService deliveryService = new DeliveryService();
    public static Stage stage;
    public static User currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        CateringApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(CateringApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws IOException {
        deliveryService.loadFromFile();
        launch();
    }

    public static <T> T setView(String view, int width, int height) {
        T controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(CateringApplication.class.getResource(view));
            Parent rootNode = (Parent) fxmlLoader.load();
            controller = fxmlLoader.<T>getController();
            Scene scene = new Scene(rootNode, width, height);
            CateringApplication.stage.setScene(scene);
            CateringApplication.stage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MessageController popMessage(String message, MessageController.MessageType type) {
        return popMessage(message, type, 75);
    }

    public static MessageController popMessage(String message, MessageController.MessageType type, int height) {
        try {
            Stage popUpStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(CateringApplication.class.getResource("message-view.fxml"));
            Parent rootNode = (Parent) fxmlLoader.load();
            MessageController controller = fxmlLoader.<MessageController>getController();
            controller.set(message, type);
            controller.setHeight(height);
            popUpStage.setScene(new Scene(rootNode, 300, height));
            popUpStage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}