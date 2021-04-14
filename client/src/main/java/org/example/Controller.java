package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Controller {
    int PlayerHitCount = 0;
    int DealerHitCount = 0;
    int upperbound = 51;
    int playerCardTotal;
    int dealerCardTotal;
    int test = 0;
    int Bust = 21;
    private int count = 0;
    private int hiddenCard = 0;

    private BlackjackClient connection;
    Random DRAW = new Random(); //instance of random class
    String drawnCard = "";
    List<Integer> cardList = new ArrayList<>();

    Image blankImage;

    //Should declare in fxml
    @FXML Label waiting;
    @FXML Label DealerTotal;
    @FXML Label PlayerTotal;
    @FXML Button P1Hit;
    @FXML Button P1Stand;


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

    //List<Label> playerLabels = new ArrayList<Label>();
    //blankImage = new Image(blankFile);

    //methods for determining card information

    //Feel like most of this should be into a different class.
    //Bloats the controller

    //Testing some code to periodicly check if both clients have pressed stand/ready
    public  javax.swing.Timer refresherTimer = null;
    public void stopRefreshing() {
        if (refresherTimer != null) {
            refresherTimer.stop();
            refresherTimer = null;
        }
    }
    protected void startRefreshing() {
        stopRefreshing();
        refresherTimer = new Timer(1000, e -> {
            //newItem.getPrice()
            if (count % 2 == 0) {
                waiting.setText("Waiting on P2..");
            } else {waiting.setText("Waiting on P2...");}
            String message = connection.sendMessage("END");
            System.out.println(message);
            if (message == "TRUE") {
                stopRefreshing();
            }
        });
        refresherTimer.start();
    }
    /*
    public void onStartButtonClicked() {
        Item newItem = new Item(newItemField.getText());
        // here newItem should be added to a list of items which should be in the ItemGUI class
        startRefreshing();
    }

    public void onStopButtonClicked() {
        stopRefreshing();
    }
    */
    public boolean determineIfFaceCard(String Letter) {
        if (Letter == "A" || Letter == "J" || Letter == "Q" || Letter == "K") {
            return true;

        } else {return false;}
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
        if (playerCardTotal > 21) {
            System.out.println("player busted");
            Main.dealerWinScreen();
        } else if (dealerCardTotal > 21) {
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
        //doesnt do anything, doesn't refrence the button declare in fxml file
        if (hitCount == 2) {
            P1Hit.setDisable(true);
        }
        return cardTotal;
    }

    public int drawCard(List<ImageView> Graphics, Label cardTotalLabel, int hitCount, int cardValue, int cardTotal, boolean hidden) {

        drawnCard = determineValue(cardValue) + determineSuit(cardValue);
        System.out.println(drawnCard);
        try {
            String currentDirectory = System.getProperty("user.dir");
            currentDirectory = currentDirectory + "/src/main/resources/PNG/";
            if (hidden){
                hiddenCard = cardValue;
                blankImage = new Image(new FileInputStream(currentDirectory + "back.png"));
            } else {blankImage = new Image(new FileInputStream(currentDirectory + drawnCard + ".png"));}
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
        //
        if (hitCount == 2) {
            P1Hit.setDisable(true);
        }
        return cardTotal;
    }

    @FXML
    public void hit(ActionEvent e) throws IOException {
        //Grab a card from the dealers deck
        String card = connection.sendMessage("HIT");
        System.out.println("Card from dealer: " + card);
        //Parse it to an int
        int playerCard = 0;
        try {
            playerCard = Integer.parseInt(card);
        } catch (NumberFormatException error) {
            System.out.println("Non-number, error: " + error);
        }

        //int playerCard = cardList.get(0);
        //cardList.remove(0);
        playerCardTotal = drawCard(playerImages, PlayerTotal, PlayerHitCount, playerCard, playerCardTotal);
        PlayerHitCount += 1;
        if (playerCardTotal > 21) {
            //gameOver();
            System.out.println("You lose!");
            //System.exit(0);
        }
    }

    @FXML
    public void ready(ActionEvent e) {
        //temp contains the randomnumberseed
        String temp = connection.sendMessage("READY");
        //turning it into a long
        long randomNumberSeed = 0;
        try {
            randomNumberSeed = Long.parseLong(temp);
        } catch (NumberFormatException error) {
            System.out.println("Seed was not a number, error: " + error.getMessage());
        }
        DRAW.setSeed(randomNumberSeed);
        shuffleDeck();

        //About to draw two cards for the dealer.
        //
        drawDealerCard(false);
        drawDealerCard(false);
    }

    public void drawDealerCard(boolean hidden) {
        int DealerDrawResult = cardList.get(0);
        cardList.remove(0);

        dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDrawResult, dealerCardTotal, hidden);
        DealerHitCount ++;

        //int DealerDraw2Result = cardList.get(0);
        //cardList.remove(0);
    }

    @FXML
    public void stand(ActionEvent e) throws IOException {
        //Player decided to stand, need to tell the server.
        //Server will recieve the users current score, ie:19 or 21...
        //Dealer will then start to hit until it reaches this number, assuming the number is <=21.

        //int to string
        String temp = Integer.toString(playerCardTotal);
        //Sends something to the server, and gets a string back.
        //String contains the
        System.out.println("Sending your score to the server.");

        String message = "";
        message = connection.sendMessage("STAND," + temp);
        System.out.println(message);

        ArrayList<String> parts = new ArrayList<>(Arrays.asList(message.split(",")));

        //P1Stand.setDisable(true);

        //If false, other player has not pressed stand
        //false
        if (parts.get(0).equals("false")) {
            //startRefreshing();
        }
        //true,STAND,10
        //or true,STAND,10,34
        //10 - dealercardtotal, 34 carddealerprevious hit with
        else if (parts.get(1).equalsIgnoreCase("STAND")) {
            if (parts.size() == 3) {
                dealerCardTotal = Integer.parseInt(parts.get(2));
                gameOver();
            } else {
                int DealerDrawResult = Integer.parseInt(parts.get(3));

                dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDrawResult, dealerCardTotal);
                //DealerHitCount++;
                dealerCardTotal = Integer.parseInt(parts.get(2));
                gameOver();
            }
        }
        //HIT,cardvalues...to display,dealerTotal
        //true,HIT,0,9
        else if (parts.get(1).equals("HIT")) {
            //int length = parts.length;
            dealerCardTotal = Integer.parseInt(parts.get(3));
            int DealerDrawResult = Integer.parseInt(parts.get(2));

            dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDrawResult, dealerCardTotal);
            //DealerHitCount++;

            gameOver();
        }
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
        //Sets blank pictures for the game
        settingBlanks();
    }

    public void shuffleDeck() {
        //DRAW is the random generator
        //Uses the same seed as server. So the decks are the same
        for (int i = 0; i < upperbound; i++) {
            cardList.add(i);
        }

        for (int i = upperbound - 1; i > 0; i--) {

            int j = DRAW.nextInt(i + 1);

            int temp = cardList.get(i);
            cardList.set(i, cardList.get(j));
            cardList.set(j, temp);
        }
    }

    public void settingBlanks() throws FileNotFoundException {
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

        //System.out.println(dealerImages.get(0));

        playerImages.add(playerImg1);
        playerImages.add(playerImg2);
        playerImages.add(playerImg3);
    }
}