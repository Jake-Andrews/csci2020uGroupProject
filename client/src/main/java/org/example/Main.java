package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory = currentDirectory + "/src/main/resources/PNG/blank.png";
        System.out.println(currentDirectory);
        FileInputStream blankFile = new FileInputStream(currentDirectory);

        //System.out.println("PATHHH "+getClass().getResource("sample.fxml"));
        //System.out.println("COULD BE "+"/out/production/GroupAssignmnet/sample/sample.fxml");

        Parent root = FXMLLoader.load(getClass().getResource("/FXML/sample.fxml"));
        primaryStage.setTitle("Black Jack");
        //Controller ctrl = root.getController();
        //Controller controller = new Controller();
        primaryStage.setScene(new Scene(root, 800, 625));
        primaryStage.show();
    }

    public static void playerWinScreen() throws IOException {

        Parent rootWinScreen = FXMLLoader.load(Main.class.getResource("/FXML/playerwinscreen.fxml"));
        stage.setScene(new Scene(rootWinScreen, 800, 625));
        stage.setTitle("Win Screen");
    }

    public static void dealerWinScreen() throws IOException {

        Parent rootWinScreen = FXMLLoader.load(Main.class.getResource("/FXML/dealerwinscreen.fxml"));
        stage.setScene(new Scene(rootWinScreen, 800, 625));
        stage.setTitle("Win Screen");
    }

    public static void main(String[] args) {
        launch(args);
    }
}