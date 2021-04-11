package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.LinkedList;

public class BlackjackServer {
    public static void start() {
        //Boilerplate code was ripped from lab10 demo for handler and server. Changed as needed.
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
            //Read in from terminal, poorly done

            //Should comment this out, change in to something else

            String line;
            socketThread.start();
            //Read input, if it is \\q. then quit
            while ((line = in.readLine()) == null || !line.equalsIgnoreCase("\\q")) {

            };

            //Somehow continue until there is a single thread alive.
            //THe way it's done above will never close since there is no input from terminal
            //We want our game to exit, once each thread is killed.
            //Also we need our server to do stuff. Such as, server reads from thread, once every thread
            //Has said recieved "READY", then we can send dealer cards to each user.
            //while (socketThread.isAlive()) {};


            socketThread.interrupt();
            //just removes the sockets, is fine
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