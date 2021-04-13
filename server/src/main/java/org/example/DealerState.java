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

public class DealerState {

    private int DealerHitCount = 0;
    private int upperbound = 51;
    private static int dealerCardTotal = 0;
    private int test = 0;
    private int Bust = 21;
    private static int ready = 0;
    public static int highestUserScore = 0;
    public static int end = 0;

    private String drawnCard = "";
    private static List<Integer> cardList = new ArrayList<Integer>();
    private static List<Integer> playerScores = new ArrayList<Integer>();

    private static long randomNumberSeed;

    public DealerState() {}

    public static int getScore() {
        while (dealerCardTotal < highestUserScore && dealerCardTotal != 21) {
            // = cardList.get(0);
        }
        return 0;
    }

    public static void setEnd() {
        end++;
    }

    public static void setHighestUserScore(int num) {
        highestUserScore = num;
    }
    public static boolean checkScores() {
        return playerScores.size() == 2;
    }

    public static int removeCard() {
        int card = cardList.get(0);
        cardList.remove(0);

        return card;
    }

    public static void setPlayerScores(Integer score) {
        playerScores.add(score);
    }
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
    /*
    public int drawCard(List<ImageView> Graphics, Label cardTotalLabel, int hitCount, int cardValue, int cardTotal) {
        //gets the current drawn card and determines its value and suit.
        //Ie: 2H
        drawnCard = determineValue(cardValue) + determineSuit(cardValue);
        System.out.println(drawnCard);

        //Grab the cards
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
    */

    public int drawCard(List<ImageView> Graphics, Label cardTotalLabel, int hitCount, int cardValue, int cardTotal, boolean hidden) {

        drawnCard = determineValue(cardValue) + determineSuit(cardValue);
        System.out.println(drawnCard);

        if (determineIfFaceCard(determineValue(cardValue)) == true) {
            cardTotal += determineValueofFaceCards(determineValue(cardValue));
        } else {
            cardTotal += Integer.parseInt(determineValue(cardValue));
        }

        return cardTotal;
    }
    /*
    public void hit() throws IOException {
        int playerCard = cardList.get(0);
        cardList.remove(0);
        playerCardTotal = drawCard(playerImages, PlayerTotal, PlayerHitCount, playerCard, playerCardTotal);
        PlayerHitCount += 1;
        if (playerCardTotal > 21) {
            gameOver();
            //System.exit(0);
        }
    }

    public void drawDealerCard(boolean hidden) {
        int DealerDrawResult = cardList.get(0);
        cardList.remove(0);

        dealerCardTotal = drawCard(dealerImages, DealerTotal, DealerHitCount, DealerDrawResult, dealerCardTotal, hidden);
        DealerHitCount += 1;

        int DealerDraw2Result = cardList.get(0);
        cardList.remove(0);
    }
    */
    public void ready() {
        this.ready++;
    }

    public void initializeDeck()  {
        //When the first player connects, ready will == 1.
        //Therefore the random number seed doesn't get overwritten by the second  player
        if (ready == 1) {
            //Uses an algorithm to fill the cardList with 52 numbers between 0 and 51
            randomNumberSeed = System.currentTimeMillis();
            Random DRAW = new Random();
            DRAW.setSeed(randomNumberSeed);
            System.out.println("Random number generator seed is " + randomNumberSeed);

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
    }

    public long getRandomNumberSeed() {
        return randomNumberSeed;
    }
}