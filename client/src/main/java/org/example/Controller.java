package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    int PlayerHitCount = 0;
    int DealerHitCount = 0;
    int upperbound = 51;
    int playerCardTotal;
    int dealerCardTotal;
    Image blankImage;
    int test = 0;
    private BlackjackClient connection;
    Random DRAW = new Random(); //instance of random class
    int Bust = 21;
    String drawnCard = "";
    List<Integer> cardList = new ArrayList<>();
    Label DealerTotal = new Label("Total: 0");
    Label PlayerTotal = new Label("Total: 0");
    Button P1Hit = new Button("Hit");
    Button P1Stand = new Button("Stand");
    FileInputStream blankFile;

    List<ImageView> playerImages = new ArrayList<ImageView>();
    List<ImageView> dealerImages = new ArrayList<ImageView>();

    @FXML private ImageView dealerImg1;
    @FXML private ImageView dealerImg2;
    @FXML private ImageView dealerImg3;

    @FXML private ImageView playerImg1;
    @FXML private ImageView playerImg2;
    @FXML private ImageView playerImg3;

    @FXML private Label playerTotalLabel;
    @FXML private Label dealerTotalLabel;

    @FXML
    public void sendToServer(ActionEvent event) {
        //int to string
        String temp = Integer.toString(test);
        //Sends something to the server, and gets a string back.
        System.out.println(connection.sendMessage(temp));
    }

    //List<Label> playerLabels = new ArrayList<Label>();
    //blankImage = new Image(blankFile);

    //methods for determining card information

    //Feel like most of this should be into a different class.
    //Bloats the controller
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

    public void gameOver() throws IOException {
        if (playerCardTotal > Bust) {
            System.out.println("player busted");
            Main.dealerWinScreen();
        } else if (dealerCardTotal > Bust) {
            System.out.println("dealer busted");
            Main.playerWinScreen();
        } else if (dealerCardTotal >= playerCardTotal) {
            System.out.println("dealer wins");
            Main.dealerWinScreen();
        } else if (dealerCardTotal < playerCardTotal) {
            System.out.println("player wins");
            Main.playerWinScreen();
        }
        //  System.exit(0);
    }

    public int drawCard(List<ImageView> Graphics, Label cardTotalLabel, int hitCount, int cardValue, int cardTotal) {

        drawnCard = determineValue(cardValue) + determineSuit(cardValue);
        System.out.println(drawnCard);
        try {
            String currentDirectory = System.getProperty("user.dir");
            currentDirectory = currentDirectory + "/src/main/resources/PNG/";
            blankImage = new Image(new FileInputStream(currentDirectory + drawnCard + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (determineIfFaceCard(determineValue(cardValue)) == true) {
            cardTotal += determineValueofFaceCards(determineValue(cardValue));
        } else {
            cardTotal += Integer.parseInt(determineValue(cardValue));
        }

        System.out.println(hitCount);
        Graphics.get(hitCount).setImage(blankImage);

        if (cardTotalLabel == DealerTotal) {
            dealerTotalLabel.setText("Dealer Total: " + cardTotal);
        } else {
            playerTotalLabel.setText("Player Total: " + cardTotal);
        }

        if (hitCount == 2) {
            P1Hit.setDisable(true);
        }

        return cardTotal;

    }


    public void hit(ActionEvent e) throws IOException {
        int playerCard = cardList.get(0);
        cardList.remove(0);
        playerCardTotal = drawCard(playerImages, PlayerTotal, PlayerHitCount, playerCard, playerCardTotal);
        PlayerHitCount += 1;
        if (playerCardTotal >= 21) {
            gameOver();
            //System.exit(0);
        }
    }

    public void stand(ActionEvent e) throws IOException {
        P1Stand.setDisable(true);

        int DealerDraw2Result = cardList.get(0);
        cardList.remove(0);

        dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDraw2Result, dealerCardTotal);
        DealerHitCount += 1;


        int DealerDraw3Result = cardList.get(0);
        cardList.remove(0);

        dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDraw3Result, dealerCardTotal);
        // DealerHitCount =0;
        gameOver();
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        //Initilizing the connection with the server
        //Can probably be put into another class later.
        //As most of the initialize stuff can be
        try {
            connection = BlackjackClient.connect("localhost", 8001);

            if (connection != null) {
                System.out.println("Connected");
            } else {
                System.err.println("No connection made.");
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String directory = System.getProperty("user.dir");
        directory = directory + "/src/main/resources/PNG/blank.png";
        blankFile = new FileInputStream(directory);
        blankImage = new Image(blankFile);


        dealerImg1.setImage(blankImage);
        dealerImg2.setImage(blankImage);
        dealerImg3.setImage(blankImage);

        playerImg1.setImage(blankImage);
        playerImg2.setImage(blankImage);
        playerImg3.setImage(blankImage);

        dealerImages.add(dealerImg1);
        dealerImages.add(dealerImg2);
        dealerImages.add(dealerImg3);

        System.out.println(dealerImages.get(0));

        playerImages.add(playerImg1);
        playerImages.add(playerImg2);
        playerImages.add(playerImg3);



        for (int i = 0; i < upperbound; i++) {
            cardList.add(i);
        }

        for (int i = upperbound - 1; i > 0; i--) {

            int j = DRAW.nextInt(i + 1);

            int temp = cardList.get(i);
            cardList.set(i, cardList.get(j));
            cardList.set(j, temp);
        }

        int DealerDraw1Result = cardList.get(0);
        cardList.remove(0);

        dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDraw1Result, dealerCardTotal);
        DealerHitCount += 1;

    }
}