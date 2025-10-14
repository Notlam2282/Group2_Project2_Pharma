package com.example.pharmacy;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PharmacyManaSys.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Pharmacy Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}