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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Controller {
    int PlayerHitCount = 0;
    int DealerHitCount = 0;
    int upperbound = 51;
    int playerCardTotal;
    int dealerCardTotal;

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

    List<ImageView> playerImages = new ArrayList<>();
    List<ImageView> dealerImages = new ArrayList<>();

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

    public boolean determineIfFaceCard(String Letter) {
        /**
         * @param Letter    indicates the card type
         * @return true if the card is an Ace, King, Queen, or Jack
         */
        return Letter.equals("A") || Letter.equals("J") || Letter.equals("Q") || Letter.equals("K");
    }

    public String determineSuit(int cardValue) {
        /**This method determines the suit of a card, based
         * on modulo calculation and image file positions.
         * @param cardValue     The value the card is worth.
         * @return the first letter of the suit the card is.
         */
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
        /**This method returns the card value as a string
         * @param cardValue     The value the card is worth.
         * @return the card value as a string
         */
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
        /** This method returns how many points a given face card will be worth
         * @param Symbol    The card type (king, queen, ace, jack)
         * @return the value the face card would recieve in this game
         */
        if (Symbol.equals("A")) {
            return 1;
        } else if (Symbol.equals("J") || Symbol.equals("Q") || Symbol.equals("K")) {
            return 10;
        } else {
            System.out.println("error in face value determiniign");
            return 0;
        }
    }


    public void gameOver() throws IOException {
        /** Cleanup function when the game ends.
         * This function times out a thread, and calculates
         * who the winner of the game is. Then calls the appropriate
         * end of game screen.
         */
        System.out.println("Game Over");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (playerCardTotal > 21) {
            System.out.println("player busted");
            Main.dealerWinScreen();
        } else if (dealerCardTotal > 21) {
            System.out.println("dealer busted");
            Main.playerWinScreen();
        } else if (dealerCardTotal >= playerCardTotal) {
            System.out.println("dealer wins");
            Main.dealerWinScreen();
        } else {
            System.out.println("player wins");
            Main.playerWinScreen();
        }
        //  System.exit(0);
    }

    public int drawCard(List<ImageView> Graphics, Label cardTotalLabel, int hitCount, int cardValue, int cardTotal) {
        /**This method builds the default gameboard the user will see.
         * @param Graphics          The images of the cards to display
         * @param cardTotalLabel    Label to display card total
         * @param hitCount          Number of times the player has hit
         * @param cardValue         The value of the card
         * @param cardTotal         The sum of the dealt cards
         */
        drawnCard = determineValue(cardValue) + determineSuit(cardValue);
        System.out.println(drawnCard);
        try {
            String currentDirectory = System.getProperty("user.dir");
            currentDirectory = currentDirectory + "/src/main/resources/PNG/";
            blankImage = new Image(new FileInputStream(currentDirectory + drawnCard + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (determineIfFaceCard(determineValue(cardValue))) { //if facecard
            cardTotal += determineValueofFaceCards(determineValue(cardValue));
        } else {                                                //if numerical card
            cardTotal += Integer.parseInt(determineValue(cardValue));
        }

        System.out.println(hitCount);
        Graphics.get(hitCount).setImage(blankImage);

        if (cardTotalLabel == DealerTotal) {
            dealerTotalLabel.setText("Dealer Total: " + cardTotal);
        } else {
            playerTotalLabel.setText("Player Total: " + cardTotal);
        }

        return cardTotal;
    }

    public int drawCard(List<ImageView> Graphics, Label cardTotalLabel, int hitCount, int cardValue, int cardTotal, boolean hidden) {
        /**This method draws the cards the user will see to the screen.
         * @param Graphics          The images of the cards to display
         * @param cardTotalLabel    Label to display card total
         * @param hitCount          Number of times the player has hit
         * @param cardValue         The value of the card
         * @param cardTotal         The sum of the dealt cards
         * @param hidden            True if card is not to be displayed yet
         */
        drawnCard = determineValue(cardValue) + determineSuit(cardValue);
        System.out.println(drawnCard);
        try {
            String currentDirectory = System.getProperty("user.dir");
            currentDirectory = currentDirectory + "/src/main/resources/PNG/";
            if (hidden){                                                                        //Draw back of card still facedown
                hiddenCard = cardValue;
                blankImage = new Image(new FileInputStream(currentDirectory + "back.png"));
            } else {blankImage = new Image(new FileInputStream(currentDirectory + drawnCard + ".png"));}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (determineIfFaceCard(determineValue(cardValue))) {
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

        return cardTotal;
    }

    @FXML
    public void hit(ActionEvent e) throws IOException, InterruptedException {
        /**This method writes to the server the player wishes to hit.
         * Then recieves a new card, and checks for a bust
         */
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
        if (playerCardTotal > 21) { //if bust, games over
            try {
                Thread.sleep(1000);
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
            gameOver();
            System.out.println("You lose!");
            //System.exit(0);
        }
    }

    @FXML
    public void ready(ActionEvent e) {
        /**
         * Writes to the server when the user is ready to play.
         * This method starts the game, by generating a seed and
         * shuffling the deck
         */
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
        /**
         * Draws a card for the dealers hand, and removes card
         * from available list
         */
        int DealerDrawResult = cardList.get(0);
        cardList.remove(0);

        dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDrawResult, dealerCardTotal, hidden);
        DealerHitCount ++;

        //int DealerDraw2Result = cardList.get(0);
        //cardList.remove(0);
    }

    @FXML
    public void stand(ActionEvent e) throws IOException {
        /**
         * Writes to the server when the client wants to stand
         * Writes STAND, and total to server so dealer can decide move
         */
        //Player decided to stand, need to tell the server.
        //Server will recieve the users current score, ie:19 or 21...
        //Dealer will then start to hit until it reaches this number, assuming the number is <=21.

        //int to string
        String temp = Integer.toString(playerCardTotal);
        //Sends something to the server, and gets a string back.
        //String contains the
        System.out.println("Sending your score to the server.");

        String message;
        message = connection.sendMessage("STAND," + temp);
        System.out.println(message);

        ArrayList<String> parts = new ArrayList<>(Arrays.asList(message.split(",")));

        //P1Stand.setDisable(true);

        //If false, other player has not pressed stand
        //false
        if (parts.get(0).equals("false")) {
            System.out.println("Wait for the other player to stand!");
        }
        //true,STAND,10
        //or true,STAND,10,34
        //10 - dealercardtotal, 34 carddealerprevious hit with
        else if (parts.get(1).equalsIgnoreCase("STAND")) {                  //everyone is standing, dealer makes play
            if (parts.size() == 3) {
                dealerCardTotal = Integer.parseInt(parts.get(2));
                gameOver();
            } else {
                int DealerDrawResult = Integer.parseInt(parts.get(3));
                //Update dealer values
                dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDrawResult, dealerCardTotal);
                //DealerHitCount++;
                dealerCardTotal = Integer.parseInt(parts.get(2));
                gameOver();
            }
        }
        //HIT,cardvalues...to display,dealerTotal
        //true,HIT,0,9
        else if (parts.get(1).equals("HIT")) {      //dealer still hits
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
        /**
         * Creates a list of indexes, then scrambles the order
         * to simulate a shuffled deck
         */
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
        /**
         * Setting gameboard to blank space. No cards visible
         */
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