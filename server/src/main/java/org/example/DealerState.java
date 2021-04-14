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

    private int upperbound = 51;
    private static int dealerCardTotal = 0;
    private static int ready = 0;
    public static int highestUserScore = 0;
    public static int end = 0;
    public static int dealersScore = 0;
    private static int counter = 0;
    private static int dealerCard = 0;

    private String drawnCard = "";
    private static List<Integer> cardList = new ArrayList<Integer>();
    private static List<Integer> playerScores = new ArrayList<Integer>();

    private static long randomNumberSeed;

    public DealerState() {}

    public static String getState() {
        if (end == 1) {
            return "false";
        } else {return "true";}
    }

    public static String getCards() {
        String listOfCards = "";
        System.out.println("Before loop, value: " + dealerCardTotal);

        while (dealerCardTotal < highestUserScore && dealerCardTotal <= 21 && counter < 1) {
            // = cardList.get(0);
            //Grab a card from the deck and remove it.
            //Cards numbered 0-51
            int card = cardList.get(0);
            dealerCard = card;
            System.out.println("In Loop: " + card);
            listOfCards = listOfCards + card + ",";
            cardList.remove(0);

            //Grab it's "value". 2,3,4,10,etc...
            int cardValue = getCardValue(card);
            dealerCardTotal += cardValue;
            System.out.println("In loop value: " + cardValue);

            counter++;
        }

        System.out.println("Dealers list: " + listOfCards);
        System.out.println("Dealers score: " + dealerCardTotal + " Users highscore: " + highestUserScore);

        //If the dealer did not have to hit at all, notify user
        if (listOfCards == "" && counter == 0) {
            return "STAND," + dealerCardTotal;
        //otherwise, the dealer hit.
        }
        else if(listOfCards == "" && counter >0) {
            return "STAND," + dealerCardTotal + "," + dealerCard;
        }
        else {return "HIT," + listOfCards + dealerCardTotal;}
    }

    public static int getCardValue(int cardValue) {

        String drawnCard = determineValue(cardValue) + determineSuit(cardValue);
        //System.out.println(drawnCard);
        int value = 0;

        if (determineIfFaceCard(determineValue(cardValue)) == true) {
            value = determineValueofFaceCards(determineValue(cardValue));
        } else {
            value = Integer.parseInt(determineValue(cardValue));
        }

        return value;
    }

    public static void setEnd() {
        end++;
    }

    public static void setHighestUserScore(int num) {
        highestUserScore = num;
    }

    public static int removeCard() {
        int card = cardList.get(0);
        int value = getCardValue(card);
        System.out.println("Adding value to dealer: " + value);

        dealerCardTotal += value;
        cardList.remove(0);

        return card;
    }

    public static int removeCardForPlayer() {
        int card = cardList.get(0);
        cardList.remove(0);

        return card;
    }

    public static void setPlayerScores(Integer score) {
        playerScores.add(score);
    }
    public static boolean determineIfFaceCard(String Letter) {
        if (Letter == "A" || Letter == "J" || Letter == "Q" || Letter == "K") {
            return true;
        } else {return false;}
    }

    public static String determineSuit(int cardValue) {
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

    public static String determineValue(int cardValue) {
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

    public static int determineValueofFaceCards(String Symbol) {
        if (Symbol == "A") {
            return 1;
        } else if (Symbol == "J" || Symbol == "Q" || Symbol == "K") {
            return 10;
        } else {
            System.out.println("error in face value determiniign");
            return 0;
        }

    }

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