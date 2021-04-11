package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main extends Application {
    private static Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory = currentDirectory + "/GroupAssignmnet/src/resources/PNG/blank.png";
        System.out.println(currentDirectory);
        FileInputStream blankFile = new FileInputStream(currentDirectory);

        //System.out.println("PATHHH "+getClass().getResource("sample.fxml"));
        //System.out.println("COULD BE "+"/out/production/GroupAssignmnet/sample/sample.fxml");

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Black Jack");
        //Controller ctrl = root.getController();
        //Controller controller = new Controller();
        primaryStage.setScene(new Scene(root, 800, 625));
        primaryStage.show();

    }

    public static void playerWinScreen() throws IOException {

        Parent rootWinScreen = FXMLLoader.load(Main.class.getResource("playerwinscreen.fxml"));
        stage.setScene(new Scene(rootWinScreen, 800, 625));
        stage.setTitle("Win Screen");

    }

    public static void dealerWinScreen() throws IOException {

        Parent rootWinScreen = FXMLLoader.load(Main.class.getResource("dealerwinscreen.fxml"));
        stage.setScene(new Scene(rootWinScreen, 800, 625));
        stage.setTitle("Win Screen");

    }



    public static void main(String[] args) {
        launch(args);
    }
/*
    public boolean determineIfFaceCard(String Letter) {
        if (Letter == "A" || Letter == "J" || Letter == "Q" || Letter == "K") {
            return true;

        } else return false;
    }

    public String determineSuit(int cardValue) {
        if (cardValue % 4 == 0) {
            return "C";
        } else if (cardValue % 4 == 1) {
            return "D";
        } else if (cardValue % 4 == 2) {
            return "H";
        } else if (cardValue % 4 == 3) {
            return "S";
        } else return "no card found #math error";
    }

    public String determineValue(int cardValue) {
        if (cardValue % 13 == 0) {
            return "2";
        } else if (cardValue % 13 == 1) {
            return "3";
        } else if (cardValue % 13 == 2) {
            return "4";
        } else if (cardValue % 13 == 3) {
            return "5";
        } else if (cardValue % 13 == 4) {
            return "6";
        } else if (cardValue % 13 == 5) {
            return "7";
        } else if (cardValue % 13 == 6) {
            return "8";
        } else if (cardValue % 13 == 7) {
            return "9";
        } else if (cardValue % 13 == 8) {
            return "10";
        } else if (cardValue % 13 == 9) {
            return "A";
        } else if (cardValue % 13 == 10) {
            return "J";
        } else if (cardValue % 13 == 11) {
            return "Q";
        } else if (cardValue % 13 == 12) {
            return "K";
        } else return "no card found #math error";
    }

    public int determineValueofFaceCards(String Symbol) {
        if (Symbol == "A") {
            return 1;
        } else if (Symbol == "J" || Symbol == "Q" || Symbol == "K") {
            return 10;
        } else {
            System.out.println("error in face value determiniign");
            return 0;
        }

    }

    public void gameOver() {
        if (playerCardTotal > Bust) {
            System.out.println("player busted");
        } else if (dealerCardTotal > Bust) {
            System.out.println("dealer busted");
        } else if (dealerCardTotal >= playerCardTotal) {
            System.out.println("dealer wins");
        } else if (dealerCardTotal < playerCardTotal) {
            System.out.println("player wins");
        }
        //  System.exit(0);
    }

    public int drawCard(List<ImageView> Graphics, Label cardTotalLabel, int hitCount, int cardValue, int cardTotal) {

        drawnCard = determineValue(cardValue) + determineSuit(cardValue);
        System.out.println(drawnCard);
        try {
            blankImage = new Image(new FileInputStream("src/resources/PNG/" + drawnCard + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (determineIfFaceCard(determineValue(cardValue)) == true) {
            cardTotal += determineValueofFaceCards(determineValue(cardValue));
        } else {
            cardTotal += Integer.parseInt(determineValue(cardValue));
        }
        
        
        Graphics.get(hitCount).setImage(blankImage);
        
        if (cardTotalLabel == DealerTotal) {
            DealerTotal.setText("Total: " + cardTotal);
        } else {
            PlayerTotal.setText("Total: " + cardTotal);
        }

        if (hitCount == 2) {
            P1Hit.setDisable(true);
        }

        return cardTotal;

    }*/

}


