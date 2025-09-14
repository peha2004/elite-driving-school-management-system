package org.example.elite_driving_school_management_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Appinitializer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/login.png"))));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("EliteDrive system");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
