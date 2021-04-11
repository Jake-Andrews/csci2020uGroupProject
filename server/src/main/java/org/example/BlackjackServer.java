package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.LinkedList;

public class BlackjackServer {
    public static void start() {
        /*
    public static String SERVER_ADDRESS = "localhost";
    public static int SERVER_PORT = 16789;
        */
        System.out.println("Started server");
        int port = 8001;
        try {
            //creates the connection between the server and the client
            //This connection is put into ClientHandler
            var serverSocket = new ServerSocket(port);
            var threads = new LinkedList<Thread>();
            var handlers = new Hashtable<Long, ClientHandler>();
            var socketThread = new Thread(() -> {
                try {
                    while (true) {
                        var newSocket = serverSocket.accept();
                        var clientHandler = new ClientHandler(newSocket);
                        var thread = new Thread(clientHandler);
                        thread.start();
                        threads.add(thread);
                        handlers.put(thread.getId(), clientHandler);
                        System.out.println("New thread added to the server!");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
            System.out.println("Now outside of the while loop");
            //need to change this, reading from terminal
            var in = new BufferedReader(new InputStreamReader(System.in));
            //Read in from the socket
            String line;
            socketThread.start();
            //Read input, if it is \\q. then quit
            while ((line = in.readLine()) == null || !line.equalsIgnoreCase("\\q"));
            //interrupt the current thread from what it is doing.
            socketThread.interrupt();
            //Interrupt every thread
            while (!threads.isEmpty()) {
                var t = threads.remove();
                handlers.get(t.getId()).stop();
                t.interrupt();
            }
            System.out.println("Closing the server.");
            //Close our serverSocket and input stream
            serverSocket.close();
            in.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}


/**
 * Lab 10 Demo: server.MessageServer
 *
 * @author Michael Valdron
 * created at 2021/03/30
 */

