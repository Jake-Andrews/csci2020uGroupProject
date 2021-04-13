package org.example;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler implements Runnable {
    private final Socket socket;
    private DealerState dealerState;

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
                String parts[] = line.split(",");

                if (line.equals("\\q")) {
                    out.println("Bye.");
                    break;
                }
                else if (line.equalsIgnoreCase("READY")) {
                    //Create the dealers deck of cards
                    dealerState = new DealerState();
                    dealerState.ready();
                    dealerState.initializeDeck();
                    long randomNumberSeed = dealerState.getRandomNumberSeed();
                    //sends the client the randomNumber
                    //Both clients will get the same number, since initializeDeck only runs for the first client
                    //And randomnumberseed is static
                    System.out.println(String.valueOf(randomNumberSeed));
                    out.println(String.valueOf(randomNumberSeed));

                    //Bad way of removing the two cards the dealer picks at the start
                    //If there are two players it works
                    //Should have the server recognize when each player is ready.
                    //Then the server sends a message to each player and also picks 2 cards for the dealer and subtracts from the deck
                    //If this was implemented could also make it so players can see if their opponenet has not pressed ready
                    //Could lock them out from hitting hit before enemy player is ready.
                    int temp = DealerState.removeCard();
                }
                else if (line.equalsIgnoreCase("HIT")) {
                    //Remove a card from the deck and give it to the player.
                    int a = DealerState.removeCard();
                    String b = String.valueOf(a);
                    out.println(b);
                }
                //Used to make sure both players have hit stand before the game ends
                else if (line.equalsIgnoreCase("END")) {
                    if (DealerState.end == 2) {
                        out.println("YES");
                    } else {out.println("NO");}
                }
                else if (parts.length == 2) {
                    //Once both players have stood, should somehow send a message to both clients, with the dealers chosen cards
                    //And the dealers value, so the player can put the cards on the board and tell if they won.
                    if (parts[0].equalsIgnoreCase("STAND")) {
                        DealerState.setEnd();

                        //Grab the users score
                        System.out.println(parts[1]);

                        //If it's > current users highscore, replace
                        int userScore = Integer.getInteger(parts[1]);
                        if (userScore > DealerState.highestUserScore) {
                            DealerState.setHighestUserScore(userScore);
                        }

                        if (DealerState.end == 1) {

                        }

                        out.println("OK");
                    } else {out.println("Unsure");}
                    //For now, just get a random score
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

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
