package org.example;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            var out = new PrintWriter(socket.getOutputStream(), true);
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while (!socket.isClosed() && (line = in.readLine()) != null) {
                if (line.equals("\\q")) {
                    out.println("Bye.");
                    break;
                }
                else if (line.equalsIgnoreCase("READY")) {
                    //String temp = generateTwoCards();
                    //Do something. Currently all of the code is in the client.
                    //Could seperate the dealer and the user
                    //Player says ready, dealer picks 2 cards, sends 2 strings to user
                    //Add those strings to the client and remove those cards from the deck
                    //Then each time user presses hit they send "HIT" to the dealer
                    //Dealer sends them a card.
                    //Dealer substracts this card from his list of avaliable cards
                    //User sends "STAND, int"
                    //Dealer tries to get higher than the int.
                }
                else if (line.equalsIgnoreCase("HIT")) {
                    //do nothing
                }
                else if (line.equalsIgnoreCase("STAND")) {
                    //Make a boolean true. So the blackjackserver can tell each user is done
                    //And the dealer can start to hit based on the highest card value given to him.
                }
                else {
                    System.out.println("Recieved: " + line + " from client.");
                    out.println(line + " was recieved");
                }
            }
            System.out.println("closed");
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    //isn't finished no clue how you guys generated the cards for the user
    public String generateTwoCards() {
        String cards = "";
        //2 to A, 2,3,4,5,6,7,8,9,10,j,q,k,a hence 2 to 14
        //Picking the value of the card
        int max = 14;
        int min = 2;

        Random random = new Random();
        int faceValue = random.nextInt(max - min + 1) + min;

        int max1 = 4;
        int min1 = 1;

        Random random1 = new Random();
        int suit = random1.nextInt(max1 - min1 + 1) + min1;

        return cards;
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
