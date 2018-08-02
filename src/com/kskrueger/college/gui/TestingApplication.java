package com.kskrueger.college.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestingApplication extends Application {
    Stage overallStage;

    @Override
    public void start(Stage stage) throws Exception {
        overallStage = stage;
        Parent parent = FXMLLoader.load(getClass().getResource("Testing.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("Testing JavaFX");
        stage.setScene(scene);
        stage.show();
    }


    public void load() {

    }

    public static void main(String[] args) {
        launch(args);
    }

}
