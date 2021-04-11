package org.example;

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
                if (line.equalsIgnoreCase("READY")) {
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
                System.out.println("Recieved: " + line + " from client.");
                out.println(line + " was recieved");
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
